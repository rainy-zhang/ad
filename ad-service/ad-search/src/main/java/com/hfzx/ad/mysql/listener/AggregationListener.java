package com.hfzx.ad.mysql.listener;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hfzx.ad.mysql.TemplateHolder;
import com.hfzx.ad.mysql.dto.BinlogRowData;
import com.hfzx.ad.mysql.dto.TableTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: zhangyu
 * @Description: 自定义EventListener 用来收集聚合Mysql binlog
 * @Date: in 2020/5/4 15:07
 */
@Slf4j
@Component
public class AggregationListener implements BinaryLogClient.EventListener {

    private String dbName;
    private String tableName;

    /**
     * 可以根据数据库中不同的表来使用不同的处理方法
     */
    private Map<String, BinlogListener> binlogListenerMap = Maps.newHashMap();

    private final TemplateHolder templateHolder;

    @Autowired
    public AggregationListener(TemplateHolder templateHolder) {
        this.templateHolder = templateHolder;
    }

    private String generatorKey(String _dbName, String _tableName) {
        return String.format("%s:%s", _dbName, _tableName);
    }

    public void register(String _dbName, String _tableName, BinlogListener binlogListener) {
        log.info("register: {}-{}", _dbName, _tableName);
        this.binlogListenerMap.put(generatorKey(_dbName, _tableName), binlogListener);
    }

    /**
     * 将event解析成自定义的BinlogRowData, 然后把BinlogRowData传递给Listener实现增量数据的更新
     * @param event
     */
    @Override
    public void onEvent(Event event) {
        EventType type = event.getHeader().getEventType();
        log.debug("eventType: {}", type);
        // TABLE_MAP中包含了接下来要操作的数据库和数据表的名字
        if (type == EventType.TABLE_MAP) {
            TableMapEventData data = event.getData();
            this.dbName = data.getDatabase();
            this.tableName = data.getTable();
            return;
        }

        if (type != EventType.EXT_DELETE_ROWS && type != EventType.EXT_UPDATE_ROWS && type != EventType.EXT_WRITE_ROWS)
            return;

        // 表名和库名是否完成填充
        if (StringUtils.isBlank(this.dbName) || StringUtils.isBlank(this.tableName)) {
            log.error("no meta data event");
            return;
        }

        // 找到对应表的监听器
        String key = generatorKey(this.dbName, this.tableName);
        BinlogListener binlogListener = this.binlogListenerMap.get(key);

        if (null == binlogListener) {
            log.debug("skip: {}", key);
            return;
        }

        log.info("trigger event: {}", type.name());

        try {
            BinlogRowData binlogRowData = buildRowData(event.getData());
            if (null == binlogRowData)
                return;
            binlogRowData.setEventType(type);
            binlogListener.onEvent(binlogRowData);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
            this.dbName = "";
            this.tableName = "";
        }
    }

    /**
     * 将EventData转换为BinlogRowData
     * @param eventData
     * @return
     */
    private BinlogRowData buildRowData(EventData eventData) {
        TableTemplate table = templateHolder.getTable(this.tableName);
        if (null == table) {
            log.warn("not found table: {}", this.tableName);
            return null;
        }

        List<Map<String, String>> afterMapList = Lists.newArrayList();
        for (Serializable[] after : getAfterValues(eventData)) {

            /**
             * key: 列名, value: 列对应的值
             */
            Map<String, String> afterMap = new HashMap<>();
            int length = after.length;

            for (int i = 0; i < length; i++) {
                // 取出当前索引对应的列名
                String columnName = table.getPosMap().get(i);

                // 如果列不存在 说明不关心这个列
                if (StringUtils.isBlank(columnName)) {
                    log.debug("ignore position: {}", i);
                    continue;
                }

                String columnValue = after[i].toString();
                afterMap.put(columnName, columnValue);
            }
            afterMapList.add(afterMap);
        }
        BinlogRowData binlogRowData = new BinlogRowData();
        binlogRowData.setAfter(afterMapList);
        binlogRowData.setTable(table);

        return binlogRowData;
    }

    /**
     * 根据不同的操作类型获取对应的Rows
     * @param eventData
     * @return
     */
    private List<Serializable[]> getAfterValues(EventData eventData) {
        // TODO:
        if (eventData instanceof WriteRowsEventData) {
            return ((WriteRowsEventData) eventData).getRows();
        }
        if (eventData instanceof UpdateRowsEventData) {
            // UpdateRowsEventData.getRows() 返回一个Map, key: before, value: after. 这里只关心修改之后的值
            return ((UpdateRowsEventData) eventData).getRows().stream().map(Map.Entry::getValue).collect(Collectors.toList());
        }
        if (eventData instanceof DeleteRowsEventData) {
            return ((DeleteRowsEventData) eventData).getRows();
        }
        return Collections.emptyList();
    }


}

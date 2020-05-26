package com.hfzx.ad.mysql.listener;

import com.github.shyiko.mysql.binlog.event.EventType;
import com.google.common.collect.Maps;
import com.hfzx.ad.mysql.constant.Constant;
import com.hfzx.ad.mysql.constant.OpType;
import com.hfzx.ad.mysql.dto.BinlogRowData;
import com.hfzx.ad.mysql.dto.MySqlRowData;
import com.hfzx.ad.mysql.dto.TableTemplate;
import com.hfzx.ad.sender.BinlogSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhangyu
 * @Description: 处理增量数据的处理器
 * @Date: in 2020/5/9 23:13
 */
@Slf4j
@Component
public class IncrementListener implements BinlogListener {

    @Resource(name = "indexSender")
    private BinlogSender sender;

    private final AggregationListener aggregationListener;

    @Autowired
    public IncrementListener(AggregationListener aggregationListener) {
        this.aggregationListener = aggregationListener;
    }

    /**
     * 将表注册
     */
    @Override
    public void register() {
        log.info("IncrementListener register db and table info");
        Constant.table2Db.forEach((key, value) -> aggregationListener.register(value, key, this));
    }

    /**
     * 将BinlogRowData转换成MySqlRowData并投递出去
     * @param eventData
     */
    @Override
    public void onEvent(BinlogRowData eventData) {
        TableTemplate table = eventData.getTable();
        EventType eventType = eventData.getEventType();

        // 包装最后需要投递的数据
        MySqlRowData mySqlRowData = new MySqlRowData();
        mySqlRowData.setTableName(table.getTableName());
        mySqlRowData.setLevel(table.getLevel());
        OpType opType = OpType.to(eventType);
        mySqlRowData.setOpType(opType);

        // 取出模板中该操作对应的字段列表
        List<String> fieldList = table.getOpTypeFieldSetMap().get(opType);
        if (null == fieldList) {
            log.warn("{} not support for {}", opType, table.getTableName());
            return;
        }

        for (Map<String, String> afterMap : eventData.getAfter()) {
            Map<String, String> _afterMap = Maps.newHashMap();
            for (Map.Entry<String, String> entry : afterMap.entrySet()) {
                String columnName = entry.getKey();
                String columnValue = entry.getValue();
                _afterMap.put(columnName, columnValue);
            }
            mySqlRowData.getFieldValueMap().add(_afterMap);
        }

        sender.send(mySqlRowData);
    }
}

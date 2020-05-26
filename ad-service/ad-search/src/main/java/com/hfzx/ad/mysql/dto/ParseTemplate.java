package com.hfzx.ad.mysql.dto;

import com.google.common.collect.Maps;
import com.hfzx.ad.mysql.constant.OpType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @Author: zhangyu
 * @Description: 解析binlog模板文件
 * @Date: in 2020/5/2 20:56
 */
@Data
public class ParseTemplate {

    private String database;

    /**
     * key: 表名, value: tableTemplate
     */
    private Map<String, TableTemplate> tableTemplateMap = Maps.newHashMap();

    /**
     * 解析模板文件
     * @param binlogTemplate 模板文件对应的实体类
     * @return
     */
    public static ParseTemplate parse(BinlogTemplate binlogTemplate) {
        ParseTemplate parseTemplate = new ParseTemplate();
        parseTemplate.setDatabase(binlogTemplate.getDatabase());

        for (JsonTable table : binlogTemplate.getTableList()) {
            String tableName = table.getTableName();
            Integer level = table.getLevel();

            TableTemplate tableTemplate = new TableTemplate();
            tableTemplate.setTableName(tableName);
            tableTemplate.setLevel(level.toString());
            parseTemplate.tableTemplateMap.put(tableName, tableTemplate);

            Map<OpType, List<String>> opTypeFieldSetMap = tableTemplate.getOpTypeFieldSetMap();

            for (JsonTable.Column column : table.getInsert()) {
                getOrCreateIfNeed(OpType.ADD, opTypeFieldSetMap, ArrayList::new).add(column.getName());
            }

            for (JsonTable.Column column : table.getUpdate()) {
                getOrCreateIfNeed(OpType.UPDATE, opTypeFieldSetMap, ArrayList::new).add(column.getName());
            }

            for (JsonTable.Column column : table.getDelete()) {
                getOrCreateIfNeed(OpType.DELETE, opTypeFieldSetMap, ArrayList::new).add(column.getName());
            }

        }
        return parseTemplate;
    }

    public static <T, R> R getOrCreateIfNeed(T key, Map<T, R> map, Supplier<R> factory) {
        return map.computeIfAbsent(key, k -> factory.get());
    }

}

package com.hfzx.ad.mysql.dto;

import com.google.common.collect.Maps;
import com.hfzx.ad.mysql.constant.OpType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @Author: zhangyu
 * @Description: 用于更方便的操作table
 * @Date: in 2020/5/2 10:47
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableTemplate {

    private String tableName;
    private String level;

    /**
     * 操作类型 -> List<列名>
     */
    private Map<OpType, List<String>> opTypeFieldSetMap = Maps.newHashMap();

    /**
     * 字段索引 -> 字段名
     */
    private Map<Integer, String> posMap = Maps.newHashMap();

}

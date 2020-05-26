package com.hfzx.ad.mysql.dto;

import com.google.common.collect.Lists;
import com.hfzx.ad.mysql.constant.OpType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @Author: zhangyu
 * @Description: 用于实现增量数据的投递
 * @Date: in 2020/5/4 15:57
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MySqlRowData {

    private String tableName;

    private String level;

    private OpType opType;

    /**
     * 就是binlog中的after
     * key: 列名, value: 列对应的值
     */
    private List<Map<String, String>> fieldValueMap = Lists.newArrayList();

}

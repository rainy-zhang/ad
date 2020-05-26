package com.hfzx.ad.mysql.dto;

import com.github.shyiko.mysql.binlog.event.EventType;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Author: zhangyu
 * @Description:
 * @Date: in 2020/5/3 11:58
 */
@Data
public class BinlogRowData {

    private TableTemplate table;
    private EventType eventType;

    /**
     * key: 操作的列的名字, value: 列对应的值
     */
    private List<Map<String, String>> before;

    /**
     * key: 操作的列的名字, value: 列对应的值
     */
    private List<Map<String, String>> after;

}

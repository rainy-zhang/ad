package com.hfzx.ad.mysql.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: zhangyu
 * @Description:
 * @Date: in 2020/5/2 10:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BinlogTemplate {

    private String database;
    private List<JsonTable> tableList;

}

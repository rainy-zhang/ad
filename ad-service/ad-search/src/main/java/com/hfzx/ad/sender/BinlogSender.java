package com.hfzx.ad.sender;

import com.hfzx.ad.mysql.dto.MySqlRowData;

/**
 * @Author: zhangyu
 * @Description: 可以有多种投递方式: kafka, 文件...
 * @Date: in 2020/5/9 23:12
 */
public interface BinlogSender {

    void send(MySqlRowData mySqlRowData);

}

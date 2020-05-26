package com.hfzx.ad.service;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;

import java.io.IOException;

/**
 * @Author: zhangyu
 * @Description:
 * @Date: in 2020/5/2 10:25
 */
public class BinlogServiceTest {

    public static void main(String[] args) throws IOException {
        BinaryLogClient logClient = new BinaryLogClient("127.0.0.1", 3306, "root", "root");
//        logClient.setBinlogFilename("mysql-bin.000002"); // 设置监听哪个binlog文件, 默认是最新的文件
//        logClient.setBinlogPosition(); // 设置从哪个位置开始监听, 默认是最新的位置
        logClient.registerEventListener(event -> {
            EventData data = event.getData();
            if (data instanceof TableMapEventData) {
                System.out.println("TableMap-------");
                System.out.println(data.toString());
            } else if (data instanceof UpdateRowsEventData) {
                System.out.println("Update--------");
                System.out.println(data.toString());
            } else if (data instanceof WriteRowsEventData) {
                System.out.println("Write---------");
                System.out.println(data.toString());
            } else if (data instanceof DeleteRowsEventData) {
                System.out.println("Delete---------");
                System.out.println(data.toString());
            }
        });
        logClient.connect();
    }

}

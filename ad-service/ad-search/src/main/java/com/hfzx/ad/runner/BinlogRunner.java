package com.hfzx.ad.runner;

import com.hfzx.ad.mysql.BinlogClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @Author: zhangyu
 * @Description: 项目启动时开始监听binlog
 * @Date: in 2020/5/9 23:48
 */
@Slf4j
@Component
public class BinlogRunner implements CommandLineRunner {

    private final BinlogClient binlogClient;

    @Autowired
    public BinlogRunner(BinlogClient binlogClient) {
        this.binlogClient = binlogClient;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Coming in BinlogRunner...");
        binlogClient.connect();
    }
}

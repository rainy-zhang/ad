package com.hfzx.ad.mysql;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.hfzx.ad.mysql.listener.AggregationListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author: zhangyu
 * @Description:
 * @Date: in 2020/5/9 23:36
 */
@Slf4j
@Component
public class BinlogClient {

    private BinaryLogClient client;

    private final BinlogConfig  binlogConfig;
    private final AggregationListener aggregationListener;

    @Autowired
    public BinlogClient(AggregationListener aggregationListener, BinlogConfig binlogConfig) {
        this.aggregationListener = aggregationListener;
        this.binlogConfig = binlogConfig;
    }

    /**
     * 开始监听mysql binlog
     */
    public void connect() {
        new Thread(() -> {
            client = new BinaryLogClient(binlogConfig.getHost(), binlogConfig.getPort(), binlogConfig.getUsername(), binlogConfig.getPassword());

            // 当binlogName不为空 且 position不等于-1时才从指定binlog文件开始监听否则从当前位置监听
            if (StringUtils.isNotBlank(binlogConfig.getBinlogName()) && !binlogConfig.getPosition().equals(-1L)) {
                client.setBinlogFilename(binlogConfig.getBinlogName());
                client.setBinlogPosition(binlogConfig.getPosition());
            }

            client.registerEventListener(aggregationListener);

            try {
                log.info("connecting to mysql start");
                client.connect();
                log.info("connecting to mysql done");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * 断开监听
     */
    public void close() {
        try {
            client.disconnect();
            log.info("disconnect to mysql!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

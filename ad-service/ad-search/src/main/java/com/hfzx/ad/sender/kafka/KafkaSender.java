package com.hfzx.ad.sender.kafka;

import com.alibaba.fastjson.JSON;
import com.hfzx.ad.mysql.dto.MySqlRowData;
import com.hfzx.ad.sender.BinlogSender;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @Author: zhangyu
 * @Description: 将增量数据投放到kafka中
 * @Date: in 2020/5/10 18:18
 */
@Slf4j
@Component(value = "kafkaSender")
public class KafkaSender implements BinlogSender {

    @Value("${adconf.kafka.topic}")
    private String topic;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public KafkaSender(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(MySqlRowData mySqlRowData) {
        kafkaTemplate.send(topic, JSON.toJSONString(mySqlRowData));
    }


    @KafkaListener(topics = {"ad-search-mysql-data"}, groupId = "ad-search")
    public void processMySqlRowData(ConsumerRecord<?, ?> consumerRecord) {
        Optional<?> kafkaMessage = Optional.ofNullable(consumerRecord.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            MySqlRowData mySqlRowData = JSON.parseObject(message.toString(), MySqlRowData.class);
            System.out.println("kafka processMySqlRowData: " + JSON.toJSONString(mySqlRowData));
        }
    }

}

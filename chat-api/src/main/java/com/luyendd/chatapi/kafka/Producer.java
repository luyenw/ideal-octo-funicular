package com.luyendd.chatapi.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class Producer {
//    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String msg) {
        System.out.println("sending message: "+msg);
        kafkaTemplate.send("test", msg);
        System.out.println("send message: "+msg);

    }
}

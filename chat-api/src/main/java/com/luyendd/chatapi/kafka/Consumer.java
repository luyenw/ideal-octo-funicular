package com.luyendd.chatapi.kafka;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    @KafkaListener(topics = "test", groupId = "group-id")
    public void consumeMessage(String message) {
        System.out.println("Received message: " + message);
    }
}

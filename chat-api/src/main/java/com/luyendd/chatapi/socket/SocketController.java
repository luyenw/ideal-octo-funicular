package com.luyendd.chatapi.socket;

import com.luyendd.chatapi.dto.Message;
import com.luyendd.chatapi.kafka.Consumer;
import com.luyendd.chatapi.kafka.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.math.BigInteger;

@Controller
public class SocketController {
    @Autowired
    private Producer kafkaProducer;
    @MessageMapping("/message")
    @SendTo("/topic/messages")
    public Message sendMessage(Message message){
        kafkaProducer.sendMessage(message.content);
        return new Message(
                "reply: "+message.content);
    }
}

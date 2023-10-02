package com.luyendd.apiserver.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

//    public List<Message> recentMessages(BigInteger id){
//        List<Message> fromId = messageRepository.
//    }
}

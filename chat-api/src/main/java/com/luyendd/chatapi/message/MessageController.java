package com.luyendd.chatapi.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {
    private MessageRepository messageRepository;
    @Autowired
    public MessageController(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }
    @GetMapping("/")
    public ResponseEntity hello(){
        return ResponseEntity.status(200).body(List.of(messageRepository.findAll()));
    }
    @PostMapping("/")
    public ResponseEntity post(@RequestBody Message message){
        System.out.println(message);
        messageRepository.insert(message);
        return ResponseEntity.status(200).body(List.of(messageRepository.findAll()));
    }
}

package com.luyendd.apiserver.message;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private LatestMessageRepository latestMessageRepository;
    @GetMapping("/")
    public ResponseEntity hello(){
        return ResponseEntity.status(200).body(List.of(messageRepository.findAll()));
    }
    @PostMapping("/")
    public ResponseEntity post(@RequestBody MessageDTO message){
        UUID random_uuid = UUID.randomUUID();
        Message newMessage = new Message(
                random_uuid,
                message.getFrom(),
                message.getTo(),
                message.getAt(),
                message.getContent());
        messageRepository.insert(newMessage);
        latestMessageRepository.insert(new LatestMessage(random_uuid, message.getFrom()));
        latestMessageRepository.insert(new LatestMessage(random_uuid, message.getTo()));
        return ResponseEntity.status(200).body(List.of(messageRepository.findAll()));
    }
    @GetMapping("/{from_id}")
    public ResponseEntity fromId(@PathVariable Long from_id){
        System.out.println(from_id);
        return ResponseEntity.status(200).body(messageRepository.findByFromId(from_id));
    }
}

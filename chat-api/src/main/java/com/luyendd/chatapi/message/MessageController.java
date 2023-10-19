package com.luyendd.chatapi.message;

import com.luyendd.chatapi.group.Group;
import com.luyendd.chatapi.group.GroupRepository;
import com.luyendd.chatapi.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:8082", allowCredentials="true")
@RequestMapping("/message")
public class MessageController {
    private MessageRepository messageRepository;
    private GroupRepository groupRepository;
    @Autowired
    public MessageController(MessageRepository messageRepository, GroupRepository groupRepository){
        this.messageRepository = messageRepository;
        this.groupRepository = groupRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMessage(@PathVariable("id") String receiverId, @CookieValue String access_token){
        try{
            RestTemplate restTemplate = new RestTemplate();
            String url = "http://localhost:3001/verify";
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + access_token);
            HttpEntity<?> httpEntity = new HttpEntity<>(headers);
            ResponseEntity<?> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, User.class);
            User sender = (User) response.getBody();
            String senderId = sender.getId();

            List<UUID> groupBySender = groupRepository.findByUserId(senderId).stream().map(Group::getGroupId).collect(Collectors.toList());
            List<UUID> groupByReceiver = groupRepository.findByUserId(receiverId).stream().map(Group::getGroupId).collect(Collectors.toList());
            UUID commonId = commonId(groupByReceiver, groupBySender);
            if (commonId == null) {
                return ResponseEntity.status(200).body(new ArrayList<>());
            }
            return ResponseEntity.status(200).body(messageRepository.findByGroupId(commonId));
        }catch(Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(200).body(new ArrayList<>());
    }
    private UUID commonId(List<UUID> list1, List<UUID> list2){
        for (UUID id: list1){
            if (list2.contains(id)){
                return id;
            }
        }
        return null;
    }
    @GetMapping("/")
    public ResponseEntity<?> hello(){
        return new ResponseEntity<>(List.of(messageRepository.findAll()), HttpStatus.OK);
    }

}

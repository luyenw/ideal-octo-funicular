package com.luyendd.chatapi.group;

import com.luyendd.chatapi.message.LatestMessage;
import com.luyendd.chatapi.message.Message;
import com.luyendd.chatapi.message.MessageRepository;
import com.luyendd.chatapi.utils.UUIDToDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/recent")
@CrossOrigin(origins = "http://localhost:8082", allowCredentials="true")
public class GroupController {
    private GroupRepository groupRepository;
    private MessageRepository messageRepository;
    @Autowired
    public GroupController(GroupRepository groupRepository, MessageRepository messageRepository){
        this.messageRepository = messageRepository;
        this.groupRepository = groupRepository;
    }
    @GetMapping("/{user_id}")
    public ResponseEntity<?> recent(@PathVariable String user_id, @CookieValue("access_token") String access_token){
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = "http://localhost:3001/verify";
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + access_token);
            HttpEntity<?> httpEntity = new HttpEntity<>(headers);
            ResponseEntity<?> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
            List<Group> groupList = groupRepository.findByUserId(user_id).stream().map(group -> {
                List<Group> list = groupRepository.getPs(group.getGroupId());
                list.removeIf(group1 -> group1.getUserId().equals(user_id));
                return list.get(0);
            }).toList();

            List<LatestMessage> latestMessageList = new ArrayList<>();
            for(Group group: groupList){
                Message latestMsg = messageRepository.findLatestMessage(group.getGroupId());
                LatestMessage latestMessage = new LatestMessage(group.getGroupId(), group.getUserId(), latestMsg.getMessage(), UUIDToDate.getTimeFromUUID(latestMsg.getMessageId()));
                latestMessageList.add(latestMessage);
            }
            return ResponseEntity.status(200).body(latestMessageList);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(200).body(new ArrayList<>());
    }
}

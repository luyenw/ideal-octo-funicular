package com.luyendd.chatapi.message;

import com.luyendd.chatapi.group.Group;
import com.luyendd.chatapi.group.GroupRepository;
import com.luyendd.chatapi.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
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
    private boolean isUUID(String id){
        try{
            UUID uuid = UUID.fromString(id);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getMessage(@PathVariable("id") String groupId, @CookieValue String access_token){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:3001/verify";
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + access_token);
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<?> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, User.class);
        User sender = (User) response.getBody();
        String senderId = sender.getId();
        if(isUUID(groupId)) {
            try {
                // kiem tra (user_id, group_id) co trong groups_by_user
                return ResponseEntity.status(200).body(messageRepository.findByGroupId(UUID.fromString(groupId)));
            } catch (HttpClientErrorException.Unauthorized e) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }else{
            if (senderId.equals(groupId))
                return ResponseEntity.status(200).body(new ArrayList<>());
            List<Group> groupList1 = groupRepository.findByUserId(senderId);
            List<Group> groupList2 = groupRepository.findByUserId(groupId);
            UUID commonGroupId = commonGroup(groupList1, groupList2);
            if(commonGroupId!=null) {
                return ResponseEntity.status(200).body(messageRepository.findByGroupId(commonGroupId));
            }
        }
        return ResponseEntity.status(200).body(new ArrayList<>());
    }
    @GetMapping("/request/uuid")
    public ResponseEntity<?> hello(){
        return ResponseEntity.status(HttpStatus.OK).body(UUID.randomUUID());
    }
    public UUID commonGroup(List<Group> list1, List<Group> list2){
        List<UUID> uuidList1 = list1.stream().map(Group::getGroupId).toList();
        List<UUID> uuidList2 = list2.stream().map(Group::getGroupId).toList();
        for(UUID uuid:uuidList1){
            if(uuidList2.contains(uuid)) return uuid;
        }
        return null;
    }
}

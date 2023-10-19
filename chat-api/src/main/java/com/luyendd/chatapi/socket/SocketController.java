package com.luyendd.chatapi.socket;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.luyendd.chatapi.group.Group;
import com.luyendd.chatapi.group.GroupRepository;
import com.luyendd.chatapi.message.Message;
import com.luyendd.chatapi.message.MessageDTO;
import com.luyendd.chatapi.message.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class SocketController {
//    @Autowired
//    private Producer kafkaProducer;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private MessageRepository messageRepository;


    @MessageMapping("/message")
    @SendTo("/topic/messages")
    public String sendMessage(MessageDTO message){
//        kafkaProducer.sendMessage(message.content);
        System.out.println(message);
        List<UUID> groupBySender = groupRepository.findByUserId(message.getFromId()).stream().map(Group::getGroupId).collect(Collectors.toList());
        List<UUID> groupByReceiver = groupRepository.findByUserId(message.getToId()).stream().map(Group::getGroupId).collect(Collectors.toList());

        // determine group_id
        UUID commonId = commonId(groupByReceiver, groupBySender);
        if (commonId == null) {
            commonId = UUID.randomUUID();
            groupRepository.insert(new Group(commonId, message.getFromId()));
            groupRepository.insert(new Group(commonId, message.getToId()));
        }
        // insert msg into messages_by_group
        Message newMessage = new Message(commonId, Uuids.timeBased(), message.getMessage(), message.getFromId());
        this.messageRepository.insert(newMessage);
        return "reply: "+message.getMessage();
    }
    private UUID commonId(List<UUID> list1, List<UUID> list2){
        for (UUID id: list1){
            if (list2.contains(id)){
                return id;
            }
        }
        return null;
    }
}

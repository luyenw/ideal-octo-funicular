package com.luyendd.chatapi.socket;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.luyendd.chatapi.group.Group;
import com.luyendd.chatapi.group.GroupRepository;
import com.luyendd.chatapi.kafka.Producer;
import com.luyendd.chatapi.message.Message;
import com.luyendd.chatapi.message.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class SocketModule {
    @Autowired
    private Producer kafkaProducer;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private GroupRepository groupRepository;
    private final SocketIOServer server;
    private final SocketService socketService;

    public SocketModule(SocketIOServer server, SocketService socketService) {
        this.server = server;
        this.socketService = socketService;
        server.addConnectListener(onConnected());
        server.addDisconnectListener(onDisconnected());
        server.addEventListener("send_message", SocketMessage.class, onChatReceived());
        server.addEventListener("new_group", Group.class, newGroupRequest());
    }
    private DataListener<Group> newGroupRequest(){
        return (socketIOClient, data, ackRequest) -> {
            groupRepository.insert(new Group(data.getGroupId(), data.getUserId()));
            System.out.println("insert new group");
        };
    }
    private DataListener<SocketMessage> onChatReceived() {
        return (senderClient, data, ackSender) -> {
            kafkaProducer.sendMessage(data.getMessage());
            log.info(data.toString());
            Message message = new Message(UUID.fromString(data.getGroupId()), Uuids.timeBased(), data.getMessage(), data.getUserId());
            messageRepository.insert(message);
            socketService.sendMessage(data.getGroupId(),"get_message", senderClient, message);
            List<Group> ps = groupRepository.getPs(UUID.fromString(data.getGroupId()));
            for(Group participant: ps){
                System.out.println("send to "+ String.valueOf(participant.getUserId()));
                socketService.sendMessage(participant.getUserId(), "new_message", senderClient, message);
            }
        };
    }


    private ConnectListener onConnected() {
        return (client) -> {
            String room = client.getHandshakeData().getSingleUrlParam("room");
            client.joinRoom(room);
            log.info("Socket ID[{}]  Connected to room {}", client.getSessionId().toString(), room);
        };

    }

    private DisconnectListener onDisconnected() {
        return client -> {
            log.info("Client[{}] - Disconnected from socket", client.getSessionId().toString());
        };
    }

}


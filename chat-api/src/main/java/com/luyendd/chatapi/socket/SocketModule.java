package com.luyendd.chatapi.socket;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.luyendd.chatapi.message.Message;
import com.luyendd.chatapi.message.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class SocketModule {
    @Autowired
    private MessageRepository messageRepository;
    private final SocketIOServer server;
    private final SocketService socketService;

    public SocketModule(SocketIOServer server, SocketService socketService) {
        this.server = server;
        this.socketService = socketService;
        server.addConnectListener(onConnected());
        server.addDisconnectListener(onDisconnected());
        server.addEventListener("send_message", SocketMessage.class, onChatReceived());
    }

    private DataListener<SocketMessage> onChatReceived() {
        return (senderClient, data, ackSender) -> {
            log.info(data.toString());
            Message message = new Message(UUID.fromString(data.getGroupId()), Uuids.timeBased(), data.getMessage(), data.getUserId());
            messageRepository.insert(message);
            socketService.sendMessage(data.getGroupId(),"get_message", senderClient, message);
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


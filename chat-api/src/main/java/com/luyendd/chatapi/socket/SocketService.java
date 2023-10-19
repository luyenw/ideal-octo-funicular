package com.luyendd.chatapi.socket;

import com.corundumstudio.socketio.SocketIOClient;
import com.luyendd.chatapi.message.Message;
import com.luyendd.chatapi.message.MessageType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SocketService {

    public void sendMessage(String room, String eventName, SocketIOClient senderClient, Message message) {
        for (SocketIOClient client : senderClient.getNamespace().getRoomOperations(room).getClients()) {
            client.sendEvent(eventName, message);
        }
    }

}
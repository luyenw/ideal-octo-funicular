package com.luyendd.chatapi.socket;

import com.luyendd.chatapi.message.MessageType;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Data
public class SocketMessage {
    private String message;
    private String userId;
    private String groupId;
}
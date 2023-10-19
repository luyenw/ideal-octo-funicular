package com.luyendd.chatapi.message;

import lombok.*;
import org.springframework.data.cassandra.core.mapping.Column;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class LatestMessage {
    private UUID groupId;
    private String userId;
    private String message;
    private long at;
}

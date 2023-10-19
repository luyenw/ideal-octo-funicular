package com.luyendd.chatapi.message;

import lombok.*;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@ToString
@Data
public class MessageDTO {
    private String message;
    private String fromId;
    private String toId;
//    private Long user_id;
}


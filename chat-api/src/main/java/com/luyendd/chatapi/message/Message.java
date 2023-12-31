package com.luyendd.chatapi.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.UUID;

@AllArgsConstructor
@Table("messages_by_group")
@Data
@Getter
@Setter
public class Message {
    @PrimaryKeyColumn(name="group_id", type = PrimaryKeyType.PARTITIONED)
    private UUID groupId;
    @PrimaryKeyColumn(name="message_id", type = PrimaryKeyType.CLUSTERED)
    private UUID messageId;
    @Column("message")
    private String message;
    @Column("user_id")
    private String userId;
}

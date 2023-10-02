package com.luyendd.chatapi.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.UUID;

@AllArgsConstructor
@Table
@Data
@Getter
@Setter
public class Message {
    @PrimaryKey
    private UUID id;
    @Column("from_id")
    private Long from;
    @Column("to_id")
    private Long to;
    @Column("at")
    private Timestamp at;
    @Column("content")
    private String content;

}

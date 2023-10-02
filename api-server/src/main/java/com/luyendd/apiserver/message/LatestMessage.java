package com.luyendd.apiserver.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@AllArgsConstructor
@Table("latest_message")
@Data
@Getter
@Setter
public class LatestMessage {
    @Column("message_id")
    private UUID message_id;
    @PrimaryKey
    @Column("user_id")
    private Long user_id;
}

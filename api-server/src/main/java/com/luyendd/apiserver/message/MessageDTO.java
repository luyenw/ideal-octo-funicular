package com.luyendd.apiserver.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.Column;

import java.math.BigInteger;
import java.sql.Timestamp;

@AllArgsConstructor
@Getter
@Setter
public class MessageDTO {
    private Long from;
    private Long to;
    private Timestamp at;
    private String content;
}

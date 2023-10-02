package com.luyendd.chatapi.message;

import org.springframework.data.cassandra.repository.CassandraRepository;

import java.math.BigInteger;


public interface MessageRepository extends CassandraRepository<Message, BigInteger> {
}

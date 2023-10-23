package com.luyendd.chatapi.message;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;


@EnableCassandraRepositories
public interface MessageRepository extends CassandraRepository<Message, Double> {
    @Query("SELECT * FROM messages_by_group WHERE group_id=:group_id order by message_id desc limit 1;")
    Message findLatestMessage(@Param("group_id") UUID group_id);
    @Query("SELECT * FROM messages_by_group WHERE group_id=:group_id;")
    List<Message> findByGroupId(@Param("group_id") UUID group_id);
}

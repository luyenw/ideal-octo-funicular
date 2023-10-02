package com.luyendd.apiserver.message;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface MessageRepository extends CassandraRepository<Message, BigInteger> {
    @Query("select * from message where fromid=:from_id ALLOW FILTERING;")
    List<Message> findByFromId(@Param("from_id") Long from_id);

}

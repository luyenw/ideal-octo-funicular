package com.luyendd.chatapi.group;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

public interface GroupRepository extends CassandraRepository<Group, String> {
    @Query("select * from groups_by_user where user_id=:user_id;")
    List<Group> findByUserId(@Param("user_id") String user_id);
    @Query("select * from groups_by_user where group_id=:group_id allow filtering;")
    List<Group> getPs(@Param("group_id") UUID group_id);
}

package com.luyendd.apiserver.message;

import org.springframework.data.cassandra.repository.CassandraRepository;

public interface LatestMessageRepository extends CassandraRepository<LatestMessage, Long> {
}

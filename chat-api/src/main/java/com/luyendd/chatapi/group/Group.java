package com.luyendd.chatapi.group;


import com.luyendd.chatapi.message.Message;
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
import java.util.UUID;

@AllArgsConstructor
@Table("groups_by_user")
@Data
@Getter
@Setter
public class Group {
    @PrimaryKeyColumn(name = "group_id", type = PrimaryKeyType.CLUSTERED)
    private UUID groupId;
    @PrimaryKeyColumn(name = "user_id", type = PrimaryKeyType.PARTITIONED)
    private String userId;
}

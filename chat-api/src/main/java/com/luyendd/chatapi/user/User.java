package com.luyendd.chatapi.user;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {
    String id;
    String name;
    String given_name;
    String family_name;
    String picture;
    String locale;
}

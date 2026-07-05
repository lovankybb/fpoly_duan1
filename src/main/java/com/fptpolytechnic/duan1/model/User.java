package com.fptpolytechnic.duan1.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults( level = AccessLevel.PRIVATE)
@Builder
public class User {

    String id;
    String username;
    String password;
    String email;
    String phone;
    String address;
    String avatar;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}

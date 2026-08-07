package com.fptpolytechnic.duan1.model;

import com.fptpolytechnic.duan1.enums.MessageStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Message {

    Long id;
    String sender;
    String email;
    String title;
    String message;
    MessageStatus status;
    LocalDateTime createAt;

    public Date getCreatedDateForJsp() {
        if (this.createAt == null) return null;
        return Timestamp.valueOf(this.createAt);
    }

}

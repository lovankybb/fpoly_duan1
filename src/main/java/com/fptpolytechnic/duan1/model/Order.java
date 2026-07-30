package com.fptpolytechnic.duan1.model;


import com.fptpolytechnic.duan1.enums.OrderStatus;
import com.fptpolytechnic.duan1.enums.PaymentMethod;
import com.fptpolytechnic.duan1.enums.PaymentStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {

    Long id;
    String orderCode;

    String userId;
    String customerName;
    String customerPhone;
    String customerAddress;
    String customerNote;

    BigDecimal totalAmount;

    PaymentMethod paymentMethod;
    PaymentStatus paymentStatus;
    LocalDateTime paidAt;


    OrderStatus orderStatus;

    LocalDateTime createdAt;
    LocalDateTime updateAt;

    LocalDateTime cancelledAt;
    String cancelReason;
}

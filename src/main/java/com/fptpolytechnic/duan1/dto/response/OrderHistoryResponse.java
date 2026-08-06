package com.fptpolytechnic.duan1.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class OrderHistoryResponse {

    Long oderDetailId;
    String orderCode;
    String name;
    String version;
    String color;
    String imageUrl;
    String orderStatus;
    String paymentStatus;
    int quantity;
    double price;
    LocalDate createdAt;

}

package com.fptpolytechnic.duan1.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class OrderHistoryResponse {

    Long variantId;
    String orderCode;
    String orderDate;
    String status;
    Double totalAmount;
    String customerName;
    String customerPhone;
    String customerAddress;


}

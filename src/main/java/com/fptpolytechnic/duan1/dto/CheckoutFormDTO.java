package com.fptpolytechnic.duan1.dto;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CheckoutFormDTO {

    String customerName;
    String customerPhone;
    String customerAddress;
    String paymentMethod;
}

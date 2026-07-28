package com.fptpolytechnic.duan1.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetail {

    Long id;
    Long orderId;
    Long variantId;
    int quantity;
    BigDecimal price;
}

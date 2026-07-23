package com.fptpolytechnic.duan1.model;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Cart {

    Long id;
    Long variantId;
    String userId;
    int quantity;
}

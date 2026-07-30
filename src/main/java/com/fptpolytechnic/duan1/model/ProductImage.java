package com.fptpolytechnic.duan1.model;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults( level = AccessLevel.PRIVATE)
public class ProductImage {

    Long id;
    String imageUrl;
    Long productId;
}

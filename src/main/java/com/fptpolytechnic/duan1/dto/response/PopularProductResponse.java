package com.fptpolytechnic.duan1.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PopularProductResponse {
    String name;
    int stock;
    double price;
    int sold;
}

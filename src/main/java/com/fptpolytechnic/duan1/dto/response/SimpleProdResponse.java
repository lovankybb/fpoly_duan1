package com.fptpolytechnic.duan1.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SimpleProdResponse {
    Long id;
    String name;
    String description;
    Double price;
    Double salePrice;
    String brand;
    String category;
    String status;
    String image;

}

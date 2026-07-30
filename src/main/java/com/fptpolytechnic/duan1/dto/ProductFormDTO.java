package com.fptpolytechnic.duan1.dto;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductFormDTO {

    String id;
    String name;
    String description;
    String categoryId;
    String brandId;
    String price;
    String status;

}

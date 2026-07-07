package com.fptpolytechnic.duan1.model;


import com.fptpolytechnic.duan1.enums.ProductStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults( level = AccessLevel.PRIVATE)
public class Product {

    Long id;
    String name;
    String description;
    BigDecimal price;
    BigDecimal salePrice;
    ProductStatus status;
    Long categoryId;
    Long brandId;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

}

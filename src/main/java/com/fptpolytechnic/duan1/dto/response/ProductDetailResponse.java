package com.fptpolytechnic.duan1.dto.response;

import com.fptpolytechnic.duan1.enums.ProductStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDetailResponse {

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

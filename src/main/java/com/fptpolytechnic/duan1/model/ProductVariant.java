package com.fptpolytechnic.duan1.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductVariant {

    Long id;
    Long productId;
    Long versionId;
    Long colorId;
    BigDecimal price;
    int stock;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}

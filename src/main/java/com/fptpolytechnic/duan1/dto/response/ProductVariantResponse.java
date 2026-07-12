package com.fptpolytechnic.duan1.dto.response;


import com.fptpolytechnic.duan1.model.Color;
import com.fptpolytechnic.duan1.model.Version;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults( level = AccessLevel.PRIVATE)
public class ProductVariantResponse {
    Long id;
    Version version;
    Color color;
    BigDecimal price;
    int stock;
}

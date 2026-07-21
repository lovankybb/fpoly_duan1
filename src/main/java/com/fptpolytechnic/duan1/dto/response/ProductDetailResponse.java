package com.fptpolytechnic.duan1.dto.response;

import com.fptpolytechnic.duan1.model.ProductImage;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

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
    List<ProductImage> images;
    List<ProductVariantResponse> variants;
    String status;
    String category;
    String brand;
}

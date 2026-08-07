package com.fptpolytechnic.duan1.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CartItemResponse{
        Long id;
        Long variantId;
        String name;
        String colorName;
        String versionName;
        double price;
        int quantity;
        Long productId;
        String productName;
        String imageUrl;
}

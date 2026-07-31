package com.fptpolytechnic.duan1.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class OrderItemResponse {
    Long id;
    Long variantId;
    String imageUrl;
    String productName;
    String colorName;
    String versionName;
    int quantity;
    double price;
}

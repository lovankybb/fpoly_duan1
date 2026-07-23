package com.fptpolytechnic.duan1.dto.response;

import lombok.Builder;

@Builder
public record CheckoutItem(
        String imageUrl,
        String productName,
        String colorName,
        String versionName,
        int quantity,
        double price
) {
}

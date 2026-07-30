package com.fptpolytechnic.duan1.dto.response;


public record CartItemResponse(
        Long id,
        Long variantId,
        String name,
        String colorName,
        String versionName,
        double price,
        int quantity
) {

//    Long id;
//    Long variantId;
//    String name;
//    String colorName;
//    String versionName;
//    double price;
//    int quantity;
}

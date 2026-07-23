package com.fptpolytechnic.duan1.service;

import com.fptpolytechnic.duan1.repository.ProductRepository;

public class CartService {

    private final ProductRepository productRepository;

    public CartService() {
        this.productRepository = new ProductRepository();
    }

}

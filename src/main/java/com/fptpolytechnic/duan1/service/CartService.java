package com.fptpolytechnic.duan1.service;

import com.fptpolytechnic.duan1.dto.response.CartItemResponse;
import com.fptpolytechnic.duan1.dto.response.OrderItemResponse;
import com.fptpolytechnic.duan1.dto.response.ProductVariantResponse;
import com.fptpolytechnic.duan1.dto.response.SimpleProdResponse;
import com.fptpolytechnic.duan1.model.Cart;
import com.fptpolytechnic.duan1.repository.CarRepository;
import com.fptpolytechnic.duan1.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

public class CartService {

    private final ProductRepository productRepository;
    private final CarRepository cartRepository;
    private final ProductService productService;
    private final ProductVariantService productVariantService;

    public CartService() {
        this.productRepository = new ProductRepository();
        this.cartRepository = new CarRepository();
        this.productService = new ProductService();
        this.productVariantService = new ProductVariantService();
    }

    public void addToCart(String userId, Long variantId, int quantity) {

        if (quantity <= 0) {
            return;
        }

        Cart existing = cartRepository.findByUserIdAndVariantId(userId, variantId);

        if (existing != null) {
            int newQuantity = existing.getQuantity() + quantity;
            cartRepository.updateQuantity(userId, variantId, newQuantity);
        } else {
            Cart cart = Cart.builder()
                    .userId(userId)
                    .variantId(variantId)
                    .quantity(quantity)
                    .build();
            cartRepository.create(cart);
        }
    }

    public void updateQuantity(String userId, Long variantId, int quantity) {

        if (quantity <= 0) {
            cartRepository.deleteByUserIdAndVariantId(userId, variantId);
            return;
        }
        cartRepository.updateQuantity(userId, variantId, quantity);
    }

    public void removeItem(String userId, Long variantId) {
        cartRepository.deleteByUserIdAndVariantId(userId, variantId);
    }

    public void clearCart(String userId) {
        cartRepository.deleteByUserId(userId);
    }

    public List<CartItemResponse> getCartItems(String userId) {

        List<Cart> carts = cartRepository.findByUserId(userId);
        List<CartItemResponse> result = new ArrayList<>();

        for (Cart cart : carts) {

            ProductVariantResponse variant = productVariantService.findById(cart.getVariantId());
            if (variant == null) {
                continue;
            }

            SimpleProdResponse product = productService.findByProductVariantId(cart.getVariantId());
            if (product == null) {
                continue;
            }

            result.add(CartItemResponse.builder()
                    .id(cart.getId())
                    .variantId(cart.getVariantId())
                    .productId(product.getId())
                    .productName(product.getName())
                    .imageUrl(product.getImage())
                    .colorName(variant.getColor() != null ? variant.getColor().getName() : "")
                    .versionName(variant.getVersion() != null ? variant.getVersion().getName() : "")
                    .price(variant.getPrice().doubleValue())
                    .quantity(cart.getQuantity())
                    .build());
        }
        return result;
    }


    public List<OrderItemResponse> getOrderItems(String userId) {
        return this.getCartItems(userId).stream().map(this::toOrderItemResponse).toList();
    }

    private OrderItemResponse toOrderItemResponse(CartItemResponse item) {
        return OrderItemResponse.builder()
                .productName(item.getProductName())
                .variantId(item.getVariantId())
                .quantity(item.getQuantity())
                .price(item.getPrice())
                .imageUrl(item.getImageUrl())
                .colorName(item.getColorName())
                .versionName(item.getVersionName())
                .build();
    }

    public int countItems(String userId) {
        return cartRepository.findByUserId(userId).size();
    }
}
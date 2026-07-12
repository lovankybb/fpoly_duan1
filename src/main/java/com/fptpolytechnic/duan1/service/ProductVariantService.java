package com.fptpolytechnic.duan1.service;

import com.fptpolytechnic.duan1.dto.response.ProductVariantResponse;
import com.fptpolytechnic.duan1.model.Color;
import com.fptpolytechnic.duan1.model.ProductVariant;
import com.fptpolytechnic.duan1.model.Version;
import com.fptpolytechnic.duan1.repository.ProductVariantRepository;

import java.util.ArrayList;
import java.util.List;

public class ProductVariantService {

    private final ProductVariantRepository productVariantRepository;

    public ProductVariantService() {
        productVariantRepository = new ProductVariantRepository();
    }

    public List<ProductVariantResponse> getProductVariantsByProductId(Long productId, int offset) {

        if(offset < 0) {
            offset = 0;
        }

        return this.productVariantRepository.findAll(productId, offset, 10)
                .stream()
                .map(this::toProductVariantResponse)
                .toList();
    }

    public ProductVariant create(ProductVariant productVariant) {
        return this.productVariantRepository.create(productVariant);
    }


    public ProductVariant update(ProductVariant productVariant) {
        return this.productVariantRepository.update(productVariant);
    }

    public void delete(Long productVariantId) {
        this.productVariantRepository.delete(productVariantId);
    }

    public void deleteByProductId(Long productId) {
        this.productVariantRepository.deleteByProductId(productId);
    }

    public ProductVariantResponse findById(Long productVariantId) {
        return this.toProductVariantResponse(this.productVariantRepository.findById(productVariantId));
    }

    public ProductVariantResponse toProductVariantResponse(ProductVariant productVariant) {

        List<Version> versions = new ArrayList<>();
        versions.add(new Version(1, "256Gb"));
        versions.add(new Version(2, "12-256Gb"));
        versions.add(new Version(3, "128Gb"));

        List<Color> colors = new ArrayList<>();
        colors.add(new Color(1, "Orange", "#fb542b"));
        colors.add(new Color(2, "Pink", "#fb578e"));
        colors.add(new Color(3, "Black", "#000000"));

        Version version = versions.stream().filter(v -> v.getId() == productVariant.getVersionId()).findFirst().orElse(null);
        Color color = colors.stream().filter(c -> c.getId() == productVariant.getColorId()).findFirst().orElse(null);

        return ProductVariantResponse.builder()
                .id(productVariant.getId())
                .version(version)
                .color(color)
                .price(productVariant.getPrice())
                .stock(productVariant.getStock())
                .build();
    }
}

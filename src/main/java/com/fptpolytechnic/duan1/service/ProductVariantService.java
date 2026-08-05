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
    private final VersionService versionService;
    private final ColorService colorService;

    public ProductVariantService() {
        productVariantRepository = new ProductVariantRepository();
        versionService = new VersionService();
        colorService = new ColorService();
    }

    public List<ProductVariantResponse> getProductVariantsByProductId(Long productId, int offset, int limit) {

        if(offset < 0) {
            offset = 0;
        }

        return this.productVariantRepository.findAll(productId, offset, limit )
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

        List<Version> versions = versionService.getAll();
        List<Color> colors = colorService.getAll();

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

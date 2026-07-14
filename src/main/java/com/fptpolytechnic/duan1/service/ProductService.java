package com.fptpolytechnic.duan1.service;


import com.fptpolytechnic.duan1.dto.response.ProductDetailResponse;
import com.fptpolytechnic.duan1.dto.response.ProductVariantResponse;
import com.fptpolytechnic.duan1.dto.response.SimpleProdResponse;
import com.fptpolytechnic.duan1.model.Product;
import com.fptpolytechnic.duan1.model.ProductImage;
import com.fptpolytechnic.duan1.model.ProductVariant;
import com.fptpolytechnic.duan1.repository.ProductRepository;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class ProductService {


    ProductRepository productRepository;
    ProductImageService productImageService;
    ProductVariantService productVariantService;

    public ProductService() {
        productRepository = new ProductRepository();
        productImageService = new ProductImageService();
        productVariantService = new ProductVariantService();
    }

    public Product create(Product product, Collection<Part> images) {
        Product savedProduct = productRepository.create(product);

        images.forEach(img -> {
            if ("image".equals(img.getName())) {
                try {
                    productImageService.insert(savedProduct.getId(), img);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return savedProduct;
    }


    public Product update(Product product, Collection<Part> images) throws IOException {


        images.forEach(img -> {
            System.out.println("Name: " + img.getName());
            System.out.println("ContentType: " + img.getContentType());
            System.out.println("SubmittedName: " + img.getSubmittedFileName());
        });

        System.out.println("image size: " + images.size());

        if (images.size() > 0 && !images.isEmpty()) {
            productImageService.delete(product.getId());
            images.forEach(img -> {
                if ("image".equals(img.getName())) {
                    try {
                        productImageService.insert(product.getId(), img);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }

        return productRepository.update(product);
    }

    public List<SimpleProdResponse> getAll(int offset) {
        if (offset < 0) {
            offset = 0;
        }
        return productRepository.findAll(offset, 10).stream().map(this::toSimpleProdResponse).toList();
    }


    public void delete(Long id) throws IOException {

        productImageService.delete(id);
        productVariantService.deleteByProductId(id);
        productRepository.delete(id);
    }


    public Product findById(Long id) {
        return productRepository.findById(id);
    }


    public SimpleProdResponse getSimpleProdResponse(Long id) {
        return this.toSimpleProdResponse(productRepository.findById(id));
    }

    public List<SimpleProdResponse> findAllActiveProduct(int offSet) {


        if (offSet < 0) {
            offSet = 0;
        }

        return productRepository.findAllActiveProduct(offSet, 20).stream().map(this::toSimpleProdResponse).toList();
    }


    public List<SimpleProdResponse> findNewestProducts() {
        return this.productRepository.findNewestProducts().stream().map(this::toSimpleProdResponse).toList();
    }


    public ProductDetailResponse getProductDetail(Long id ){
        return this.toProductDetailResponse(productRepository.findById(id));
    }


    private ProductDetailResponse toProductDetailResponse(Product product) {

        ProductDetailResponse productDetailResponse = new ProductDetailResponse();
        productDetailResponse.setId(product.getId());
        productDetailResponse.setName(product.getName());
        productDetailResponse.setDescription(product.getDescription());

        productDetailResponse.setBrand("Brand");
        productDetailResponse.setCategory("Category");

        List<ProductImage> images = productImageService.findByProdId(product.getId());
        productDetailResponse.setImages(images);

        List<ProductVariantResponse> variants = productVariantService.getProductVariantsByProductId(product.getId(), 0, 100);
        productDetailResponse.setVariants(variants);

        return productDetailResponse;
    }


    private SimpleProdResponse toSimpleProdResponse(Product product) {

        List<ProductImage> productImages = productImageService.findByProdId(product.getId());

        String mainImg = "";
        if (!productImages.isEmpty()) {
            mainImg = productImages.get(0).getImageUrl();
        }

        double salePrice = Objects.isNull(product.getSalePrice())
                ? product.getPrice().doubleValue() : product.getSalePrice().doubleValue();

        return SimpleProdResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .image(mainImg)
                .price(product.getPrice().doubleValue())
                .salePrice(salePrice)
                .status(product.getStatus().name())
                .build();

    }
}

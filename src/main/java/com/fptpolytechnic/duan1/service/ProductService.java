package com.fptpolytechnic.duan1.service;


import com.fptpolytechnic.duan1.dto.response.ProductDetailResponse;
import com.fptpolytechnic.duan1.dto.response.ProductVariantResponse;
import com.fptpolytechnic.duan1.dto.response.SimpleProdResponse;
import com.fptpolytechnic.duan1.model.Brand;
import com.fptpolytechnic.duan1.model.Category;
import com.fptpolytechnic.duan1.model.Product;
import com.fptpolytechnic.duan1.model.ProductImage;
import com.fptpolytechnic.duan1.repository.BrandRepository;
import com.fptpolytechnic.duan1.repository.CategoryRepository;
import com.fptpolytechnic.duan1.repository.ProductRepository;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProductService {


    private final ProductRepository productRepository;
    private final ProductImageService productImageService;
    private final ProductVariantService productVariantService;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;


    public ProductService() {
        productRepository = new ProductRepository();
        productImageService = new ProductImageService();
        productVariantService = new ProductVariantService();
        brandRepository = new BrandRepository();
        categoryRepository = new CategoryRepository();
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
        return productRepository.findAll(offset, 10).stream().map(this::toSimpleProdResponse).collect(Collectors.toList());
    }


    public void delete(Long id) throws IOException {
        productImageService.delete(id);
        productVariantService.deleteByProductId(id);
        productRepository.delete(id);
    }


    public SimpleProdResponse findById(Long id) {
        return this.toSimpleProdResponse(productRepository.findById(id));
    }


    public SimpleProdResponse getSimpleProdResponse(Long id) {
        return this.toSimpleProdResponse(productRepository.findById(id));
    }

    public List<SimpleProdResponse> findAllActiveProduct(int offSet, String categoryId, String brandId, String partName) {


        System.out.println("offSet: " + offSet + ", categoryId: " + categoryId + ", brandId: " + brandId + ", partName: " + partName);
        if (offSet < 0) {
            offSet = 0;
        }

        return productRepository.findAllActiveProduct(offSet, 20, categoryId, brandId, partName).stream().map(this::toSimpleProdResponse).toList();
    }


    public List<SimpleProdResponse> findNewestProducts() {
        return this.productRepository.findNewestProducts().stream().map(this::toSimpleProdResponse).toList();
    }


    public ProductDetailResponse getProductDetail(Long id) {
        return this.toProductDetailResponse(productRepository.findById(id));
    }


    public List<Product> findByBrandId(Long brandId) {
        return productRepository.findByBrandId(brandId);
    }

    public List<Product> findByCategoryId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }


    public SimpleProdResponse findByProductVariantId(Long productVariantId) {
        return this.toSimpleProdResponse(this.productRepository.findProductByVariantId(productVariantId));
    }


    private ProductDetailResponse toProductDetailResponse(Product product) {

        ProductDetailResponse productDetailResponse = new ProductDetailResponse();
        productDetailResponse.setId(product.getId());
        productDetailResponse.setName(product.getName());
        productDetailResponse.setDescription(product.getDescription());

        Brand brand = brandRepository.getById(Math.toIntExact(product.getBrandId()));
        Category category = categoryRepository.findById(product.getCategoryId());

        productDetailResponse.setBrand(brand != null ? brand.getName() : "Brand");
        productDetailResponse.setCategory(category != null ? category.getName() : "Category");

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
        Brand brand = brandRepository.getById(Math.toIntExact(product.getBrandId()));
        Category category = categoryRepository.findById(product.getCategoryId());
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
                .brand(brand.getName())
                .category(category.getName())
                .build();

    }


}

package com.fptpolytechnic.duan1.service;

import com.fptpolytechnic.duan1.model.ProductImage;
import com.fptpolytechnic.duan1.repository.ProductImageRepository;
import com.fptpolytechnic.duan1.utils.StorageService;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.util.List;

public class ProductImageService {


    private ProductImageRepository productImageRepository;
    private StorageService storageService;

    public ProductImageService(){
        productImageRepository = new ProductImageRepository();
        storageService = new StorageService();
    }


    public void insert(Long prodId, Part file) throws IOException {

        String imageUrl = storageService.storage(file);
        if(imageUrl != null){
            ProductImage productImage = new ProductImage();
            productImage.setProductId(prodId);
            productImage.setImageUrl(imageUrl);
            productImageRepository.create(productImage);
        }
    }

    public void delete( Long productId) throws IOException {

        List<ProductImage> productImages = productImageRepository.findByProductId(productId);
        for (ProductImage productImage : productImages) {
            storageService.delete(productImage.getImageUrl());
        }
        productImageRepository.deleteByProductId(productId);
    }

    public List<ProductImage> findByProdId(Long prodId){
        return productImageRepository.findByProductId(prodId);
    }

}

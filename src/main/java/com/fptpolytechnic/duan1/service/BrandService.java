package com.fptpolytechnic.duan1.service;

import com.fptpolytechnic.duan1.model.Brand;
import com.fptpolytechnic.duan1.model.Product;
import com.fptpolytechnic.duan1.repository.BrandRepository;
import com.fptpolytechnic.duan1.repository.ProductRepository;

import java.io.IOException;
import java.util.List;

public class BrandService {

    private final BrandRepository repository = new BrandRepository();
    private ProductService productService = new ProductService();

    public List<Brand> getAll() { return repository.getAll(); }
    public Brand getById(int id) { return repository.getById(id); }
    public void add(Brand b) { repository.add(b); }
    public void update(Brand b) { repository.update(b); }

    public void delete(long id) throws IOException {
        List<Product> products = productService.findByBrandId(id);
        for (Product product : products) {
            productService.delete(product.getId());
        }
        repository.delete(id);
    }
}
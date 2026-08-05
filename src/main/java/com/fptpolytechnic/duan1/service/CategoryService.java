package com.fptpolytechnic.duan1.service;

import com.fptpolytechnic.duan1.model.Category;
import com.fptpolytechnic.duan1.model.Product;
import com.fptpolytechnic.duan1.repository.CategoryRepository;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class CategoryService {
    private final CategoryRepository repository = new CategoryRepository();
    private final ProductService productService = new ProductService();

    public List<Category> getAll() {
        List<Category> list = repository.getAll();

        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }

        list.sort((c1, c2) -> Long.compare(c2.getId(), c1.getId()));
        return list;
    }


    public Category getById(Long id) {
       return this.repository.findById(id);
    }

    public void add(Category c) {
        repository.add(c);
    }

    public void delete(long id) throws IOException {
        List<Product> products = productService.findByCategoryId(id);
        for (Product product : products) {
            productService.delete(product.getId());
        }
        repository.delete(id);
    }
}

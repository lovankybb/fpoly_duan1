package com.fptpolytechnic.duan1.service;

import com.fptpolytechnic.duan1.model.Color;
import com.fptpolytechnic.duan1.repository.ColorRepository;
import com.fptpolytechnic.duan1.repository.ProductVariantRepository;

import java.util.List;

public class ColorService {
    private final ColorRepository repository = new ColorRepository();
    private final ProductVariantRepository productVariantRepository = new ProductVariantRepository();

    public List<Color> getAll() { return repository.getAll(); }
    public Color getById(int id) { return repository.getById(id); }
    public void add(Color c) { repository.add(c); }
    public void update(Color c) { repository.update(c); }
    public void delete(long id) {
        productVariantRepository.deleteByColorId((long) id);
        repository.delete(id);
    }
}


package com.fptpolytechnic.duan1.service;

import com.fptpolytechnic.duan1.model.Brand;
import com.fptpolytechnic.duan1.repository.BrandRepository;
import java.util.List;

public class BrandService {

    private final BrandRepository repository = new BrandRepository();

    public List<Brand> getAll() { return repository.getAll(); }
    public Brand getById(int id) { return repository.getById(id); }
    public void add(Brand b) { repository.add(b); }
    public void update(Brand b) { repository.update(b); }
    public void delete(int id) { repository.delete(id); }
}
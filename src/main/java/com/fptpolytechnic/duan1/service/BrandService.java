package com.fptpolytechnic.duan1.service;

import com.fptpolytechnic.duan1.model.Brand;
import com.fptpolytechnic.duan1.repository.BrandRepository;
import com.fptpolytechnic.duan1.utils.DBContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BrandService {
    private final BrandRepository repository = new BrandRepository();

    public List<Brand> getAll() {
        List<Brand> list = repository.getAll();
        list.sort((b1, b2) -> Integer.compare(b2.getId(), b1.getId()));
        return list;
    }

    public void add(Brand b) {
        repository.add(b);
    }
    public void delete(int id) {
        repository.delete(id);
    }

}

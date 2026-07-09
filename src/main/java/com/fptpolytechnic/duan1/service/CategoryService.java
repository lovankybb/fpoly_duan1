package com.fptpolytechnic.duan1.service;

import com.fptpolytechnic.duan1.model.Category;
import com.fptpolytechnic.duan1.repository.CategoryRepository;
import com.fptpolytechnic.duan1.utils.DBContext;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class CategoryService {
    private  final CategoryRepository repository = new CategoryRepository();

    public List<Category> getAll() {
        List<Category> list = repository.getAll();

        list.sort((c1, c2) -> Long.compare(c2.getId(), c1.getId()));
        return list;
    }


   public void add(Category c) {
       repository.add(c);
   }
   public void delete(int id) {
       repository.delete(id);
   }
}

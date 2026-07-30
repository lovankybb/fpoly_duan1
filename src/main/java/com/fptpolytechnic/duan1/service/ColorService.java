package com.fptpolytechnic.duan1.service;

import com.fptpolytechnic.duan1.model.Color;
import com.fptpolytechnic.duan1.repository.ColorRepository;
import com.fptpolytechnic.duan1.utils.DBContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ColorService {
    private final ColorRepository repository = new ColorRepository();

    public List<Color> getAll() {
        List<Color> list = repository.getAll();
        list.sort((c1, c2) -> Integer.compare(c2.getId(), c1.getId()));
        return list;

    }
    public void add(Color c) {
        repository.add(c);
    }

    public void delete(int id) {
        repository.delete(id);
    }
}

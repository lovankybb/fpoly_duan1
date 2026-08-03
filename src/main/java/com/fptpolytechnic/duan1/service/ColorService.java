package com.fptpolytechnic.duan1.service;

import com.fptpolytechnic.duan1.model.Color;
import com.fptpolytechnic.duan1.repository.ColorRepository;
import java.util.List;

public class ColorService {
    private final ColorRepository repository = new ColorRepository();

    public List<Color> getAll() { return repository.getAll(); }
    public Color getById(int id) { return repository.getById(id); }
    public void add(Color c) { repository.add(c); }
    public void update(Color c) { repository.update(c); }
    public void delete(int id) { repository.delete(id); }
}


package com.fptpolytechnic.duan1.service;

import com.fptpolytechnic.duan1.model.Version;
import com.fptpolytechnic.duan1.repository.VersionRepository;

import java.util.List;

public class Versionervice {
    private final VersionRepository repository = new VersionRepository();

    public List<Version> getAll() {
        List<Version> list = repository.getAll();
        list.sort((v1,v2) -> Integer.compare(v2.getId(), v1.getId()));
        return list;
    }
    public void add(Version v) {
        repository.add(v);
    }
    public void delete(int id) {
        repository.delete(id);
    }
}

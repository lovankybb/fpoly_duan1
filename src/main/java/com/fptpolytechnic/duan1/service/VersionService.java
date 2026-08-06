package com.fptpolytechnic.duan1.service;

import com.fptpolytechnic.duan1.model.Version;
import com.fptpolytechnic.duan1.repository.ProductVariantRepository;
import com.fptpolytechnic.duan1.repository.VersionRepository;
import java.util.List;

public class VersionService {
    private final VersionRepository repository = new VersionRepository();
    private final ProductVariantRepository productVariantRepository = new ProductVariantRepository();

    public List<Version> getAll() { return repository.getAll(); }
    public Version getById(int id) { return repository.getById(id); }
    public void add(Version v) { repository.add(v); }
    public void update(Version v) { repository.update(v); }
    public void delete(long id) {
        productVariantRepository.deleteByVersionId((long) id);
        repository.delete(id);  
    }
}

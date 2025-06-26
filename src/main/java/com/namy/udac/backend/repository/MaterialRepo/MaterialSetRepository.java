package com.namy.udac.backend.repository.MaterialRepo;

import java.util.List;

import com.namy.udac.backend.model.learningMaterial.MaterialSet;

public interface MaterialSetRepository {
    MaterialSet addSet(MaterialSet set);
    int findMaxIdSet();
    MaterialSet findById(String id);
    List<MaterialSet> findAll();
    MaterialSet update(String id, MaterialSet set);
    int deleteById(String id);
}
    

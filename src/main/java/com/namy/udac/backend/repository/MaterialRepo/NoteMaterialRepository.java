package com.namy.udac.backend.repository.MaterialRepo;

import java.util.List;

import com.namy.udac.backend.model.learningMaterial.NoteMaterial;

public interface NoteMaterialRepository {
    int findMaxId();
    NoteMaterial add(NoteMaterial noteMaterial);
    NoteMaterial findById(String id);
    List<NoteMaterial> findBySection(String idSection);
    List<NoteMaterial> findAll();
    NoteMaterial update(String id, NoteMaterial noteMaterial);
    int deleteById(String id);
}
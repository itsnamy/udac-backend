package com.namy.udac.backend.repository.MaterialRepo;

import java.util.List;

import com.namy.udac.backend.model.learningMaterial.SubtopicSection;

public interface SubtopicMaterialRepository {
    int findMaxId();
    SubtopicSection add(SubtopicSection section);
    SubtopicSection findById(String id);
    List<SubtopicSection> findByMaterialSets(String idSet);
    List<SubtopicSection> findAll();
    SubtopicSection update(String id, SubtopicSection section);
    int deleteById(String id);
}

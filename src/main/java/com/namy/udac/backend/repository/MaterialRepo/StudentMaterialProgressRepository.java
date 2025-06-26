package com.namy.udac.backend.repository.MaterialRepo;

import java.util.List;

import com.namy.udac.backend.model.learningMaterial.StudentMaterialProgress;

public interface StudentMaterialProgressRepository {
    int findMaxId();
    StudentMaterialProgress add(StudentMaterialProgress progress);
    List<StudentMaterialProgress> findByStudent(String studentId);
    List<StudentMaterialProgress> findByMaterialSet(String studentId, String materialSetId);
    boolean isMaterialCompleted(String studentId, String materialId);
    int deleteById(String id); 
} 
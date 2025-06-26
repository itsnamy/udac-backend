package com.namy.udac.backend.service.materialService;

import com.namy.udac.backend.model.learningMaterial.StudentMaterialProgress;
import com.namy.udac.backend.model.learningMaterial.DTOMaterial.MaterialBySubtopic;
import com.namy.udac.backend.model.learningMaterial.DTOMaterial.SubtopicBySet;
import com.namy.udac.backend.model.learningMaterial.DTOMaterial.MaterialCompletion;
import com.namy.udac.backend.model.learningMaterial.MaterialSet;
import com.namy.udac.backend.repository.MaterialRepo.StudentMaterialProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentMaterialProgressService {

    @Autowired
    private StudentMaterialProgressRepository progressRepository;

    @Autowired
    private MaterialSetService materialSetService;

    public StudentMaterialProgress addProgress(String studentId, String materialSetId, String materialId, String materialType) {
        if (progressRepository.isMaterialCompleted(studentId, materialId)) {
            return null; // already completed
        }

        int maxId = progressRepository.findMaxId();
        String newId = "matprog" + String.format("%04d", maxId + 1);

        StudentMaterialProgress progress = new StudentMaterialProgress();
        progress.setIdMaterialProgress(newId);
        progress.setStudentId(studentId);
        progress.setMaterialSetId(materialSetId);
        progress.setMaterialId(materialId);
        progress.setMaterialType(materialType);
        progress.setCompletedAt(new Timestamp(System.currentTimeMillis()));

        return progressRepository.add(progress);
    }

    public boolean isCompleted(String studentId, String materialId) {
        return progressRepository.isMaterialCompleted(studentId, materialId);
    }

    public List<StudentMaterialProgress> getProgressByMaterialSet(String studentId, String materialSetId) {
        return progressRepository.findByMaterialSet(studentId, materialSetId);
    }

    public MaterialCompletion getMaterialCompletion(String studentId) {
        List<MaterialSet> allSets = materialSetService.getAll();
        Set<String> allMaterialIds = new LinkedHashSet<>();

        for (MaterialSet set : allSets) {
            SubtopicBySet subtopicData = materialSetService.getSubtopicsBySet(set.getIdMaterialSet());
            for (MaterialBySubtopic m : subtopicData.getSubtopicSections()) {
                if (m.getVideoMaterial() != null) allMaterialIds.add(m.getVideoMaterial().getIdVideo());
                if (m.getNoteMaterial() != null) allMaterialIds.add(m.getNoteMaterial().getIdMaterialNote());
                if (m.getExerciseBySet() != null) allMaterialIds.add(m.getExerciseBySet().getExerciseSet().getIdExerciseSet());
            }
        }

        List<StudentMaterialProgress> completed = progressRepository.findByStudent(studentId);
        Set<String> completedIds = completed.stream().map(StudentMaterialProgress::getMaterialId).collect(Collectors.toSet());

        long completedCount = allMaterialIds.stream().filter(completedIds::contains).count();
        double percentage = (allMaterialIds.isEmpty()) ? 0 : (completedCount * 100.0) / allMaterialIds.size();

        String nextMaterialId = null;
        for (String id : allMaterialIds) {
            if (!completedIds.contains(id)) {
                nextMaterialId = id;
                break;
            }
        }

        return new MaterialCompletion(percentage, nextMaterialId);
    }
} 

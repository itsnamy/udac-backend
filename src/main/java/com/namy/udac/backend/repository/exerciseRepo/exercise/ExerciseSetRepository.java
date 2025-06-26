package com.namy.udac.backend.repository.exerciseRepo.exercise;

import java.util.List;

import com.namy.udac.backend.model.exercise.ExerciseSet;

public interface ExerciseSetRepository {
    int findMaxId();
    ExerciseSet add(ExerciseSet exerciseSet);
    ExerciseSet findById(String id);
    List<ExerciseSet> findSetsBySection(String sectionTitle);
    List<ExerciseSet> findBySectionAndType(String sectionTitle, String exerciseType);
    List<ExerciseSet> findByOwner(String owner);
    List<ExerciseSet> findByType(String exerciseType);
    List<ExerciseSet> findByTypeAndOwner(String exerciseType, String owner);
    List<ExerciseSet> findAll();
    ExerciseSet update(String id, ExerciseSet exerciseSet);
    int deleteById(String idSet);
}

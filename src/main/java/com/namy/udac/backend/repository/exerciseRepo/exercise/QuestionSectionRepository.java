package com.namy.udac.backend.repository.exerciseRepo.exercise;

import java.util.List;

import com.namy.udac.backend.model.exercise.QuestionSection;

public interface QuestionSectionRepository {
    int findMaxId();
    QuestionSection add(QuestionSection questionSection);
    QuestionSection findById(String id);
    List<QuestionSection> findByExerciseSets(String idSet);
    List<QuestionSection> findAll();
    QuestionSection update(String id, QuestionSection questionSection);
    int deleteById(String id);
}

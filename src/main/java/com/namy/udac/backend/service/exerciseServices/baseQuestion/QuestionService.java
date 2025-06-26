package com.namy.udac.backend.service.exerciseServices.baseQuestion;

import java.util.List;

import com.namy.udac.backend.model.exercise.question.Question;

public interface QuestionService<T extends Question> {
    T add(T question);
    T findById(String id);
    List<T> findBySectionId(String sectionId);
    T update(T updated);
    int deleteById(String id);
}

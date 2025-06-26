package com.namy.udac.backend.repository.exerciseRepo.question;

import java.util.List;

import com.namy.udac.backend.model.exercise.question.MCQQuestion;

public interface MCQQuestionRepository {
    int findMaxId();
    MCQQuestion add(MCQQuestion mcq);
    MCQQuestion findById(String id);
    List<MCQQuestion> findBySectionId(String idSection);
    MCQQuestion update(String id,  MCQQuestion mcqQuestion);
    int deleteById(String id);
}

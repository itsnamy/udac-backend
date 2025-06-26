package com.namy.udac.backend.repository.exerciseRepo.question;

import java.util.List;

import com.namy.udac.backend.model.exercise.question.DNDQuestion;

public interface DNDQuestionRepository {
    int findMaxId();
    DNDQuestion add(DNDQuestion dnd);
    DNDQuestion findById(String id);
    List<DNDQuestion> findBySectionId(String idSection);
    DNDQuestion update(String id, DNDQuestion dnd);
    int deleteById(String id);
}

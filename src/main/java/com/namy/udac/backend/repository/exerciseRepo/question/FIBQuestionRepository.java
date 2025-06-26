package com.namy.udac.backend.repository.exerciseRepo.question;

import java.util.List;

import com.namy.udac.backend.model.exercise.question.FIBQuestion;

public interface FIBQuestionRepository {
    int findMaxId();
    FIBQuestion add(FIBQuestion fib);
    FIBQuestion findById(String id);
    List<FIBQuestion> findBySectionId(String idSection);
    FIBQuestion update(String id, FIBQuestion fib);
    int deleteById(String id);
}

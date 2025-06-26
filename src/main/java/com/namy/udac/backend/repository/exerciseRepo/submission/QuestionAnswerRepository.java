package com.namy.udac.backend.repository.exerciseRepo.submission;

import java.util.List;

import com.namy.udac.backend.model.exercise.exerciseSubmission.QuestionAnswer;

public interface QuestionAnswerRepository {
    int findMaxId();
    QuestionAnswer add(QuestionAnswer answer);
    List<QuestionAnswer> findBySubmissionId(String submissionId);
}

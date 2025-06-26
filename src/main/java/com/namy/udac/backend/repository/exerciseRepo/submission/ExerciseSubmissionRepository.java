package com.namy.udac.backend.repository.exerciseRepo.submission;

import java.util.List;

import com.namy.udac.backend.model.exercise.exerciseSubmission.ExerciseSubmission;

public interface ExerciseSubmissionRepository {
    int findMaxId();
    ExerciseSubmission add(ExerciseSubmission submission);
    ExerciseSubmission findById(String id);
    List<ExerciseSubmission> findByStudentAndExerciseSet(String studentId, String exerciseSetId);
    ExerciseSubmission update(ExerciseSubmission submission, String id);
}

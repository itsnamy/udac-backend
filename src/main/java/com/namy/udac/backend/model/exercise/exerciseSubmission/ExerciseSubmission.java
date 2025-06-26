package com.namy.udac.backend.model.exercise.exerciseSubmission;

import java.time.LocalDateTime;

public class ExerciseSubmission {
    private String idSubmission;
    private String idExerciseSet;
    private String studentId;
    private LocalDateTime submittedAt;
    private int totalScore;
    private int maxScore;

    public ExerciseSubmission() {
    }

    public ExerciseSubmission(ExerciseSubmission exerciseSubmission) {
        this.idSubmission = exerciseSubmission.idSubmission;
        this.idExerciseSet = exerciseSubmission.idExerciseSet;
        this.studentId = exerciseSubmission.studentId;
        this.submittedAt = exerciseSubmission.submittedAt;
        this.totalScore = exerciseSubmission.totalScore;
        this.maxScore = exerciseSubmission.maxScore;
    }

    public ExerciseSubmission(String idSubmission, String idExerciseSet, String studentId, LocalDateTime submittedAt, int totalScore, int maxScore) {
        this.idSubmission = idSubmission;
        this.idExerciseSet = idExerciseSet;
        this.studentId = studentId;
        this.submittedAt = submittedAt;
        this.totalScore = totalScore;
        this.maxScore = maxScore;
    }

    public String getIdSubmission() {
        return idSubmission;
    }

    public void setIdSubmission(String idSubmission) {
        this.idSubmission = idSubmission;
    }

    public String getIdExerciseSet() {
        return idExerciseSet;
    }

    public void setIdExerciseSet(String idExerciseSet) {
        this.idExerciseSet = idExerciseSet;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }
}

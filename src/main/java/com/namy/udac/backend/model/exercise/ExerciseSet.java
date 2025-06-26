package com.namy.udac.backend.model.exercise;

import java.time.LocalDateTime;

public class ExerciseSet {
    private String idExerciseSet;
    private String exerciseSection;
    private ExerciseType exerciseType;
    private String exerciseTitle;
    private String exerciseDesc;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String ownedBy;

    public enum ExerciseType{
        QUIZ, THEMATIC, CUMULATIVE
    }

    public ExerciseSet() {
    }

    public ExerciseSet(ExerciseSet exerciseSet) {
        this.idExerciseSet = exerciseSet.idExerciseSet;
        this.exerciseSection = exerciseSet.exerciseSection;
        this.exerciseType = exerciseSet.exerciseType;
        this.exerciseTitle = exerciseSet.exerciseTitle;
        this.exerciseDesc = exerciseSet.exerciseDesc;
        this.createdAt = exerciseSet.createdAt;
        this.updatedAt = exerciseSet.updatedAt;
        this.ownedBy = exerciseSet.ownedBy;
    }

    public ExerciseSet(String idExerciseSet, String exerciseSection, ExerciseType exerciseType, String exerciseTitle, String exerciseDesc, LocalDateTime createdAt, LocalDateTime updatedAt, String ownedBy) {
        this.idExerciseSet = idExerciseSet;
        this.exerciseSection = exerciseSection;
        this.exerciseType = exerciseType;
        this.exerciseTitle = exerciseTitle;
        this.exerciseDesc = exerciseDesc;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.ownedBy = ownedBy;
    }

    public String getIdExerciseSet() {
        return idExerciseSet;
    }

    public void setIdExerciseSet(String idExerciseSet2) {
        this.idExerciseSet = idExerciseSet2;
    }

    public String getExerciseSection() {
        return exerciseSection;
    }

    public void setExerciseSection(String exerciseSection) {
        this.exerciseSection = exerciseSection;
    }

    public ExerciseType getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(ExerciseType exerciseType) {
        this.exerciseType = exerciseType;
    }

    public String getExerciseTitle() {
        return exerciseTitle;
    }

    public void setExerciseTitle(String exerciseTitle) {
        this.exerciseTitle = exerciseTitle;
    }

    public String getExerciseDesc() {
        return exerciseDesc;
    }

    public void setExerciseDesc(String exerciseDesc) {
        this.exerciseDesc = exerciseDesc;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(String ownedBy) {
        this.ownedBy = ownedBy;
    }
}

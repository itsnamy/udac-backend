package com.namy.udac.backend.model.exercise.DTOexercise;

import java.util.List;

import com.namy.udac.backend.model.exercise.ExerciseSet;

public class SectionWithExercise {
    private String exerciseSection;
    List<ExerciseSet> exercises;

    public SectionWithExercise() {
    }

    public SectionWithExercise(String exerciseSection, List<ExerciseSet> exercises) {
        this.exerciseSection = exerciseSection;
        this.exercises = exercises;
    }

    public String getExerciseSection() {
        return exerciseSection;
    }

    public void setExerciseSection(String exerciseSection) {
        this.exerciseSection = exerciseSection;
    }

    public List<ExerciseSet> getExercises() {
        return exercises;
    }

    public void setExercises(List<ExerciseSet> exercises) {
        this.exercises = exercises;
    }
}

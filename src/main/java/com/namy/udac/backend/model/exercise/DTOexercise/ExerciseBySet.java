package com.namy.udac.backend.model.exercise.DTOexercise;

import java.util.List;

import com.namy.udac.backend.model.exercise.ExerciseSet;

public class ExerciseBySet {
    private ExerciseSet exerciseSet;
    private List<SectionWithQuestion> sections; 

    public ExerciseBySet() {
    }
    
    public ExerciseBySet(ExerciseSet exerciseSet, List<SectionWithQuestion> sections) {
        this.exerciseSet = exerciseSet;
        this.sections = sections;
    }

    public ExerciseSet getExerciseSet() {
        return exerciseSet;
    }

    public void setExerciseSet(ExerciseSet exerciseSet) {
        this.exerciseSet = exerciseSet;
    }

    public List<SectionWithQuestion> getQuestionSections() {
        return sections;
    }

    public void setQuestionSections(List<SectionWithQuestion> sections) {
        this.sections = sections;
    }
}

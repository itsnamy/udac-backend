package com.namy.udac.backend.model.exercise.DTOexercise;

import java.util.List;

import com.namy.udac.backend.model.exercise.QuestionSection;
import com.namy.udac.backend.model.exercise.question.Question;

public class SectionWithQuestion {

    private QuestionSection questionSection;
    private List<? extends Question> questions;
    
    public SectionWithQuestion() {
    }

    public SectionWithQuestion(QuestionSection questionSection, List<? extends Question> questions) {
        this.questionSection = questionSection;
        this.questions = questions;
    }

    public QuestionSection getQuestionSection() {
        return questionSection;
    }

    public void setQuestionSection(QuestionSection questionSection) {
        this.questionSection = questionSection;
    }

    public List<? extends Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    

}

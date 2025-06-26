package com.namy.udac.backend.model.exercise;

import com.namy.udac.backend.model.exercise.question.Question.QuestionType;

public class QuestionSection {
    private String idQuestionSec;
    private String exerciseSetId;
    private String sectionTitle;
    private QuestionType questionType;
    private int sectionOrder;

    public QuestionSection() {
    }

    public QuestionSection(QuestionSection questionSection) {
        this.idQuestionSec = questionSection.idQuestionSec;
        this.exerciseSetId = questionSection.exerciseSetId;
        this.sectionTitle = questionSection.sectionTitle;
        this.questionType = questionSection.questionType;
        this.sectionOrder = questionSection.sectionOrder;
    }
    
    public QuestionSection(String idQuestionSec, String exerciseSetId, String sectionTitle, QuestionType questionType, int sectionOrder) {
        this.idQuestionSec = idQuestionSec;
        this.exerciseSetId = exerciseSetId;
        this.sectionTitle = sectionTitle;
        this.questionType = questionType;
        this.sectionOrder = sectionOrder;
    }

    public String getIdQuestionSec() {
        return idQuestionSec;
    }

    public void setIdQuestionSec(String idQuestionSec) {
        this.idQuestionSec = idQuestionSec;
    }

    public String getExerciseSetId() {
        return exerciseSetId;
    }

    public void setExerciseSetId(String exerciseSetId) {
        this.exerciseSetId = exerciseSetId;
    }

    public String getSectionTitle() {
        return sectionTitle;
    }

    public void setSectionTitle(String sectionTitle) {
        this.sectionTitle = sectionTitle;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public int getSectionOrder() {
        return sectionOrder;
    }

    public void setSectionOrder(int sectionOrder) {
        this.sectionOrder = sectionOrder;
    }
}

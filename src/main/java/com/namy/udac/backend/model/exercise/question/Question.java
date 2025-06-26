package com.namy.udac.backend.model.exercise.question;

import java.sql.Blob;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "questionType",
  visible = true
)
@JsonSubTypes({
  @JsonSubTypes.Type(value = MCQQuestion.class, name = "MCQ"),
  @JsonSubTypes.Type(value = FIBQuestion.class, name = "FIB"),
  @JsonSubTypes.Type(value = DNDQuestion.class, name = "DND")
})

public abstract class Question {
    private String idQuestion;
    private String idQuestionSec;
    private QuestionType questionType;
    private String questionText;
    private Blob questionDiagram;
    private int point;

    public enum QuestionType {
        MCQ, FIB, DND
    }

    public Question() {
    }

    public Question(Question question) {
        this.idQuestion = question.idQuestion;
        this.idQuestionSec = question.idQuestionSec;
        this.questionType = question.questionType;
        this.questionText = question.questionText;
        this.questionDiagram = question.questionDiagram;
        this.point = question.point;
    }

    public Question(String idQuestion, String idQuestionSec, QuestionType questionType, String questionText, Blob questionDiagram, int point) {
        this.idQuestion = idQuestion;
        this.idQuestionSec = idQuestionSec;
        this.questionType = questionType;
        this.questionText = questionText;
        this.questionDiagram = questionDiagram;
        this.point = point;
    }

    public String getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(String idQuestion) {
        this.idQuestion = idQuestion;
    }

    public String getIdQuestionSec() {
        return idQuestionSec;
    }

    public void setIdQuestionSec(String idQuestionSec) {
        this.idQuestionSec = idQuestionSec;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public Blob getQuestionDiagram() {
        return questionDiagram;
    }

    public void setQuestionDiagram(Blob questionDiagram) {
        this.questionDiagram = questionDiagram;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}

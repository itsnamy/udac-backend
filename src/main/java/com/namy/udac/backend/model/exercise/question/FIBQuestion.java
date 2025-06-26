package com.namy.udac.backend.model.exercise.question;

import java.sql.Blob;

public class FIBQuestion extends Question{
    private String[] fibAnswer;
    private boolean isCaseSensitive;

    public FIBQuestion() {
        super();
    }

    public FIBQuestion(FIBQuestion fibQuestion) {
        super(fibQuestion);
        this.fibAnswer = fibQuestion.fibAnswer;
        this.isCaseSensitive = fibQuestion.isCaseSensitive;
    }

    public FIBQuestion(String idQuestion, String idExerciseSet, QuestionType questionType, String questionText, Blob questionDiagram, int point, String[] fibAnswer, boolean isCaseSensitive) {
        super(idQuestion, idExerciseSet, questionType, questionText, questionDiagram, point);
        this.fibAnswer = fibAnswer;
        this.isCaseSensitive = isCaseSensitive;
    }

    public String[] getFibAnswer() {
        return fibAnswer;
    }

    public void setFibAnswer(String[] fibAnswer) {
        this.fibAnswer = fibAnswer;
    }

    public boolean isCaseSensitive() {
        return isCaseSensitive;
    }

    public void setCaseSensitive(boolean isCaseSensitive) {
        this.isCaseSensitive = isCaseSensitive;
    }
}

package com.namy.udac.backend.model.exercise.exerciseSubmission;

import com.namy.udac.backend.model.exercise.question.Question.QuestionType;

public class QuestionAnswer {
    private String idAnswer;
    private String idSubmission;
    private String idQuestion;
    private QuestionType questionType;
    private String answer;
    private boolean isCorrect;
    private int score;
    private String feedback;
    private String correctAnswerXML;

    public QuestionAnswer() {
    } 

    public QuestionAnswer(String idAnswer, String idSubmission, String idQuestion, QuestionType questionType, String answer, boolean isCorrect, int score, String feedback) {
        this.idAnswer = idAnswer;
        this.idSubmission = idSubmission;
        this.idQuestion = idQuestion;
        this.questionType = questionType;
        this.answer = answer;
        this.isCorrect = isCorrect;
        this.score = score;
        this.feedback = feedback;
    }

    public String getIdAnswer() {
        return idAnswer;
    }

    public void setIdAnswer(String idAnswer) {
        this.idAnswer = idAnswer;
    }

    public String getIdSubmission() {
        return idSubmission;
    }

    public void setIdSubmission(String idSubmission) {
        this.idSubmission = idSubmission;
    }

    public String getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(String idQuestion) {
        this.idQuestion = idQuestion;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }
    
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getCorrectAnswerXML() {
        return correctAnswerXML;
    }

    public void setCorrectAnswerXML(String correctAnswerXML) {
        this.correctAnswerXML = correctAnswerXML;
    }
}

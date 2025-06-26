package com.namy.udac.backend.model.exercise.question;

import java.sql.Blob;

public class MCQQuestion extends Question {
    private String[] mcqOption;
    private int mcqAnsIndex;

    public MCQQuestion() {
        super();
    }

    public MCQQuestion(MCQQuestion mcqQuestion) {
        super(mcqQuestion);
        this.mcqOption = mcqQuestion.mcqOption;
        this.mcqAnsIndex = mcqQuestion.mcqAnsIndex;
    }

    public MCQQuestion(String idQuestion, String idExerciseSet, QuestionType questionType, String questionText, Blob questionDiagram, int point, String[] mcqOption, int mcqAnsIndex) {
        super(idQuestion, idExerciseSet, questionType, questionText, questionDiagram, point);
        this.mcqOption = mcqOption;
        this.mcqAnsIndex = mcqAnsIndex;
    }

    public String[] getMcqOption() {
        return mcqOption;
    }

    public void setMcqOption(String[] mcqOption) {
        this.mcqOption = mcqOption;
    }

    public int getMcqAnsIndex() {
        return mcqAnsIndex;
    }

    public void setMcqAnsIndex(int mcqAnsIndex) {
        this.mcqAnsIndex = mcqAnsIndex;
    }
    
}

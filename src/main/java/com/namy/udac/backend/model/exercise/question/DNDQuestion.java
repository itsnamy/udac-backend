package com.namy.udac.backend.model.exercise.question;

import java.sql.Blob;

public class DNDQuestion extends Question{
    private DiagramType diagramType;
    private String correctDiagramJson;
    private String correctDiagramXml;

    public enum DiagramType {
        ERD, DFD
    }

    public DNDQuestion() {
        super();
    }

    public DNDQuestion(DNDQuestion dndQuestion) {
        super(dndQuestion);
        this.diagramType = dndQuestion.diagramType;
        this.correctDiagramJson = dndQuestion.correctDiagramJson;
        this.correctDiagramXml = dndQuestion.correctDiagramXml;
    }

    public DNDQuestion(String idQuestion, String idQuestionSec, QuestionType questionType, String questionText, Blob questionDiagram, int point, DiagramType diagramType, String correctDiagramJson, String correctDiagramXml) {
        super(idQuestion, idQuestionSec, questionType, questionText, questionDiagram, point);
        this.diagramType = diagramType;
        this.correctDiagramJson = correctDiagramJson;
        this.correctDiagramXml = correctDiagramXml;
    }
  
    public DiagramType getDiagramType() {
        return diagramType;
    }

    public void setDiagramType(DiagramType diagramType) {
        this.diagramType = diagramType;
    }

    public String getCorrectDiagramJson() {
        return correctDiagramJson;
    }

    public void setCorrectDiagramJson(String correctDiagramJson) {
        this.correctDiagramJson = correctDiagramJson;
    }

    public String getCorrectDiagramXml() {
        return correctDiagramXml;
    }

    public void setCorrectDiagramXml(String correctDiagramXml) {
        this.correctDiagramXml = correctDiagramXml;
    }
}

package com.namy.udac.backend.model.tutorial;

public class SQLTutorialSet {
    private String idTutorialSet;
    private String tutorialTitle;
    private String tutorialInstructions;
    private String exampleCode;
    private String datasetJson;
    
    // Constructor
    public SQLTutorialSet() {
        // Default constructor
    }

    public SQLTutorialSet(SQLTutorialSet tutorial){
        this.idTutorialSet = tutorial.idTutorialSet;
        this.tutorialTitle = tutorial.tutorialTitle;
        this.tutorialInstructions = tutorial.tutorialInstructions;
        this.exampleCode = tutorial.exampleCode;
        this.datasetJson = tutorial.datasetJson;
    } 
    
    public SQLTutorialSet(String idTutorialSet, String tutorialTitle, String tutorialInstructions, String exampleCode, String datasetJson) {
        this.idTutorialSet = idTutorialSet;
        this.tutorialTitle = tutorialTitle;
        this.tutorialInstructions = tutorialInstructions;
        this.exampleCode = exampleCode;
        this.datasetJson = datasetJson;
    }

    // Getters and Setters
    public String getIdTutorialSet() {
        return idTutorialSet;
    }

    public void setIdTutorialSet(String idTutorialSet) {
        this.idTutorialSet = idTutorialSet;
    }

    public String getTutorialTitle() {
        return tutorialTitle;
    }

    public void setTutorialTitle(String tutorialTitle) {
        this.tutorialTitle = tutorialTitle;
    }

    public String getTutorialInstructions() {
        return tutorialInstructions;
    }

    public void setTutorialInstructions(String tutorialInstructions) {
        this.tutorialInstructions = tutorialInstructions;
    }

    public String getExampleCode() {
        return exampleCode;
    }

    public void setExampleCode(String exampleCode) {
        this.exampleCode = exampleCode;
    }

    public String getDatasetJson() {
        return datasetJson;
    }

    public void setDatasetJson(String datasetJson) {
        this.datasetJson = datasetJson;
    }
}

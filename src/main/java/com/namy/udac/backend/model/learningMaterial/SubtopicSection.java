package com.namy.udac.backend.model.learningMaterial;

public class SubtopicSection {
    private String idSubtopicSection;
    private String idMaterialSet;
    private String subtopicTitle;

    public SubtopicSection() {
    }

    public SubtopicSection(SubtopicSection subtopicSection) {
        this.idSubtopicSection = subtopicSection.idSubtopicSection;
        this.idMaterialSet = subtopicSection.idMaterialSet;
        this.subtopicTitle = subtopicSection.subtopicTitle;
    }

    public SubtopicSection(String idSubtopicSection, String idMaterialSet, String subtopicTitle) {
        this.idSubtopicSection = idSubtopicSection;
        this.idMaterialSet = idMaterialSet;
        this.subtopicTitle = subtopicTitle;
    }

    public String getIdSubtopicSection() {
        return idSubtopicSection;
    }

    public void setIdSubtopicSection(String idSubtopicSection) {
        this.idSubtopicSection = idSubtopicSection;
    }

    public String getIdMaterialSet() {
        return idMaterialSet;
    }

    public void setIdMaterialSet(String idMaterialSet) {
        this.idMaterialSet = idMaterialSet;
    }

    public String getSubtopicTitle() {
        return subtopicTitle;
    }

    public void setSubtopicTitle(String subtopicTitle) {
        this.subtopicTitle = subtopicTitle;
    }
}

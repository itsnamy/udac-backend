package com.namy.udac.backend.model.learningMaterial;

public class NoteMaterial {
    private String idMaterialNote;
    private String idSubtopicSection;
    private String noteTitle;
    private String noteContent;

    public NoteMaterial() {}

    public NoteMaterial(NoteMaterial noteMaterial) {
        this.idMaterialNote = noteMaterial.idMaterialNote;
        this.idSubtopicSection = noteMaterial.idSubtopicSection;
        this.noteTitle = noteMaterial.noteTitle;
        this.noteContent = noteMaterial.noteContent;
    }

    public NoteMaterial(String idMaterialNote, String idSubtopicSection, String noteTitle, String noteContent) {
        this.idMaterialNote = idMaterialNote;
        this.idSubtopicSection = idSubtopicSection;
        this.noteTitle = noteTitle;
        this.noteContent = noteContent;
    }

    public String getIdMaterialNote() {
        return idMaterialNote;
    }

    public void setIdMaterialNote(String idMaterialNote) {
        this.idMaterialNote = idMaterialNote;
    }

    public String getIdSubtopicSection() {
        return idSubtopicSection;
    }

    public void setIdSubtopicSection(String idSubtopicSection) {
        this.idSubtopicSection = idSubtopicSection;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }
}

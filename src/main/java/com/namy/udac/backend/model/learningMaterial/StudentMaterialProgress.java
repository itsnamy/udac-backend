package com.namy.udac.backend.model.learningMaterial;

import java.sql.Timestamp;

public class StudentMaterialProgress {
    private String idMaterialProgress;
    private String studentId;
    private String materialSetId;
    private String materialId;
    private String materialType;
    private Timestamp completedAt;

    public StudentMaterialProgress() {
    }

    public StudentMaterialProgress(StudentMaterialProgress studentMaterialProgress) {
        this.idMaterialProgress = studentMaterialProgress.idMaterialProgress;
        this.studentId = studentMaterialProgress.studentId;
        this.materialSetId = studentMaterialProgress.materialSetId;
        this.materialId = studentMaterialProgress.materialId;
        this.materialType = studentMaterialProgress.materialType;
        this.completedAt = studentMaterialProgress.completedAt;
    }

    public StudentMaterialProgress(String idMaterialProgress, String studentId, String materialSetId, String materialId, String materialType, Timestamp completedAt) {
        this.idMaterialProgress = idMaterialProgress;
        this.studentId = studentId;
        this.materialSetId = materialSetId;
        this.materialId = materialId;
        this.materialType = materialType;
        this.completedAt = completedAt;
    }

    public String getIdMaterialProgress() {
        return idMaterialProgress;
    }

    public void setIdMaterialProgress(String idMaterialProgress) {
        this.idMaterialProgress = idMaterialProgress;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getMaterialSetId() {
        return materialSetId;
    }

    public void setMaterialSetId(String materialSetId) {
        this.materialSetId = materialSetId;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public Timestamp getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Timestamp completedAt) {
        this.completedAt = completedAt;
    }
}

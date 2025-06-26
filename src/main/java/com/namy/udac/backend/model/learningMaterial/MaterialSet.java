package com.namy.udac.backend.model.learningMaterial;

import java.time.LocalDateTime;

public class MaterialSet {
    private String idMaterialSet;
    private String topicTitle;
    private String topicDesc;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String ownedBy;

    // Constructors
    public MaterialSet() {
    }

    public MaterialSet(MaterialSet materialSet) {
        this.idMaterialSet = materialSet.idMaterialSet;
        this.topicTitle = materialSet.topicTitle;
        this.topicDesc = materialSet.topicDesc;
        this.createdAt = materialSet.createdAt;
        this.updatedAt = materialSet.updatedAt;
        this.ownedBy = materialSet.ownedBy;
    }
    
    public MaterialSet(String idMaterialSet, String topicTitle, String topicDesc, LocalDateTime createdAt, LocalDateTime updatedAt, String ownedBy) {
        this.idMaterialSet = idMaterialSet;
        this.topicTitle = topicTitle;
        this.topicDesc = topicDesc;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.ownedBy = ownedBy;
    }

    // Getters and Setters
    public String getIdMaterialSet() {
        return idMaterialSet;
    }

    public void setIdMaterialSet(String idMaterialSet) {
        this.idMaterialSet = idMaterialSet;
    }

    public String getTopicTitle() {
        return topicTitle;
    }

    public void setTopicTitle(String topicTitle) {
        this.topicTitle = topicTitle;
    }

    public String getTopicDesc() {
        return topicDesc;
    }

    public void setTopicDesc(String topicDesc) {
        this.topicDesc = topicDesc;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(String ownedBy) {
        this.ownedBy = ownedBy;
    }
}

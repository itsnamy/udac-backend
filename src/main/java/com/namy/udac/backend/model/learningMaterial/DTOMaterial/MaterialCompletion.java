package com.namy.udac.backend.model.learningMaterial.DTOMaterial;

public class MaterialCompletion {
    private double progressPercentage;
    private String nextMaterialId;

    public MaterialCompletion() {
    }

    public MaterialCompletion(MaterialCompletion materialCompletion) {
        this.progressPercentage = materialCompletion.progressPercentage;
        this.nextMaterialId = materialCompletion.nextMaterialId;
    }
    
    public MaterialCompletion(double progressPercentage, String nextMaterialId) {
        this.progressPercentage = progressPercentage;
        this.nextMaterialId = nextMaterialId;
    }

    public double getProgressPercentage() {
        return progressPercentage;
    }

    public void setProgressPercentage(double progressPercentage) {
        this.progressPercentage = progressPercentage;
    }

    public String getNextMaterialId() {
        return nextMaterialId;
    }

    public void setNextMaterialId(String nextMaterialId) {
        this.nextMaterialId = nextMaterialId;
    }
}

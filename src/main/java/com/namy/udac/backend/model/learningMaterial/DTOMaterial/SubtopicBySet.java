package com.namy.udac.backend.model.learningMaterial.DTOMaterial;

import java.util.List;

import com.namy.udac.backend.model.learningMaterial.MaterialSet;

public class SubtopicBySet {
    private MaterialSet materialSet;
    private List<MaterialBySubtopic> subtopicSections;

    public SubtopicBySet() {
    }

    public SubtopicBySet(SubtopicBySet subtopicBySet) {
        this.materialSet = subtopicBySet.materialSet;
        this.subtopicSections = subtopicBySet.subtopicSections;
    }

    public SubtopicBySet(MaterialSet materialSet, List<MaterialBySubtopic> subtopicSections) {
        this.materialSet = materialSet;
        this.subtopicSections = subtopicSections;
    }

    public MaterialSet getMaterialSet() {
        return materialSet;
    }

    public void setMaterialSet(MaterialSet materialSet) {
        this.materialSet = materialSet;
    }

    public List<MaterialBySubtopic> getSubtopicSections() {
        return subtopicSections;
    }

    public void setSubtopicSections(List<MaterialBySubtopic> subtopicSections) {
        this.subtopicSections = subtopicSections;
    }

}

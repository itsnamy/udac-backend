package com.namy.udac.backend.model.learningMaterial.DTOMaterial;

import com.namy.udac.backend.model.exercise.DTOexercise.ExerciseBySet;
import com.namy.udac.backend.model.learningMaterial.NoteMaterial;
import com.namy.udac.backend.model.learningMaterial.SubtopicSection;
import com.namy.udac.backend.model.learningMaterial.VideoMaterial;

public class MaterialBySubtopic {
    SubtopicSection subtopic;
    VideoMaterial videoMaterial;
    NoteMaterial noteMaterial;
    ExerciseBySet exerciseBySet;

    public MaterialBySubtopic() {
    }

    public MaterialBySubtopic(MaterialBySubtopic materialBySubtopic) {
        this.subtopic = materialBySubtopic.subtopic;
        this.videoMaterial = materialBySubtopic.videoMaterial;
        this.noteMaterial = materialBySubtopic.noteMaterial;
        this.exerciseBySet = materialBySubtopic.exerciseBySet;
    }
    
    public MaterialBySubtopic(SubtopicSection subtopicSection, VideoMaterial videoMaterial, NoteMaterial noteMaterial, ExerciseBySet exerciseBySet) {
        this.subtopic = subtopicSection;
        this.videoMaterial = videoMaterial;
        this.noteMaterial = noteMaterial;
        this.exerciseBySet = exerciseBySet;
    }

    public SubtopicSection getSubtopicSection() {
        return subtopic;
    }

    public void setSubtopicSection(SubtopicSection subtopic) {
        this.subtopic = subtopic;
    }

    public VideoMaterial getVideoMaterial() {
        return videoMaterial;
    }

    public void setVideoMaterial(VideoMaterial videoMaterial) {
        this.videoMaterial = videoMaterial;
    }

    public NoteMaterial getNoteMaterial() {
        return noteMaterial;
    }

    public void setNoteMaterial(NoteMaterial noteMaterial) {
        this.noteMaterial = noteMaterial;
    }

    public ExerciseBySet getExerciseBySet() {
        return exerciseBySet;
    }

    public void setExerciseBySet(ExerciseBySet exerciseBySet) {
        this.exerciseBySet = exerciseBySet;
    }
    
}

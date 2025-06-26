package com.namy.udac.backend.service.materialService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.namy.udac.backend.model.exercise.DTOexercise.ExerciseBySet;
import com.namy.udac.backend.model.learningMaterial.NoteMaterial;
import com.namy.udac.backend.model.learningMaterial.SubtopicSection;
import com.namy.udac.backend.model.learningMaterial.VideoMaterial;
import com.namy.udac.backend.model.learningMaterial.DTOMaterial.MaterialBySubtopic;
import com.namy.udac.backend.repository.MaterialRepo.SubtopicMaterialRepository;
import com.namy.udac.backend.service.exerciseServices.QuestionSetService;

@Service
public class SubtopicService {

    @Autowired
    private SubtopicMaterialRepository subtopicMaterialRepository;

    @Autowired
    private VideoService videoService;

    @Autowired
    private NoteService noteService;

    @Autowired
    private QuestionSetService questionSetService;


    public SubtopicSection addSubtopic(SubtopicSection section) {
        // Generate new idSubtopicSection
        int maxId = subtopicMaterialRepository.findMaxId();
        String newId = "subtopic" + String.format("%03d", maxId + 1);
        section.setIdSubtopicSection(newId);
        return subtopicMaterialRepository.add(section);
    }

    public SubtopicSection getById(String id) {
        return subtopicMaterialRepository.findById(id);
    }

    public List<SubtopicSection> getByMaterialSet(String idSet) {
        return subtopicMaterialRepository.findByMaterialSets(idSet);
    }

    public List<SubtopicSection> getAll() {
        return subtopicMaterialRepository.findAll();
    }

    public SubtopicSection updateSubtopic(SubtopicSection updatedSection) {
        // Fetch the existing subtopic section to update
        String id = updatedSection.getIdSubtopicSection();
        SubtopicSection section = subtopicMaterialRepository.findById(id);
        if (section == null) {
            return null; // Section not found
        }
        // Update fields
        if (updatedSection.getIdMaterialSet() != null) {
            section.setIdMaterialSet(updatedSection.getIdMaterialSet());
        }
        if (updatedSection.getSubtopicTitle() != null) {
            section.setSubtopicTitle(updatedSection.getSubtopicTitle());
        }
        return subtopicMaterialRepository.update(id, section);
    }
    
    public boolean deleteById(String id) {
        return subtopicMaterialRepository.deleteById(id) > 0;
    }


    // Method related to material in subtopic section

    public MaterialBySubtopic getMaterialBySubtopic(String idSubtopicSection) {
        MaterialBySubtopic materialBySubtopic = new MaterialBySubtopic();

        SubtopicSection subtopicSection = subtopicMaterialRepository.findById(idSubtopicSection);
        if (subtopicSection != null) {
            materialBySubtopic.setSubtopicSection(subtopicSection);
        }

        List<VideoMaterial> videoMaterials = videoService.getBySection(idSubtopicSection);
        if (!videoMaterials.isEmpty()) {
            for (VideoMaterial video : videoMaterials) {
                materialBySubtopic.setVideoMaterial(video);
            }
        }

        List<NoteMaterial> noteMaterials = noteService.getBySection(idSubtopicSection);
        if (!noteMaterials.isEmpty()) {
            for (NoteMaterial note : noteMaterials) {
                materialBySubtopic.setNoteMaterial(note);
            }
        }

        ExerciseBySet exerciseBySets = questionSetService.getExerciseSetForQuiz(subtopicSection.getSubtopicTitle());
        if (exerciseBySets != null) {
            materialBySubtopic.setExerciseBySet(exerciseBySets);
        }
    
        return materialBySubtopic;
    }
}

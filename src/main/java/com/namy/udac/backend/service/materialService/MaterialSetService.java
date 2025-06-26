package com.namy.udac.backend.service.materialService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.namy.udac.backend.model.learningMaterial.MaterialSet;
import com.namy.udac.backend.model.learningMaterial.SubtopicSection;
import com.namy.udac.backend.model.learningMaterial.DTOMaterial.MaterialBySubtopic;
import com.namy.udac.backend.model.learningMaterial.DTOMaterial.SubtopicBySet;
import com.namy.udac.backend.repository.MaterialRepo.MaterialSetRepository;

@Service
public class MaterialSetService {

    @Autowired
    private MaterialSetRepository materialSetRepository;

    @Autowired
    private SubtopicService subtopicService;

    public MaterialSet addSet(MaterialSet set) {
        // Generate new idSet
        int maxId = materialSetRepository.findMaxIdSet();
        String newId = "matset" + String.format("%03d", maxId + 1);
        set.setIdMaterialSet(newId);
        return materialSetRepository.addSet(set);
    }

    public MaterialSet getById(String id) {
        return materialSetRepository.findById(id);
    }

    public List<MaterialSet> getAll() {
        return materialSetRepository.findAll();
    }

    public MaterialSet updateSet(MaterialSet updatedSet) {
        // Fetch the existing material set to update
        String id = updatedSet.getIdMaterialSet();
        MaterialSet set = materialSetRepository.findById(id);
        if (set == null) {
            return null; // Set not found
        }
        // Update fields
        if (updatedSet.getTopicTitle() != null) {
            set.setTopicTitle(updatedSet.getTopicTitle());
        }
        if (updatedSet.getTopicDesc() != null) {
            set.setTopicDesc(updatedSet.getTopicDesc());
        }
        return materialSetRepository.update(id, set);
    }

    public boolean deleteById(String id) {
        // Delete all subtopics associated with this material set
        List<String> subtopicIds = subtopicService.getByMaterialSet(id)
                .stream()
                .map(subtopic -> subtopic.getIdSubtopicSection())
                .toList();
        for (String subtopicId : subtopicIds) {
            subtopicService.deleteById(subtopicId);
        }
        return materialSetRepository.deleteById(id) > 0;
    }

    // Method related to subtopic in material set

    public SubtopicBySet getSubtopicsBySet(String idSet) {
        SubtopicBySet subtopicBySet = new SubtopicBySet();

        MaterialSet materialSet = materialSetRepository.findById(idSet);
        if (materialSet != null) {
            subtopicBySet.setMaterialSet(materialSet);
        } 

        List<SubtopicSection> subtopicSections = subtopicService.getByMaterialSet(idSet);
        List<MaterialBySubtopic> materialBySubtopics = new ArrayList<>();

        for (SubtopicSection subtopic : subtopicSections) {
            MaterialBySubtopic material = subtopicService.getMaterialBySubtopic(subtopic.getIdSubtopicSection());
            if (material != null) {
                materialBySubtopics.add(material);
            }
        }

        subtopicBySet.setSubtopicSections(materialBySubtopics);


        return subtopicBySet;
    }
}

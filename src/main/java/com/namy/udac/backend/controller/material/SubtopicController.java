package com.namy.udac.backend.controller.material;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.namy.udac.backend.model.learningMaterial.SubtopicSection;
import com.namy.udac.backend.service.materialService.SubtopicService;

@RestController
@RequestMapping("/material/subtopic")
public class SubtopicController {
    
    @Autowired
    private SubtopicService subtopicService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<?> createSubtopic(@RequestBody SubtopicSection subtopic) { 
        SubtopicSection addedSubtopic = subtopicService.addSubtopic(subtopic);
        return addedSubtopic != null 
                ? ResponseEntity.ok(addedSubtopic)
                : ResponseEntity.status(404).body("Subtopic is not added");
    }
    
    @GetMapping("view/all")
    public List<SubtopicSection> getAllSubtopics(@RequestBody SubtopicSection section) {
        List<SubtopicSection> subtopics = subtopicService.getAll();
        return subtopics != null ? subtopics : List.of();
    }

    @GetMapping("view/{id}")
    public SubtopicSection getSubtopic(@PathVariable String id) {
        SubtopicSection subtopic = subtopicService.getById(id);
        return subtopic != null ? subtopic : new SubtopicSection();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateSubtopic(@PathVariable String id, @RequestBody SubtopicSection subtopic) {
        SubtopicSection updatedSubtopic = subtopicService.updateSubtopic(subtopic);
        return updatedSubtopic != null 
            ? ResponseEntity.ok(updatedSubtopic) 
            : ResponseEntity.status(404).body("Subtopic not found or not updated");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSubtopic(@PathVariable String id) {
        boolean isDeleted = subtopicService.deleteById(id);
        return isDeleted 
            ? ResponseEntity.ok("Subtopic deleted successfully")
            : ResponseEntity.status(404).body("Subtopic not found");
    }
}

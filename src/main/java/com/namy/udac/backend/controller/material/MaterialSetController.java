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

import com.namy.udac.backend.model.learningMaterial.MaterialSet;
import com.namy.udac.backend.model.learningMaterial.DTOMaterial.SubtopicBySet;
import com.namy.udac.backend.service.materialService.MaterialSetService;

@RestController
@RequestMapping("/material/set")
public class MaterialSetController {
    
    @Autowired
    private MaterialSetService materialSetService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<?> createMaterialSet(
        @RequestBody MaterialSet materialSet
    ) {
        MaterialSet addedSet = materialSetService.addSet(materialSet);

        return  addedSet != null 
                    ?ResponseEntity.ok(addedSet)
                    : ResponseEntity.status(404).body("Material set is not added");
    }

    @GetMapping("view/{id}")
    public ResponseEntity<?> getMaterialSetById(@PathVariable String id) {
        MaterialSet materialSet= materialSetService.getById(id);

        return materialSet != null
                ? ResponseEntity.ok(materialSet)
                : ResponseEntity.status(404).body("Material set not found");
    }

    @GetMapping("/subtopics/{id}")
    public ResponseEntity<?> getSubtopicsByMaterialSet(@PathVariable String id) {
        SubtopicBySet result = materialSetService.getSubtopicsBySet(id);
        return result != null && result.getMaterialSet() != null
                ? ResponseEntity.ok(result)
                : ResponseEntity.status(404).body("Material set or its subtopics not found");
    }

    @GetMapping("view/all")
    public ResponseEntity<?> getAllMaterialSets() {
        List<MaterialSet> materialSets = materialSetService.getAll();

        return !materialSets.isEmpty()
                ? ResponseEntity.ok(materialSets)
                : ResponseEntity.status(404).body("No material sets found");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMaterialSet(@PathVariable String id, @RequestBody MaterialSet updatedSet) {
        updatedSet.setIdMaterialSet(id);
        MaterialSet updated = materialSetService.updateSet(updatedSet);
        return updated != null
                ? ResponseEntity.ok(updated)
                : ResponseEntity.status(404).body("Material set not found or update failed");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMaterialSet(@PathVariable String id) {
        boolean deleted = materialSetService.deleteById(id);
        return deleted
                ? ResponseEntity.ok("Material set and its subtopics deleted successfully")
                : ResponseEntity.status(404).body("Material set not found or delete failed");
    }
}

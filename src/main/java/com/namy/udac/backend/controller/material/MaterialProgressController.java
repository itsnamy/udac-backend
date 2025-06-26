package com.namy.udac.backend.controller.material;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.namy.udac.backend.model.learningMaterial.StudentMaterialProgress;
import com.namy.udac.backend.model.learningMaterial.DTOMaterial.MaterialCompletion;
import com.namy.udac.backend.service.materialService.StudentMaterialProgressService;

@RestController
@RequestMapping("/material/progress")
public class MaterialProgressController {

    @Autowired
    private StudentMaterialProgressService progressService;

    @PostMapping("/add")
    public ResponseEntity<?> recordProgress(
        @RequestParam String studentId,
        @RequestParam String materialSetId,
        @RequestParam String materialId,
        @RequestParam String materialType
    ) {
        StudentMaterialProgress progress = progressService.addProgress(studentId, materialSetId, materialId, materialType);
        return (progress != null)
                ? ResponseEntity.ok(progress)
                : ResponseEntity.status(409).body("Progress already recorded for this material");
    }

    @GetMapping("/status")
    public ResponseEntity<?> isMaterialCompleted(
        @RequestParam String studentId,
        @RequestParam String materialId
    ) {
        boolean completed = progressService.isCompleted(studentId, materialId);
        return ResponseEntity.ok(completed);
    }

    @GetMapping("/set/{materialSetId}/student/{studentId}")
    public ResponseEntity<?> getProgressByMaterialSet(
        @PathVariable String studentId,
        @PathVariable String materialSetId
    ) {
        List<StudentMaterialProgress> progressList = progressService.getProgressByMaterialSet(studentId, materialSetId);
        return ResponseEntity.ok(progressList);
    }

    @GetMapping("/completion/student/{studentId}")
    public ResponseEntity<?> getOverallCompletionStatus(
        @PathVariable String studentId
    ) {
        MaterialCompletion result = progressService.getMaterialCompletion(studentId);
        return ResponseEntity.ok(result);
    }
}

package com.namy.udac.backend.controller.exercise;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.namy.udac.backend.model.exercise.ExerciseSet;
import com.namy.udac.backend.repository.exerciseRepo.exercise.ExerciseSetRepository;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/exercise/sets")
public class ExerciseSetController {
    
    @Autowired
    ExerciseSetRepository exerciseSetRepo;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<?> createExerciseSet(
        @RequestBody ExerciseSet exerciseSet
    ) {
        
        int maxId = exerciseSetRepo.findMaxId();
        String id = String.format("exeset%03d", maxId + 1); 
        exerciseSet.setIdExerciseSet(id);
        
        exerciseSet.setCreatedAt(LocalDateTime.now());
        exerciseSet.setUpdatedAt(LocalDateTime.now());
        
        // set section, type, title, description, and owner is set in the request body
        ExerciseSet addedSet = exerciseSetRepo.add(exerciseSet);

        return  addedSet != null 
                    ?ResponseEntity.ok(addedSet)
                    : ResponseEntity.status(404).body("Exercise set is not added");
    }

    @GetMapping("view/{id}")
    public ResponseEntity<?> getExerciseSetById(@PathVariable String id) {
        ExerciseSet exerciseSet = exerciseSetRepo.findById(id);

        return exerciseSet != null
                ? ResponseEntity.ok(exerciseSet)
                : ResponseEntity.status(404).body("Exercise set not found");
    }

    @GetMapping("view/all")
    public ResponseEntity<?> getAllExerciseSets() {
        List<ExerciseSet> exerciseSets = exerciseSetRepo.findAll();

        return !exerciseSets.isEmpty()
                ? ResponseEntity.ok(exerciseSets)
                : ResponseEntity.status(404).body("No exercise sets found");
    }

    @GetMapping("/view/grouped-by-section")
    public ResponseEntity<?> getGroupedExerciseSets() {
        List<ExerciseSet> sets = exerciseSetRepo.findAll();

        if (sets.isEmpty()) {
            return ResponseEntity.status(404).body("No exercise sets found");
        }

        Map<String, List<ExerciseSet>> grouped = sets.stream()
            .collect(Collectors.groupingBy(ExerciseSet::getExerciseSection));

        return ResponseEntity.ok(grouped);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("view/owner/{owner}")
    public ResponseEntity<?> getExerciseSetsByOwner(@PathVariable String owner) {
        List<ExerciseSet> exerciseSets = exerciseSetRepo.findByOwner(owner);

        return !exerciseSets.isEmpty()
                ? ResponseEntity.ok(exerciseSets)
                : ResponseEntity.status(404).body("No exercise sets found for this owner");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("view/type/{type}")
    public ResponseEntity<?> getExerciseSetsByType(@PathVariable String type) {
        List<ExerciseSet> exerciseSets = exerciseSetRepo.findByType(type);

        return !exerciseSets.isEmpty()
                ? ResponseEntity.ok(exerciseSets)
                : ResponseEntity.status(404).body("No exercise sets found for this type");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("view/owner/{owner}/type/{type}")
    public ResponseEntity<?> getExerciseSetsByOwner(@PathVariable String owner, @PathVariable String type) {
        List<ExerciseSet> exerciseSets = exerciseSetRepo.findByTypeAndOwner(owner, type);

        return !exerciseSets.isEmpty()
                ? ResponseEntity.ok(exerciseSets)
                : ResponseEntity.status(404).body("No exercise sets found for this owner");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<ExerciseSet> updateExerciseSet(
        @RequestBody ExerciseSet updatedExerciseSet
    ) {
        ExerciseSet exerciseSet = exerciseSetRepo.findById(updatedExerciseSet.getIdExerciseSet());
        
        if(updatedExerciseSet.getExerciseSection() != null) {
            exerciseSet.setExerciseSection(updatedExerciseSet.getExerciseSection());
        }

        if(updatedExerciseSet.getExerciseType() != null) {
            exerciseSet.setExerciseType(updatedExerciseSet.getExerciseType());
        }

        if(updatedExerciseSet.getExerciseTitle() != null) {
            exerciseSet.setExerciseTitle(updatedExerciseSet.getExerciseTitle());
        }

        if(updatedExerciseSet.getExerciseDesc() != null) {
            exerciseSet.setExerciseDesc(updatedExerciseSet.getExerciseDesc());
        }

        exerciseSet.setUpdatedAt(LocalDateTime.now());

        ExerciseSet updatedSet = exerciseSetRepo.update(updatedExerciseSet.getIdExerciseSet(), exerciseSet);
        return ResponseEntity.ok(updatedSet);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteExerciseSet(@PathVariable String id) {
        int deleted = exerciseSetRepo.deleteById(id);

        return deleted>0
                ? ResponseEntity.noContent().build() // Return 204 No Content if deletion is successful
                : ResponseEntity.notFound().build(); // Return 404 Not Found if the exercise set is not found
    }

    
}

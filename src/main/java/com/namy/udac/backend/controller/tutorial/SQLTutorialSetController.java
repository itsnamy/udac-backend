package com.namy.udac.backend.controller.tutorial;

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

import com.namy.udac.backend.model.tutorial.SQLTutorialSet;
import com.namy.udac.backend.service.tutorialServices.SQLTutorialSetService;

@RestController
@RequestMapping("/tutorial/set")
public class SQLTutorialSetController {
    @Autowired
    private SQLTutorialSetService tutorialSetService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<SQLTutorialSet> addTutorialSet(@RequestBody SQLTutorialSet set) {
        SQLTutorialSet created = tutorialSetService.addSet(set);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SQLTutorialSet> getTutorialSet(@PathVariable String id) {
        SQLTutorialSet found = tutorialSetService.findSetById(id);
        return (found != null) ? ResponseEntity.ok(found) : ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<SQLTutorialSet>> getAllTutorialSets() {
        return ResponseEntity.ok(tutorialSetService.findAllSets());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<SQLTutorialSet> updateTutorialSet(@PathVariable String id, @RequestBody SQLTutorialSet set) {
        SQLTutorialSet updated = tutorialSetService.updateSet(id, set);
        return (updated != null) ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTutorialSet(@PathVariable String id) {
        boolean deleted = tutorialSetService.deleteSet(id);
        return deleted ? ResponseEntity.ok("Deleted successfully.") : ResponseEntity.status(404).body("Delete failed.");
    }
}

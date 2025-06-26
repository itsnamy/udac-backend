package com.namy.udac.backend.controller.tutorial;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.namy.udac.backend.model.tutorial.SQLTutorialSet;
import com.namy.udac.backend.service.tutorialServices.SQLExecutorService;
import com.namy.udac.backend.service.tutorialServices.SQLTutorialSetService;

@RestController
@RequestMapping("/tutorial/sql-executor")
public class SQLExecutorController {

    @Autowired
    private SQLTutorialSetService setService;

    @Autowired
    private SQLExecutorService executorService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/load-from-set/{id}")
    public ResponseEntity<?> loadDatasetFromTutorialSet(@PathVariable String id) {
        try {
            SQLTutorialSet set = setService.findSetById(id);
            if (set == null) {
                return ResponseEntity.status(404).body("Tutorial set not found");
            }

            String json = set.getDatasetJson();
            if (json == null || json.trim().isEmpty()) {
                executorService.resetDataset();
                return ResponseEntity.ok("Dataset is reset for this tutorial set");
            } 
            else{
                Map<String, Object> dataset = objectMapper.readValue(
                    json, new TypeReference<Map<String, Object>>() {}
                );

                String createTableSQL = (String) dataset.get("createTableSQL");
                List<String> insertStatements = objectMapper.convertValue(
                    dataset.get("insertStatements"), new TypeReference<List<String>>() {}
                );

                executorService.resetAndLoadDataset(createTableSQL, insertStatements);
                return ResponseEntity.ok("Dataset from tutorial set loaded successfully.");
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to load from tutorial set: " + e.getMessage());
        }
    }


    // Reset H2 DB 
    @PostMapping("/reset")
    public ResponseEntity<String> resetDataset() {
        try {
            executorService.resetDataset();
            return ResponseEntity.ok("Dataset reset successfully.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to reset dataset: " + e.getMessage());
        }
    }

    // Execute SQL
    @PostMapping("/execute")
    public ResponseEntity<?> executeSQL(@RequestBody Map<String, String> payload) {
        try {
            String sql = payload.get("sql");
            if (sql == null || sql.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Missing or empty SQL query");
            }

            Object result = executorService.executeSQL(sql);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("SQL Execution Error: " + e.getMessage());
        }
    }
}

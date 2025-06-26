package com.namy.udac.backend.service.tutorialServices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.namy.udac.backend.model.tutorial.SQLTutorialSet;
import com.namy.udac.backend.repository.tutorialRepo.SQLTutorialSetRepository;

@Service
public class SQLTutorialSetService {
    
    @Autowired
    private SQLTutorialSetRepository sqlTutorialRepo;

    public SQLTutorialSet addSet(SQLTutorialSet set) {
        int maxId = sqlTutorialRepo.findMaxIdSet();
        String newId = String.format("sql%03d", maxId + 1);  // sql001, sql002, etc.
        System.out.println("Adding new SQL Tutorial Set with ID: " + newId);
        set.setIdTutorialSet(newId);
        return sqlTutorialRepo.addSet(set);
    }

    public SQLTutorialSet findSetById(String id) {
        return sqlTutorialRepo.findById(id);
    }

    public List<SQLTutorialSet> findAllSets() {
        return sqlTutorialRepo.findAll();
    }

    public SQLTutorialSet updateSet(String id, SQLTutorialSet set) {
        SQLTutorialSet existingSet = sqlTutorialRepo.findById(id);
        if (existingSet == null) {
            return null; // Set not found
        }

        // Update fields
        if (set.getTutorialTitle() != null) {
            existingSet.setTutorialTitle(set.getTutorialTitle());
        }
        if (set.getTutorialInstructions() != null) {
            existingSet.setTutorialInstructions(set.getTutorialInstructions());
        }
        if (set.getExampleCode() != null) {
            existingSet.setExampleCode(set.getExampleCode());
        }
        if (set.getDatasetJson() != null) {
            existingSet.setDatasetJson(set.getDatasetJson());
        }

        return sqlTutorialRepo.update(id, existingSet);
    }

    public boolean deleteSet(String id) {
        return sqlTutorialRepo.deleteById(id) > 0;
    }
}

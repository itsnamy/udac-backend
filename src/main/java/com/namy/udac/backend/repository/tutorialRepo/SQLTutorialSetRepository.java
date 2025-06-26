package com.namy.udac.backend.repository.tutorialRepo;

import java.util.List;

import com.namy.udac.backend.model.tutorial.SQLTutorialSet;

public interface SQLTutorialSetRepository {
    SQLTutorialSet addSet(SQLTutorialSet set);
    int findMaxIdSet();
    SQLTutorialSet findById(String id);
    List<SQLTutorialSet> findAll();
    SQLTutorialSet update(String id, SQLTutorialSet set);
    int deleteById(String id);
}

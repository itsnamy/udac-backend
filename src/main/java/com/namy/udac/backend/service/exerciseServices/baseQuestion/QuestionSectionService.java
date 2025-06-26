package com.namy.udac.backend.service.exerciseServices.baseQuestion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.namy.udac.backend.model.exercise.QuestionSection;
import com.namy.udac.backend.repository.exerciseRepo.exercise.QuestionSectionRepository;

@Service
public class QuestionSectionService {

    @Autowired
    QuestionSectionRepository questionSectionRepos;

    public QuestionSection add(QuestionSection questionSection) {
        int maxId = questionSectionRepos.findMaxId();
        String id = String.format("qsc%03d", maxId + 1); 
        questionSection.setIdQuestionSec(id);
        return questionSectionRepos.add(questionSection);
    }

    public QuestionSection findById(String id) {
        return questionSectionRepos.findById(id);
    }

    public List<QuestionSection> findByExerciseSets(String idSet) {
        return questionSectionRepos.findByExerciseSets(idSet);
    }

    public List<QuestionSection> findAll() {
        return questionSectionRepos.findAll();
    }

    public QuestionSection update(QuestionSection updatedSection, String idQuestionSection) {
        QuestionSection existing = questionSectionRepos.findById(idQuestionSection);

        if (updatedSection.getSectionTitle() != null && !updatedSection.getSectionTitle().isEmpty()) {
            existing.setSectionTitle(updatedSection.getSectionTitle());
        }

        if(updatedSection.getQuestionType() != null) {
            existing.setQuestionType(updatedSection.getQuestionType());
        }

        if(updatedSection.getSectionOrder() != 0) {
            existing.setSectionOrder(updatedSection.getSectionOrder());
        }

        
        return questionSectionRepos.update(idQuestionSection, existing);
    }

    public int deleteById(String id) {
        return questionSectionRepos.deleteById(id);
    }
}

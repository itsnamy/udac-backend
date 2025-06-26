package com.namy.udac.backend.service.exerciseServices.baseQuestion;

import java.util.List;

import org.springframework.stereotype.Service;

import com.namy.udac.backend.model.exercise.question.MCQQuestion;
import com.namy.udac.backend.repository.exerciseRepo.question.MCQQuestionRepository;

@Service
public class MCQService implements QuestionService<MCQQuestion> {

    private final MCQQuestionRepository mcqRepo;

    public MCQService(MCQQuestionRepository mcqRepo) {
        this.mcqRepo = mcqRepo;
    }

    @Override
    public MCQQuestion add(MCQQuestion question) {
        int maxId = mcqRepo.findMaxId();
        String id = String.format("mcq%03d", maxId + 1);
        question.setIdQuestion(id);
        return mcqRepo.add(question);
    }

    @Override
    public MCQQuestion findById(String id) {
        MCQQuestion question = mcqRepo.findById(id);
        return question;
    }

    @Override
    public List<MCQQuestion> findBySectionId(String sectionId) {
        List<MCQQuestion> questions = mcqRepo.findBySectionId(sectionId);
        return questions;
    }

    @Override
    public MCQQuestion update(MCQQuestion updated) {
        String id = updated.getIdQuestion();
        MCQQuestion existing = mcqRepo.findById(id);

        if (existing == null) {
            existing = add(updated);
        }

        if (updated.getQuestionText() != null && !updated.getQuestionText().isEmpty()) {
            existing.setQuestionText(updated.getQuestionText());
        }

        if (updated.getQuestionDiagram() != null) {
            existing.setQuestionDiagram(updated.getQuestionDiagram());
        }
        
        if (updated.getPoint() != 0) {
            existing.setPoint(updated.getPoint());
        }

        if (updated.getMcqOption() != null && updated.getMcqOption().length > 0) {
            existing.setMcqOption(updated.getMcqOption());
        }

        if (updated.getMcqAnsIndex() != 0) {
            existing.setMcqAnsIndex(updated.getMcqAnsIndex());
        }
        
        return mcqRepo.update(id, existing);
    }

    @Override
    public int deleteById(String id) {
        return mcqRepo.deleteById(id);
    }
}

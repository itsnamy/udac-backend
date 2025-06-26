package com.namy.udac.backend.service.exerciseServices.baseQuestion;

import java.util.List;

import org.springframework.stereotype.Service;

import com.namy.udac.backend.model.exercise.question.DNDQuestion;
import com.namy.udac.backend.repository.exerciseRepo.question.DNDQuestionRepository;

@Service
public class DNDService implements QuestionService<DNDQuestion> {

    private final DNDQuestionRepository dndRepo;

    public DNDService(DNDQuestionRepository dndRepo) {
        this.dndRepo = dndRepo;
    }

    @Override
    public DNDQuestion add(DNDQuestion question) {
        int maxId = dndRepo.findMaxId();
        String id = String.format("dnd%03d", maxId + 1);
        question.setIdQuestion(id);
        return dndRepo.add(question);
    }

    @Override
    public DNDQuestion findById(String id) {
        return dndRepo.findById(id);
    }

    @Override
    public List<DNDQuestion> findBySectionId(String sectionId) {
        return dndRepo.findBySectionId(sectionId);
    }

    @Override
    public DNDQuestion update(DNDQuestion updated) {
        String id = updated.getIdQuestion();
        DNDQuestion existing = dndRepo.findById(id);

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

        if (updated.getDiagramType() != null) {
            existing.setDiagramType(updated.getDiagramType());
        }

        if (updated.getCorrectDiagramJson() != null && !updated.getCorrectDiagramJson().isEmpty()) {
            existing.setCorrectDiagramJson(updated.getCorrectDiagramJson());
        }

        return dndRepo.update(id, existing);
    }

    @Override
    public int deleteById(String id) {
        return dndRepo.deleteById(id);
    }
}

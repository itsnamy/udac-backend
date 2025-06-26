package com.namy.udac.backend.service.exerciseServices.baseQuestion;

import java.util.List;

import org.springframework.stereotype.Service;

import com.namy.udac.backend.model.exercise.question.FIBQuestion;
import com.namy.udac.backend.repository.exerciseRepo.question.FIBQuestionRepository;

@Service
public class FIBService implements QuestionService<FIBQuestion> {

    private final FIBQuestionRepository fibRepo;

    public FIBService(FIBQuestionRepository fibRepo) {
        this.fibRepo = fibRepo;
    }

    @Override
    public FIBQuestion add(FIBQuestion question) {
        int maxId = fibRepo.findMaxId();
        String id = String.format("fib%03d", maxId + 1);
        question.setIdQuestion(id);
        return fibRepo.add(question);
    }

    @Override
    public FIBQuestion findById(String id) {
        return fibRepo.findById(id);
    }

    @Override
    public List<FIBQuestion> findBySectionId(String sectionId) {
        return fibRepo.findBySectionId(sectionId);
    }

    @Override
    public FIBQuestion update(FIBQuestion updated) {
        String id = updated.getIdQuestion();
        FIBQuestion existing = fibRepo.findById(id);

        if (existing == null) {
            return add(updated);
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

        if (updated.getFibAnswer() != null && updated.getFibAnswer().length > 0) {
            existing.setFibAnswer(updated.getFibAnswer());
        }

        existing.setCaseSensitive(updated.isCaseSensitive());

        return fibRepo.update(id, existing);
    }

    @Override
    public int deleteById(String id) {
        return fibRepo.deleteById(id);
    }

}

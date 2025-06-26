package com.namy.udac.backend.service.exerciseServices;

import java.util.EnumMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.namy.udac.backend.model.exercise.question.Question;
import com.namy.udac.backend.model.exercise.question.Question.QuestionType;
import com.namy.udac.backend.service.exerciseServices.baseQuestion.DNDService;
import com.namy.udac.backend.service.exerciseServices.baseQuestion.FIBService;
import com.namy.udac.backend.service.exerciseServices.baseQuestion.MCQService;
import com.namy.udac.backend.service.exerciseServices.baseQuestion.QuestionService;

@Component
public class QuestionServiceManager {
    private final Map<QuestionType, QuestionService<? extends Question>> services = new EnumMap<>(QuestionType.class);

    public QuestionServiceManager(
            MCQService mcqService,
            FIBService fibService,
            DNDService dndService
    ) {
        services.put(QuestionType.MCQ, mcqService);
        services.put(QuestionType.FIB, fibService);
        services.put(QuestionType.DND, dndService);
    }

    public QuestionService<? extends Question> getService(QuestionType type) {
        return services.get(type);
    }
}

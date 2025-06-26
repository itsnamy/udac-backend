package com.namy.udac.backend.service.exerciseServices;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.namy.udac.backend.model.exercise.ExerciseSet;
import com.namy.udac.backend.model.exercise.question.DNDQuestion;
import com.namy.udac.backend.model.exercise.question.FIBQuestion;
import com.namy.udac.backend.model.exercise.question.MCQQuestion;
import com.namy.udac.backend.model.exercise.question.Question;
import com.namy.udac.backend.model.exercise.question.Question.QuestionType;
import com.namy.udac.backend.repository.exerciseRepo.exercise.ExerciseSetRepository;
import com.namy.udac.backend.service.exerciseServices.baseQuestion.QuestionSectionService;
import com.namy.udac.backend.service.exerciseServices.baseQuestion.QuestionService;
import com.namy.udac.backend.model.exercise.QuestionSection;
import com.namy.udac.backend.model.exercise.DTOexercise.ExerciseBySet;
import com.namy.udac.backend.model.exercise.DTOexercise.SectionWithQuestion;

@Service
public class QuestionSetService {
    private final ExerciseSetRepository exerciseSetRepo;
    private final QuestionSectionService questionSectionService;
    private final QuestionServiceManager questionServiceManager;

    public QuestionSetService(
            ExerciseSetRepository exerciseSetRepo,
            QuestionSectionService questionSectionService,
            QuestionServiceManager questionServiceManager
    ) {
        this.exerciseSetRepo = exerciseSetRepo;
        this.questionSectionService = questionSectionService;
        this.questionServiceManager = questionServiceManager;
    }

    // view all question sections in the exercise set
    /**
     * Return 
     * Details of an exercise set get fron the exercise set repository
     * All question sections associated with the exercise set get from the question section service
     * All questions in each question section group in SectionWithQuestion object
     */
    public ExerciseBySet viewAllQuestionSections(String exerciseSetId) {
        System.err.println("ExerciseSet ID: " + exerciseSetId);
        ExerciseSet exerciseSet = exerciseSetRepo.findById(exerciseSetId);
        List<QuestionSection> questionSections = questionSectionService.findByExerciseSets(exerciseSetId);
        List<SectionWithQuestion> sectionWithQuestions = new ArrayList<>();

        for (QuestionSection section : questionSections) {
            System.out.println("Section ID: " + section.getIdQuestionSec());
            sectionWithQuestions.add(viewAllQuestionsInSection(section.getIdQuestionSec()));
        }

        ExerciseBySet exerciseSetWithSections = new ExerciseBySet(exerciseSet, sectionWithQuestions);

        return exerciseSetWithSections;
    }

    // view all questions in the question section
    public SectionWithQuestion viewAllQuestionsInSection(String sectionId) {
        QuestionSection questionSection = questionSectionService.findById(sectionId);
        QuestionService<? extends Question> questionService = questionServiceManager.getService(questionSection.getQuestionType());
        System.out.println("QuestionType: " + questionSection.getQuestionType());

        List<? extends Question> questions = questionService.findBySectionId(sectionId);
        SectionWithQuestion sectionWithQuestions = new SectionWithQuestion(questionSection, questions);

        return sectionWithQuestions;
    }

    // get exercise set for quiz
    public ExerciseBySet getExerciseSetForQuiz(String sectionTitle) {
        System.out.println("Getting exercise set for section: " + sectionTitle);
        List<ExerciseSet> exerciseSets = exerciseSetRepo.findBySectionAndType(sectionTitle, "QUIZ");
        if (exerciseSets.isEmpty()) {
            System.out.println("No exercise sets found for section: " + sectionTitle);
            return null;
        }

        ExerciseSet exerciseSet = exerciseSets.get(0); // Assuming we take the first one
        System.out.println("Found exercise set: " + exerciseSet);

        return viewAllQuestionSections(exerciseSet.getIdExerciseSet());
    }

    // add question section with multiple questions
    public SectionWithQuestion addQuestionSection(SectionWithQuestion sectionWithQuestions) {
        System.out.println("Received sectionWithQuestions: " + sectionWithQuestions);
        QuestionSection questionSection = sectionWithQuestions.getQuestionSection();
        System.out.println("questionSection: " + questionSection);
        questionSection = questionSectionService.add(questionSection);
        System.out.println("added questionSection: " + questionSection);
        

        if (sectionWithQuestions.getQuestionSection() == null) {
            System.out.println("questionSection is null!");
        }

        List<? extends Question> questions = sectionWithQuestions.getQuestions();

        for (Question question : questions) {
            question = addQuestionToSection(question, questionSection.getQuestionType(), questionSection.getIdQuestionSec());
        }

        return viewAllQuestionsInSection(questionSection.getIdQuestionSec());
    }

    @SuppressWarnings("unchecked")
    public <T extends Question> T addQuestionToSection(Question question, QuestionType questionType, String sectionId) {
        question.setIdQuestionSec(sectionId);

        switch (questionType) {
            case MCQ:
                MCQQuestion mcq = new MCQQuestion();
                mcq.setIdQuestionSec(question.getIdQuestionSec());
                mcq.setQuestionText(question.getQuestionText());
                mcq.setQuestionDiagram(question.getQuestionDiagram());
                mcq.setPoint(question.getPoint());
                mcq.setQuestionType(questionType);

                // Get MCQ-specific fields from original `question` if possible
                if (question instanceof MCQQuestion original) {
                    mcq.setMcqOption(original.getMcqOption());
                    mcq.setMcqAnsIndex(original.getMcqAnsIndex());
                } else {
                    System.out.println("Warning: question is not instance of MCQQuestion");
                    // Set dummy/default values or throw an exception
                    mcq.setMcqOption(new String[]{"Option A", "Option B"});
                    mcq.setMcqAnsIndex(0);
                }
                QuestionService<MCQQuestion> mcqService = (QuestionService<MCQQuestion>)
                questionServiceManager.getService(QuestionType.MCQ);
                System.out.println("MCQ Service: " + mcqService);

                MCQQuestion added = mcqService.add(mcq);
                return (T) added;
            case FIB:
                // Handle FIBQuestion
                QuestionService<FIBQuestion> fibService = (QuestionService<FIBQuestion>)
                questionServiceManager.getService(QuestionType.FIB);
                FIBQuestion fib = new FIBQuestion();
                fib.setIdQuestionSec(sectionId);
                fib.setQuestionText(question.getQuestionText());
                fib.setQuestionDiagram(question.getQuestionDiagram());
                fib.setPoint(question.getPoint());
                fib.setQuestionType(questionType);

                if (question instanceof FIBQuestion original) {
                    fib.setFibAnswer(original.getFibAnswer());
                    fib.setCaseSensitive(original.isCaseSensitive());
                } else {
                    System.out.println("Warning: question is not instance of FIBQuestion");
                    fib.setFibAnswer(new String[]{"default"}); // or throw an error
                    fib.setCaseSensitive(false);
                }

                return (T) fibService.add(fib);
            case DND:
                DNDQuestion dnd = new DNDQuestion();
                dnd.setIdQuestionSec(sectionId);
                dnd.setQuestionText(question.getQuestionText());
                dnd.setQuestionDiagram(question.getQuestionDiagram());
                dnd.setPoint(question.getPoint());
                dnd.setQuestionType(questionType);

                if (question instanceof DNDQuestion original) {
                    dnd.setDiagramType(original.getDiagramType());
                    System.out.println("DiagramType: " + original.getDiagramType());
                    dnd.setCorrectDiagramXml(original.getCorrectDiagramXml());
                    dnd.setCorrectDiagramJson("");
                    
                } else {
                    System.out.println("Warning: question is not instance of DNDQuestion");
                    dnd.setDiagramType(DNDQuestion.DiagramType.ERD); // default
                    dnd.setCorrectDiagramJson("{}");
                    dnd.setCorrectDiagramXml("<mxGraphModel></mxGraphModel>");
                }
                
                QuestionService<DNDQuestion> dndService = (QuestionService<DNDQuestion>)
                questionServiceManager.getService(QuestionType.DND);
                System.out.println("DND Service: " + dndService);

                return (T) dndService.add(dnd);
            default:
                break;
        }
        return null;

    }

    // update question section with multiple questions
    @SuppressWarnings("unchecked")
    public SectionWithQuestion updateQuestionSectionWithQuestions(SectionWithQuestion sectionWithQuestions, String idSection) {
        // 1. Update the question section info
        QuestionSection updatedSection = questionSectionService.update(sectionWithQuestions.getQuestionSection(), idSection);
    
        // 2. Update all questions in the section
        System.out.println("Updating questions in section: " + updatedSection.getIdQuestionSec());
        List<? extends Question> questions = sectionWithQuestions.getQuestions();
        for (Question question : questions) {
            QuestionService<Question> questionService = (QuestionService<Question>) questionServiceManager.getService(question.getQuestionType());
            question.setIdQuestionSec(updatedSection.getIdQuestionSec());
            if (questionService != null) {
                questionService.update(question);
            }
        }
    
        System.out.println("Updated section: " + updatedSection);
        // 3. Return updated SectionWithQuestion including updated questions
        return viewAllQuestionsInSection(idSection);
    }

    // delete question section with multiple questions - directly delete in QuestionSectionService

    // delete question by id
    public int deleteQuestionById(String questionId, String type) {
        QuestionType questionType = QuestionType.valueOf(type.toUpperCase());
        QuestionService<? extends Question> questionService = questionServiceManager.getService(questionType);
        
        return questionService.deleteById(questionId);
    }
}

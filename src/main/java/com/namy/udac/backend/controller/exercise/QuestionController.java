package com.namy.udac.backend.controller.exercise;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.namy.udac.backend.model.exercise.DTOexercise.ExerciseBySet;
import com.namy.udac.backend.model.exercise.DTOexercise.SectionWithQuestion;
import com.namy.udac.backend.model.exercise.question.Question;
import com.namy.udac.backend.model.exercise.question.Question.QuestionType;
import com.namy.udac.backend.service.exerciseServices.QuestionSetService;
import com.namy.udac.backend.service.exerciseServices.baseQuestion.QuestionSectionService;

@RestController
@RequestMapping("/exercise/questions")
public class QuestionController {

    @Autowired
    QuestionSetService questionSetService;

    @Autowired
    QuestionSectionService questionSectionService;

    // View exercise set details with all question sections and their questions
    @GetMapping("/view/exercise-set/{exerciseSetId}")
    public ResponseEntity<?> viewAllQuestionSections(@PathVariable String exerciseSetId) {
        ExerciseBySet exerciseSetWithSections = questionSetService.viewAllQuestionSections(exerciseSetId);
        return exerciseSetWithSections != null
                ? ResponseEntity.ok(exerciseSetWithSections)
                : ResponseEntity.status(404).body("Exercise set is Empty");
    }

    // View all questions in the question section
    @GetMapping("/view/question-section/{sectionId}")
    public ResponseEntity<?> viewAllQuestionsInSection(@PathVariable String sectionId) {
        SectionWithQuestion sectionWithQuestions = questionSetService.viewAllQuestionsInSection(sectionId);
        return sectionWithQuestions != null
                ? ResponseEntity.ok(sectionWithQuestions)
                : ResponseEntity.status(404).body("Question section is Empty");
    }

    // add multiple questions to the question section
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add/question-section")
    public ResponseEntity<?> addQuestionSection(
        @RequestBody SectionWithQuestion sectionWithQuestions
    ) {
        System.out.println("Received sectionWithQuestions: " + sectionWithQuestions);
        SectionWithQuestion addedSection = questionSetService.addQuestionSection(sectionWithQuestions);
        return addedSection != null
                ? ResponseEntity.ok(addedSection)
                : ResponseEntity.status(404).body("Question section is not added");
     
    }

    // add single question to the question section
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add/question")
    public ResponseEntity<?> addQuestionToSection(
        @RequestBody Question question,
        @RequestParam String sectionId
    ) {
        QuestionType questionType = question.getQuestionType();
        Question addedQuestion = questionSetService.addQuestionToSection(question, questionType, sectionId);
        return addedQuestion != null
                ? ResponseEntity.ok(addedQuestion)
                : ResponseEntity.status(404).body("Question is not added");
    }

    // update question section with multiple questions
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update/question-section")
    public ResponseEntity<?> updateQuestionSection(
        @RequestBody SectionWithQuestion sectionWithQuestions
    ) {
        System.out.println("Converted QuestionSection: " + sectionWithQuestions.getQuestionSection());
        SectionWithQuestion updatedSection = questionSetService.updateQuestionSectionWithQuestions(sectionWithQuestions, sectionWithQuestions.getQuestionSection().getIdQuestionSec());
        return updatedSection != null
                ? ResponseEntity.ok(updatedSection)
                : ResponseEntity.status(404).body("Question section is not updated");
    }

    // delete question section by id
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/question-section/{id}")
    public ResponseEntity<?> deleteQuestionSection(@PathVariable String id) {
        int deletedCount = questionSectionService.deleteById(id);
        return deletedCount > 0
                ? ResponseEntity.ok("Question section deleted successfully")
                : ResponseEntity.status(404).body("Question section not found");
    }

    // delete question by id
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/question/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable String id, @RequestParam String questionType) {
        int deletedCount = questionSetService.deleteQuestionById(id , questionType);
        return deletedCount > 0
                ? ResponseEntity.ok("Question deleted successfully")
                : ResponseEntity.status(404).body("Question not found");
    }    
}

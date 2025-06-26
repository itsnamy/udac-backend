package com.namy.udac.backend.controller.exercise;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.namy.udac.backend.model.exercise.exerciseSubmission.ExerciseSubmission;
import com.namy.udac.backend.model.exercise.exerciseSubmission.QuestionAnswer;
import com.namy.udac.backend.service.exerciseServices.submission.ExerciseSubmissionService;

@RestController
@RequestMapping("/exercise/submissions")
public class ExerciseSubmissionController {
    private final ExerciseSubmissionService submissionService;

    public ExerciseSubmissionController(ExerciseSubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    // Submit an exercise
    @PostMapping("/submit")
    public ResponseEntity<ExerciseSubmission> submitExercise(
            @RequestParam String studentId,
            @RequestParam String exerciseSetId,
            @RequestBody List<QuestionAnswer> answers) {
        
        System.out.println("Received answers:");
        for (QuestionAnswer a : answers) {
            System.out.println(" -> " + a.getIdQuestion() + ", " + a.getQuestionType() + ", " + a.getAnswer());
        }
        ExerciseSubmission submission = submissionService.submitExercise(studentId, exerciseSetId, answers);
        return ResponseEntity.ok(submission);
    }

    // Get all submissions by a student for a specific exercise set
    @GetMapping("/by-student-set")
    public ResponseEntity<List<ExerciseSubmission>> getSubmissionsByStudentAndSet(
            @RequestParam String studentId,
            @RequestParam String exerciseSetId) {

        List<ExerciseSubmission> submissions = submissionService.getSubmissionsByStudentAndSet(studentId, exerciseSetId);
        return ResponseEntity.ok(submissions);
    }

    // Get all answers from a specific submission
    @GetMapping("/answers")
    public ResponseEntity<List<QuestionAnswer>> getAnswersBySubmission(
            @RequestParam String submissionId) {

        List<QuestionAnswer> answers = submissionService.getAnswersBySubmission(submissionId);
        return ResponseEntity.ok(answers);
    }
}

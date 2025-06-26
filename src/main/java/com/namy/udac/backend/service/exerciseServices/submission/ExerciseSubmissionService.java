package com.namy.udac.backend.service.exerciseServices.submission;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.namy.udac.backend.model.exercise.exerciseSubmission.ExerciseSubmission;
import com.namy.udac.backend.model.exercise.exerciseSubmission.QuestionAnswer;
import com.namy.udac.backend.model.exercise.question.DNDQuestion;
import com.namy.udac.backend.model.exercise.question.FIBQuestion;
import com.namy.udac.backend.model.exercise.question.MCQQuestion;
import com.namy.udac.backend.repository.exerciseRepo.question.DNDQuestionRepository;
import com.namy.udac.backend.repository.exerciseRepo.question.FIBQuestionRepository;
import com.namy.udac.backend.repository.exerciseRepo.question.MCQQuestionRepository;
import com.namy.udac.backend.repository.exerciseRepo.submission.ExerciseSubmissionRepository;
import com.namy.udac.backend.repository.exerciseRepo.submission.QuestionAnswerRepository;
import com.namy.udac.backend.util.XmlComparatorUtil;

@Service
public class ExerciseSubmissionService {
    
    private final ExerciseSubmissionRepository submissionRepo;
    private final QuestionAnswerRepository answerRepo;
    private final MCQQuestionRepository mcqRepo;
    private final FIBQuestionRepository fibRepo;
    private final DNDQuestionRepository dndRepo;

    public ExerciseSubmissionService(ExerciseSubmissionRepository submissionRepo,
                                     QuestionAnswerRepository answerRepo,
                                     MCQQuestionRepository mcqRepo,
                                     FIBQuestionRepository fibRepo,
                                     DNDQuestionRepository dndRepo) {
        this.submissionRepo = submissionRepo;
        this.answerRepo = answerRepo;
        this.mcqRepo = mcqRepo;
        this.fibRepo = fibRepo;
        this.dndRepo = dndRepo;
    }

    public ExerciseSubmission submitExercise(String studentId, String exerciseSetId, List<QuestionAnswer> answers) {
        // Generate new submission ID
        int submissionMaxId = submissionRepo.findMaxId();
        String submissionId = String.format("exsub%03d", submissionMaxId + 1);
    
        // Initialize scores
        int totalScore = 0;
        int maxScore = 0;
    
        // Step 1: Save the submission first to satisfy FK constraint
        ExerciseSubmission submission = new ExerciseSubmission();
        submission.setIdSubmission(submissionId);
        submission.setIdExerciseSet(exerciseSetId);
        submission.setStudentId(studentId);
        submission.setSubmittedAt(LocalDateTime.now());
    
        // Temporarily set score to 0 — will update after processing answers
        submission.setTotalScore(0);
        submission.setMaxScore(0);
        submissionRepo.add(submission);
    
        // Step 2: Evaluate answers and save them
        for (QuestionAnswer answer : answers) {
            boolean isCorrect = false;
            int score = 0;
            String feedback = "";
    
            switch (answer.getQuestionType()) {
                case MCQ:
                    MCQQuestion mcq = mcqRepo.findById(answer.getIdQuestion());
                    maxScore += mcq.getPoint();
                    
                    isCorrect = Integer.toString(mcq.getMcqAnsIndex()).equals(answer.getAnswer());
                    score = isCorrect ? mcq.getPoint() : 0;
    
                    String[] options = mcq.getMcqOption();
                    int correctIndex = mcq.getMcqAnsIndex();
                    String correctText = (correctIndex >= 0 && correctIndex < options.length)
                            ? options[correctIndex]
                            : "Unavailable";
    
                    feedback = isCorrect
                            ? "Betul. Jawapan: " + correctText
                            : "Salah. Jawapan : " + correctText;
                    break;
    
                case FIB:
                    String rawAnswer = answer.getAnswer();
                    List<String> userAnswers = Arrays.stream(rawAnswer.split("\\\\"))
                                                    .map(String::trim)
                                                    .collect(Collectors.toList());
                
                    FIBQuestion fib = fibRepo.findById(answer.getIdQuestion());
                    maxScore += fib.getPoint();
                
                    try {
                        String[] correctArray = fib.getFibAnswer();
                        isCorrect = true;
                        int correctCount = 0;
                        int totalBlanks = correctArray.length;
                
                        for (int i = 0; i < totalBlanks; i++) {
                            String submittedVal = i < userAnswers.size() ? userAnswers.get(i) : "";
                            String correctVal = correctArray[i].trim();
                
                            boolean match = fib.isCaseSensitive()
                                ? submittedVal.equals(correctVal)
                                : submittedVal.equalsIgnoreCase(correctVal);
                
                            if (match) {
                                correctCount++;
                            } else {
                                isCorrect = false;
                            }
                        }
                
                        double pointPerBlank = (double) fib.getPoint() / totalBlanks;
                        score = (int) Math.round(correctCount * pointPerBlank);
                
                        feedback = String.format(
                            "%s Anda dapat %d daripada %d betul. Jawapan betul: %s",
                            isCorrect ? "Betul." : "Salah.",
                            correctCount,
                            totalBlanks,
                            Arrays.toString(correctArray)
                        );
                    } catch (Exception e) {
                        isCorrect = false;
                        score = 0;
                        feedback = "Format tidak sah.";
                    }
                    break;
            
    
                case DND:
                    DNDQuestion dnd = dndRepo.findById(answer.getIdQuestion());
                    maxScore += dnd.getPoint();
                    
                    String submittedRaw = answer.getAnswer(); // base64-encoded or plain XML
                    String correctXml = dnd.getCorrectDiagramXml(); // stored decoded XML
                    String submittedXml = submittedRaw;

                    if (correctXml != null && correctXml.startsWith("data:image/svg+xml;base64,")) {
                        try {
                            String base64 = correctXml.replaceFirst("data:image/svg\\+xml;base64,", "");
                            byte[] decoded = java.util.Base64.getDecoder().decode(base64);
                            correctXml = new String(decoded, StandardCharsets.UTF_8);
                        } catch (Exception e) {
                            correctXml = "";
                        }
                    }
                    if (submittedRaw != null && submittedRaw.startsWith("data:image/svg+xml;base64,")) {
                        try {
                            String base64 = submittedRaw.replaceFirst("data:image/svg\\+xml;base64,", "");
                            byte[] decodedBytes = java.util.Base64.getDecoder().decode(base64);
                            submittedXml = new String(decodedBytes, java.nio.charset.StandardCharsets.UTF_8);
                        } catch (Exception e) {
                            submittedXml = ""; // fallback to empty
                        }
                    }

                    isCorrect = XmlComparatorUtil.isEquivalent(submittedXml, correctXml);
                    score = isCorrect ? dnd.getPoint() : 0;
                    feedback = dnd.getCorrectDiagramXml();; 
                    break;
                default:
                    break;
            }
    
            // Generate answer ID and insert
            int answerMaxId = answerRepo.findMaxId();
            String answerId = String.format("subans%03d", answerMaxId + 1);
    
            answer.setIdAnswer(answerId);
            answer.setIdSubmission(submissionId);
            answer.setCorrect(isCorrect);
            answer.setScore(score);
            answer.setFeedback(feedback);
            answerRepo.add(answer);
    
            totalScore += score;
        }
    
        // Step 3: Update the total and max score for the submission
        submission.setTotalScore(totalScore);
        submission.setMaxScore(maxScore);
        submissionRepo.update(submission, submissionId); // Make sure you have an update method
    
        return submission;
    }
    
    public List<ExerciseSubmission> getSubmissionsByStudentAndSet(String studentId, String setId) {
        return submissionRepo.findByStudentAndExerciseSet(studentId, setId);
    }

    public List<QuestionAnswer> getAnswersBySubmission(String submissionId) {
        return answerRepo.findBySubmissionId(submissionId);
    }

 }


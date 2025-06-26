package com.namy.udac.backend.repository.exerciseRepo.submission;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.namy.udac.backend.model.exercise.exerciseSubmission.QuestionAnswer;
import com.namy.udac.backend.model.exercise.question.Question;

@Repository
public class Imp_QuestionAnswerRepo implements QuestionAnswerRepository {

    // Assuming you have a JdbcTemplate instance injected here
    private final JdbcTemplate jdbcTemplate;

    public Imp_QuestionAnswerRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<QuestionAnswer> rowMapper () {
        return (rs, rowNum) -> {
            QuestionAnswer answer = new QuestionAnswer();
            answer.setIdAnswer(rs.getString("id_answer"));
            answer.setIdSubmission(rs.getString("id_submission"));
            answer.setIdQuestion(rs.getString("id_question"));
            answer.setQuestionType(Question.QuestionType.valueOf(rs.getString("question_type")));
            answer.setAnswer(rs.getString("answer"));
            answer.setCorrect(rs.getBoolean("is_correct"));
            answer.setScore(rs.getInt("score"));
            answer.setFeedback(rs.getString("feedback"));
            return answer;
        };
    }

    @Override
    public int findMaxId() {
        String sql = "SELECT MAX(CAST(SUBSTRING(id_answer, LENGTH('subans') + 1) AS UNSIGNED)) FROM question_answer WHERE id_answer LIKE 'subans%'";
        try {
            Integer max = jdbcTemplate.queryForObject(sql, Integer.class);
            return (max != null) ? max : 0;
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

    @Override
    public QuestionAnswer add(QuestionAnswer answer) {
        String sql = "INSERT INTO question_answer " +
                "(id_answer, id_submission, id_question, question_type, answer, is_correct, score, feedback) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        int rowAffected = jdbcTemplate.update(sql,
                answer.getIdAnswer(),
                answer.getIdSubmission(),
                answer.getIdQuestion(),
                answer.getQuestionType().name(),
                answer.getAnswer(),
                answer.isCorrect(),
                answer.getScore(),
                answer.getFeedback());

        return rowAffected > 0 ? answer : null;
    }

    @Override
    public List<QuestionAnswer> findBySubmissionId(String submissionId) {
        String sql = "SELECT * FROM question_answer WHERE id_submission = ?";
        return jdbcTemplate.query(sql, rowMapper(), submissionId);
    }
    
}

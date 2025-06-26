package com.namy.udac.backend.repository.exerciseRepo.submission;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.namy.udac.backend.model.exercise.exerciseSubmission.ExerciseSubmission;

@Repository
public class Imp_ExerciseSubmissionRepo implements ExerciseSubmissionRepository{

    private final JdbcTemplate jdbcTemplate;

    public Imp_ExerciseSubmissionRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<ExerciseSubmission> rowMapper() {
        return (rs, rowNum) -> {
            ExerciseSubmission submission = new ExerciseSubmission();
            submission.setIdSubmission(rs.getString("id_submission"));
            submission.setIdExerciseSet(rs.getString("id_exercise_set"));
            submission.setStudentId(rs.getString("student_id"));
            submission.setSubmittedAt(rs.getTimestamp("submitted_at").toLocalDateTime());
            submission.setTotalScore(rs.getInt("total_score"));
            submission.setMaxScore(rs.getInt("max_score"));
            return submission;
        };
    }

    @Override
    public int findMaxId() {
        String sql = "SELECT MAX(CAST(SUBSTRING(id_submission, LENGTH('exsub') + 1) AS UNSIGNED)) FROM exercise_submission WHERE id_submission LIKE 'exsub%'";
        try {
            Integer max = jdbcTemplate.queryForObject(sql, Integer.class);
            return (max != null) ? max : 0;
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

    @Override
    public ExerciseSubmission add(ExerciseSubmission submission) {
        String sql = "INSERT INTO exercise_submission " +
                "(id_submission, id_exercise_set, student_id, submitted_at, total_score, max_score) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        int rowAffected = jdbcTemplate.update(sql,
                submission.getIdSubmission(),
                submission.getIdExerciseSet(),
                submission.getStudentId(),
                submission.getSubmittedAt(),
                submission.getTotalScore(),
                submission.getMaxScore());

        return rowAffected>0 ? submission: null;
    }

    @Override
    public ExerciseSubmission findById(String id) {
        String sql = "SELECT * FROM exercise_submission WHERE id_submission = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper(), id);
    }

    @Override
    public List<ExerciseSubmission> findByStudentAndExerciseSet(String studentId, String exerciseSetId) {
        String sql = "SELECT * FROM exercise_submission WHERE student_id = ? AND id_exercise_set = ? ORDER BY submitted_at DESC";
        return jdbcTemplate.query(sql, rowMapper(), studentId, exerciseSetId);
    }

    @Override
    public ExerciseSubmission update(ExerciseSubmission submission, String id) {
        String sql = "UPDATE exercise_submission SET " +
                "id_exercise_set = ?, student_id = ?, submitted_at = ?, total_score = ?, max_score = ? " +
                "WHERE id_submission = ?";

        int rowAffected = jdbcTemplate.update(sql,
                submission.getIdExerciseSet(),
                submission.getStudentId(),
                submission.getSubmittedAt(),
                submission.getTotalScore(),
                submission.getMaxScore(),
                id);

        return rowAffected > 0 ? submission : null;
    }
}

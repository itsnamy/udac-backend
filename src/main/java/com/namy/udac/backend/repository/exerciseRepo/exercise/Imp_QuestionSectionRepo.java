package com.namy.udac.backend.repository.exerciseRepo.exercise;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.namy.udac.backend.model.exercise.QuestionSection;
import com.namy.udac.backend.model.exercise.question.Question;

@Repository
public class Imp_QuestionSectionRepo implements QuestionSectionRepository {

    private final JdbcTemplate jdbcTemplate;

    public Imp_QuestionSectionRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<QuestionSection> rowMapper() {
        return (rs, rowNum) -> {
            QuestionSection section = new QuestionSection();
            section.setIdQuestionSec(rs.getString("id_question_section"));
            section.setExerciseSetId(rs.getString("id_exercise_set"));
            section.setSectionTitle(rs.getString("section_title"));
            section.setQuestionType(Question.QuestionType.valueOf(rs.getString("question_type")));
            section.setSectionOrder(rs.getInt("section_order"));
            return section;
        };
    }

    @Override
    public int findMaxId() {
        try{
            Integer max = jdbcTemplate.queryForObject(
                "SELECT COALESCE(MAX(CAST(SUBSTRING(id_question_section, 4) AS UNSIGNED)), 0) FROM question_section WHERE id_question_section LIKE 'qsc%'",
                Integer.class
            );
            return max != null ? max : 0;
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

    @Override
    public QuestionSection add(QuestionSection questionSection) {
        String sql = "INSERT INTO question_section (id_question_section, id_exercise_set, section_title, question_type, section_order) " +
                     "VALUES (?, ?, ?, ?, ?)";

        int rowAffected = jdbcTemplate.update(sql,
            questionSection.getIdQuestionSec(),
            questionSection.getExerciseSetId(),
            questionSection.getSectionTitle(),
            questionSection.getQuestionType().name(),
            questionSection.getSectionOrder()
        );

        return rowAffected > 0 ? questionSection : null;
    }

    @Override
    public QuestionSection findById(String id) {
        String sql = "SELECT * FROM question_section WHERE id_question_section = ?";
        try {
            return jdbcTemplate.queryForObject(sql, rowMapper(), id);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<QuestionSection > findByExerciseSets(String idSet) {
        String sql = "SELECT * FROM question_section WHERE id_exercise_set = ? ORDER BY section_order";
        return jdbcTemplate.query(sql, rowMapper(), idSet);
    }

    @Override
    public List<QuestionSection > findAll() {
        String sql = "SELECT * FROM question_section ORDER BY id_exercise_set, section_order";
        return jdbcTemplate.query(sql, rowMapper());
    }

    @Override
    public QuestionSection update(String id, QuestionSection questionSection) {
        String sql = "UPDATE question_section SET section_title = ?, question_type = ?, section_order = ? WHERE id_question_section = ?";

        int rowAffected = jdbcTemplate.update(sql,
            questionSection.getSectionTitle(),
            questionSection.getQuestionType().name(),
            questionSection.getSectionOrder(),
            id
        );

        return rowAffected > 0 ? questionSection : null;
    }

    @Override
    public int deleteById(String id) {
        String sql = "DELETE FROM question_section WHERE id_question_section = ?";
        return jdbcTemplate.update(sql, id);
    }
    
}

package com.namy.udac.backend.repository.exerciseRepo.question;

import java.sql.Blob;
import java.sql.ResultSet;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.namy.udac.backend.model.exercise.question.MCQQuestion;
import com.namy.udac.backend.model.exercise.question.Question;

import org.json.JSONArray;
import org.springframework.dao.DataAccessException;

@Repository
public class Imp_MCQQuestionRepo implements MCQQuestionRepository {

    private final JdbcTemplate jdbcTemplate;

    public Imp_MCQQuestionRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<MCQQuestion> mcqRowMapper() {
        return (ResultSet rs, int rowNum) -> {
            String idQuestion = rs.getString("id_question");
            String idQuestionSec = rs.getString("id_question_section");
            Question.QuestionType type = Question.QuestionType.valueOf(rs.getString("question_type"));
            String text = rs.getString("question_text");
            Blob diagram = rs.getBlob("question_diagram");
            int point = rs.getInt("point");

            JSONArray optionsJson = new JSONArray(rs.getString("mcq_options"));
            String[] options = new String[optionsJson.length()];
            for (int i = 0; i < optionsJson.length(); i++) {
                options[i] = optionsJson.getString(i);
            }

            int answerIndex = rs.getInt("mcq_ans_index");

            return new MCQQuestion(idQuestion, idQuestionSec, type, text, diagram, point, options, answerIndex);
        };
    }

    @Override
    public int findMaxId() {
        String sql = "SELECT MAX(CAST(SUBSTRING(id_question, LENGTH('mcq') + 1) AS UNSIGNED)) FROM question WHERE id_question LIKE 'mcq%'";
        Integer max = jdbcTemplate.queryForObject(sql, Integer.class);
        return (max != null) ? max : 0;
    }

    @Override
    public MCQQuestion add(MCQQuestion mcq) {
        String sqlQuestion = "INSERT INTO question (id_question, id_question_section, question_type, question_text, question_diagram, point) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlMCQ = "INSERT INTO mcq_question (id_question, mcq_options, mcq_ans_index) VALUES (?, ?, ?)";

        int rowAffected = jdbcTemplate.update(sqlQuestion,
                mcq.getIdQuestion(),
                mcq.getIdQuestionSec(),
                mcq.getQuestionType().name(),
                mcq.getQuestionText(),
                mcq.getQuestionDiagram(),
                mcq.getPoint()
        );

        JSONArray optionsJson = new JSONArray(mcq.getMcqOption());

        rowAffected += jdbcTemplate.update(sqlMCQ,
                mcq.getIdQuestion(),
                optionsJson.toString(),
                mcq.getMcqAnsIndex()
        );

        return rowAffected > 1 ? mcq : null;
    }

    @Override
    public MCQQuestion findById(String id) {
        String sql = "SELECT q.*, m.mcq_options, m.mcq_ans_index " +
                     "FROM question q " +
                     "JOIN mcq_question m ON q.id_question = m.id_question " +
                     "WHERE q.id_question = ?";
        try {
            return jdbcTemplate.queryForObject(sql, mcqRowMapper(), id);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<MCQQuestion> findBySectionId(String sectionId) {
        String sql = "SELECT q.*, m.mcq_options, m.mcq_ans_index " +
                     "FROM question q " +
                     "JOIN mcq_question m ON q.id_question = m.id_question " +
                     "WHERE q.id_question_section = ?";
        return jdbcTemplate.query(sql, mcqRowMapper(), sectionId);
    }

    @Override
    public MCQQuestion update(String id, MCQQuestion mcq) {
        String sqlQuestion = "UPDATE question SET id_question_section = ?, question_type = ?, question_text = ?, question_diagram = ?, point = ? WHERE id_question = ?";
        String sqlMCQ = "UPDATE mcq_question SET mcq_options = ?, mcq_ans_index = ? WHERE id_question = ?";

        int rowAffected = jdbcTemplate.update(sqlQuestion,
                mcq.getIdQuestionSec(),
                mcq.getQuestionType().name(),
                mcq.getQuestionText(),
                mcq.getQuestionDiagram(),
                mcq.getPoint(),
                id
        );

        JSONArray optionsJson = new JSONArray(mcq.getMcqOption());

        rowAffected += jdbcTemplate.update(sqlMCQ,
                optionsJson.toString(),
                mcq.getMcqAnsIndex(),
                id
        );

        return rowAffected > 1 ? mcq : null;
    }

    @Override
    public int deleteById(String id) {
        String sql = "DELETE FROM question WHERE id_question = ?";
        return jdbcTemplate.update(sql, id);
    }
}

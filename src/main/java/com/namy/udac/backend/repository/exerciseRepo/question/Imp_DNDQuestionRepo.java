package com.namy.udac.backend.repository.exerciseRepo.question;

import java.sql.Blob;
import java.sql.ResultSet;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.namy.udac.backend.model.exercise.question.DNDQuestion;
import com.namy.udac.backend.model.exercise.question.Question;

@Repository
public class Imp_DNDQuestionRepo implements DNDQuestionRepository {

    private final JdbcTemplate jdbcTemplate;

    public Imp_DNDQuestionRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<DNDQuestion> rowMapper() {
        return (ResultSet rs, int rowNum) -> {
            String id = rs.getString("id_question");
            String secId = rs.getString("id_question_section");
            Question.QuestionType type = Question.QuestionType.valueOf(rs.getString("question_type"));
            String text = rs.getString("question_text");
            Blob diagram = rs.getBlob("question_diagram");
            int point = rs.getInt("point");
            DNDQuestion.DiagramType diagramType = DNDQuestion.DiagramType.valueOf(rs.getString("diagram_type"));
            String correctDiagramJson = rs.getString("correct_diagram_json");
            String correctDiagramXml = rs.getString("correct_diagram_xml");

            DNDQuestion dnd = new DNDQuestion(id, secId, type, text, diagram, point, diagramType, correctDiagramJson, correctDiagramXml);
            return dnd;
        };
    }

    @Override
    public int findMaxId() {
        String sql = "SELECT MAX(CAST(SUBSTRING(id_question, LENGTH('dnd') + 1) AS UNSIGNED)) FROM question WHERE id_question LIKE 'dnd%'";
        Integer max = jdbcTemplate.queryForObject(sql, Integer.class);
        return (max != null) ? max : 0;
    }

    @Override
    public DNDQuestion add(DNDQuestion dnd) {
        String sqlQuestion = "INSERT INTO question (id_question, id_question_section, question_type, question_text, question_diagram, point) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlDND = "INSERT INTO dnd_question (id_question, diagram_type, correct_diagram_json, correct_diagram_xml) VALUES (?, ?, ?, ?)";

        int rowAffected = jdbcTemplate.update(sqlQuestion,
                dnd.getIdQuestion(),
                dnd.getIdQuestionSec(),
                dnd.getQuestionType().name(),
                dnd.getQuestionText(),
                dnd.getQuestionDiagram(),
                dnd.getPoint()
        );

        String json = dnd.getCorrectDiagramJson();
        rowAffected += jdbcTemplate.update(sqlDND,
                dnd.getIdQuestion(),
                dnd.getDiagramType().name(),
                (json == null || json.trim().isEmpty()) ? null : json,
                dnd.getCorrectDiagramXml()
        );

        return rowAffected > 1 ? dnd : null;
    }

    @Override
    public DNDQuestion findById(String id) {
        String sql = "SELECT q.*, d.diagram_type, d.correct_diagram_json, d.correct_diagram_xml " +
                     "FROM question q " +
                     "JOIN dnd_question d ON q.id_question = d.id_question " +
                     "WHERE q.id_question = ?";
        try {
            return jdbcTemplate.queryForObject(sql, rowMapper(), id);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<DNDQuestion> findBySectionId(String sectionId) {
        String sql = "SELECT q.*, d.diagram_type, d.correct_diagram_json, d.correct_diagram_xml " +
                     "FROM question q " +
                     "JOIN dnd_question d ON q.id_question = d.id_question " +
                     "WHERE q.id_question_section = ?";
        return jdbcTemplate.query(sql, rowMapper(), sectionId);
    }

    @Override
    public DNDQuestion update(String id, DNDQuestion dnd) {
        String sqlQuestion = "UPDATE question SET id_question_section = ?, question_type = ?, question_text = ?, question_diagram = ?, point = ? WHERE id_question = ?";
        String sqlDND = "UPDATE dnd_question SET diagram_type = ?, correct_diagram_json = ?, correct_diagram_xml = ? WHERE id_question = ?";

        int rowAffected = jdbcTemplate.update(sqlQuestion,
                dnd.getIdQuestionSec(),
                dnd.getQuestionType().name(),
                dnd.getQuestionText(),
                dnd.getQuestionDiagram(),
                dnd.getPoint(),
                id
        );

        String json = dnd.getCorrectDiagramJson();
        rowAffected += jdbcTemplate.update(sqlDND,
                dnd.getDiagramType().name(),
                (json == null || json.trim().isEmpty()) ? null : json,
                dnd.getCorrectDiagramXml(),
                id
        );

        return rowAffected > 1 ? dnd : null;
    }

    @Override
    public int deleteById(String id) {
        String sql = "DELETE FROM question WHERE id_question = ?";
        return jdbcTemplate.update(sql, id);
    }
}

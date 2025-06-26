package com.namy.udac.backend.repository.exerciseRepo.question;

import java.sql.Blob;
import java.sql.ResultSet;
import java.util.List;

import org.json.JSONArray;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.namy.udac.backend.model.exercise.question.FIBQuestion;
import com.namy.udac.backend.model.exercise.question.Question;

@Repository
public class Imp_FIBQuestionRepo implements FIBQuestionRepository {

    private final JdbcTemplate jdbcTemplate;

    public Imp_FIBQuestionRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<FIBQuestion> fibRowMapper() {
        return (ResultSet rs, int rowNum) -> {
            String idQuestion = rs.getString("id_question");
            String idQuestionSec = rs.getString("id_question_section");
            Question.QuestionType type = Question.QuestionType.valueOf(rs.getString("question_type"));
            String text = rs.getString("question_text");
            Blob diagram = rs.getBlob("question_diagram");
            int point = rs.getInt("point");

            JSONArray ansJson = new JSONArray(rs.getString("fib_answers"));
            String[] answers = new String[ansJson.length()];
            for (int i = 0; i < ansJson.length(); i++) {
                answers[i] = ansJson.getString(i);
            }

            boolean caseSensitive = rs.getBoolean("case_sensitive");

            return new FIBQuestion(idQuestion, idQuestionSec, type, text, diagram, point, answers, caseSensitive);
        };
    }
    

    @Override
    public int findMaxId() {
        String sql = "SELECT MAX(CAST(SUBSTRING(id_question, LENGTH('fib') + 1) AS UNSIGNED)) FROM question WHERE id_question LIKE 'fib%'";
        Integer max = jdbcTemplate.queryForObject(sql, Integer.class);
        return (max != null) ? max : 0;
    }

    @Override
    public FIBQuestion add(FIBQuestion fib) {
        String sqlQuestion = "INSERT INTO question (id_question, id_question_section, question_type, question_text, question_diagram, point) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlFIB = "INSERT INTO fib_question (id_question, fib_answers, case_sensitive) VALUES (?, ?, ?)";

        int rowAffected = jdbcTemplate.update(sqlQuestion,
                fib.getIdQuestion(),
                fib.getIdQuestionSec(),
                fib.getQuestionType().name(),
                fib.getQuestionText(),
                fib.getQuestionDiagram(),
                fib.getPoint()
        );

        JSONArray answersJson = new JSONArray(fib.getFibAnswer());

        rowAffected += jdbcTemplate.update(sqlFIB,
                fib.getIdQuestion(),
                answersJson.toString(),
                fib.isCaseSensitive()
        );

        return rowAffected > 1 ? fib : null;
    }

    @Override
    public FIBQuestion findById(String id) {
        String sql = "SELECT q.*, f.fib_answers, f.case_sensitive " +
                     "FROM question q " +
                     "JOIN fib_question f ON q.id_question = f.id_question " +
                     "WHERE q.id_question = ?";
        try {
            return jdbcTemplate.queryForObject(sql, fibRowMapper(), id);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<FIBQuestion> findBySectionId(String idSection) {
        String sql = "SELECT q.*, f.fib_answers, f.case_sensitive " +
                     "FROM question q " +
                     "JOIN fib_question f ON q.id_question = f.id_question " +
                     "WHERE q.id_question_section = ?";
        return jdbcTemplate.query(sql, fibRowMapper(), idSection);
    }

    @Override
    public FIBQuestion update(String id, FIBQuestion fib) {
        String sqlQuestion = "UPDATE question SET id_question_section = ?, question_type = ?, question_text = ?, question_diagram = ?, point = ? WHERE id_question = ?";
        String sqlFIB = "UPDATE fib_question SET fib_answers = ?, case_sensitive = ? WHERE id_question = ?";

        int rowAffected = jdbcTemplate.update(sqlQuestion,
                fib.getIdQuestionSec(),
                fib.getQuestionType().name(),
                fib.getQuestionText(),
                fib.getQuestionDiagram(),
                fib.getPoint(),
                id
        );

        JSONArray answersJson = new JSONArray(fib.getFibAnswer());

        rowAffected += jdbcTemplate.update(sqlFIB,
                answersJson.toString(),
                fib.isCaseSensitive(),
                id
        );

        return rowAffected > 1 ? fib : null;
    }

    @Override
    public int deleteById(String id) {
        String sql = "DELETE FROM question WHERE id_question = ?";
        return jdbcTemplate.update(sql, id);
    }
}

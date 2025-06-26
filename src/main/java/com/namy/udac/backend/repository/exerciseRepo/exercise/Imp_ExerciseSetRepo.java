package com.namy.udac.backend.repository.exerciseRepo.exercise;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.namy.udac.backend.model.exercise.ExerciseSet;
import com.namy.udac.backend.model.exercise.ExerciseSet.ExerciseType;

@Repository
public class Imp_ExerciseSetRepo implements ExerciseSetRepository {

    private final JdbcTemplate jdbcTemplate;

    public Imp_ExerciseSetRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<ExerciseSet> exerciseSetRowMapper() {
        return (rs, rowNum) -> {
            ExerciseSet set = new ExerciseSet();
            set.setIdExerciseSet(rs.getString("id_exercise_set"));
            set.setExerciseSection(rs.getString("exercise_section"));
            set.setExerciseType(ExerciseType.valueOf(rs.getString("exercise_type")));
            set.setExerciseTitle(rs.getString("exercise_title"));
            set.setExerciseDesc(rs.getString("exercise_desc"));
            set.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            set.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
            set.setOwnedBy(rs.getString("owned_by"));
            return set;
        };
    }

    @Override
    public int findMaxId() {
        String sql = "SELECT MAX(CAST(SUBSTRING(id_exercise_set, 7) AS UNSIGNED)) FROM exercise_set WHERE id_exercise_set LIKE 'exeset%'";
        try {
            Integer max = jdbcTemplate.queryForObject(sql, Integer.class);
            return (max != null) ? max : 0;
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

    @Override
    public ExerciseSet add(ExerciseSet exerciseSet) {
        String sql = "INSERT INTO exercise_set (id_exercise_set, exercise_section, exercise_type, exercise_title, exercise_desc, created_at, updated_at, owned_by) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        int rowAffected = jdbcTemplate.update(sql,
                exerciseSet.getIdExerciseSet(),
                exerciseSet.getExerciseSection(),
                exerciseSet.getExerciseType().name(),
                exerciseSet.getExerciseTitle(),
                exerciseSet.getExerciseDesc(),
                exerciseSet.getCreatedAt(),
                exerciseSet.getUpdatedAt(),
                exerciseSet.getOwnedBy()
        );
        return rowAffected > 0 ? exerciseSet : null;
    }

    @Override
    public ExerciseSet findById(String id) {
        String sql = "SELECT * FROM exercise_set WHERE id_exercise_set = ?";
        try {
            return jdbcTemplate.queryForObject(sql, exerciseSetRowMapper(), id);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<ExerciseSet> findSetsBySection(String sectionTitle) {
        String sql = "SELECT * FROM exercise_set WHERE exercise_section = ?";
        return jdbcTemplate.query(sql, exerciseSetRowMapper(), sectionTitle);
    }

    @Override
    public List<ExerciseSet> findBySectionAndType(String sectionTitle, String exerciseType) {
        String sql = "SELECT * FROM exercise_set WHERE exercise_section = ? AND exercise_type = ?";
        return jdbcTemplate.query(sql, exerciseSetRowMapper(), sectionTitle, exerciseType);
    }


    @Override
    public List<ExerciseSet> findByOwner(String owner) {
        String sql = "SELECT * FROM exercise_set WHERE owned_by = ?";
        return jdbcTemplate.query(sql, exerciseSetRowMapper(), owner);
    }

    @Override
    public List<ExerciseSet> findByType(String type) {
        String sql = "SELECT * FROM exercise_set WHERE exercise_type = ?";
        return jdbcTemplate.query(sql, exerciseSetRowMapper(), type);
    }

    @Override
    public List<ExerciseSet> findByTypeAndOwner(String exerciseType, String owner) {
        String sql = "SELECT * FROM exercise_set WHERE exercise_type = ? AND owned_by = ?";
        return jdbcTemplate.query(sql, exerciseSetRowMapper(), exerciseType, owner);
    }

    @Override
    public List<ExerciseSet> findAll() {
        String sql = "SELECT * FROM exercise_set";
        return jdbcTemplate.query(sql, exerciseSetRowMapper());
    }

    @Override
    public ExerciseSet update(String id, ExerciseSet exerciseSet) {
        String sql = "UPDATE exercise_set SET exercise_section = ?, exercise_type = ?, exercise_title = ?, exercise_desc = ?, updated_at = ?, owned_by = ? WHERE id_exercise_set = ?";
        int rowAffected = jdbcTemplate.update(sql,
                exerciseSet.getExerciseSection(),
                exerciseSet.getExerciseType().name(),
                exerciseSet.getExerciseTitle(),
                exerciseSet.getExerciseDesc(),
                exerciseSet.getUpdatedAt(),
                exerciseSet.getOwnedBy(),
                id
        );
        return rowAffected > 0 ? exerciseSet : null;
    }

    @Override
    public int deleteById(String idSet) {
        String sql = "DELETE FROM exercise_set WHERE id_exercise_set = ?";
        return jdbcTemplate.update(sql, idSet);
    }
    
}

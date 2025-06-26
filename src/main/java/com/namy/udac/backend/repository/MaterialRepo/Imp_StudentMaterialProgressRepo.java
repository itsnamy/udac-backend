package com.namy.udac.backend.repository.MaterialRepo;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.namy.udac.backend.model.learningMaterial.StudentMaterialProgress;

@Repository
public class Imp_StudentMaterialProgressRepo implements StudentMaterialProgressRepository {

    private final JdbcTemplate jdbcTemplate;

    public Imp_StudentMaterialProgressRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<StudentMaterialProgress> rowMapper = (rs, rowNum) -> {
        StudentMaterialProgress progress = new StudentMaterialProgress();
        progress.setIdMaterialProgress(rs.getString("id_material_progress"));
        progress.setStudentId(rs.getString("student_id"));
        progress.setMaterialSetId(rs.getString("material_set_id"));
        progress.setMaterialId(rs.getString("material_id"));
        progress.setMaterialType(rs.getString("material_type"));
        progress.setCompletedAt(rs.getTimestamp("completed_at"));
        return progress;
    };

    @Override
    public int findMaxId() {
        String sql = "SELECT MAX(CAST(SUBSTRING(id_material_progress, 9) AS UNSIGNED)) FROM student_material_progress WHERE id_material_progress LIKE 'progress%'";
        try {
            Integer max = jdbcTemplate.queryForObject(sql, Integer.class);
            return (max != null) ? max : 0;
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

    @Override
    public StudentMaterialProgress add(StudentMaterialProgress progress) {
        String sql = "INSERT INTO student_material_progress (id_material_progress, student_id, material_set_id, material_id, material_type, completed_at) VALUES (?, ?, ?, ?, ?, ?)";
        int rows = jdbcTemplate.update(sql,
                progress.getIdMaterialProgress(),
                progress.getStudentId(),
                progress.getMaterialSetId(),
                progress.getMaterialId(),
                progress.getMaterialType(),
                progress.getCompletedAt());
        return (rows > 0) ? progress : null;
    }

    @Override
    public List<StudentMaterialProgress> findByStudent(String studentId) {
        String sql = "SELECT * FROM student_material_progress WHERE student_id = ?";
        return jdbcTemplate.query(sql, rowMapper, studentId);
    }

    @Override
    public List<StudentMaterialProgress> findByMaterialSet(String studentId, String materialSetId) {
        String sql = "SELECT * FROM student_material_progress WHERE student_id = ? AND material_set_id = ?";
        return jdbcTemplate.query(sql, rowMapper, studentId, materialSetId);
    }

    @Override
    public boolean isMaterialCompleted(String studentId, String materialId) {
        String sql = "SELECT COUNT(*) FROM student_material_progress WHERE student_id = ? AND material_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, studentId, materialId);
        return count != null && count > 0;
    }

    @Override
    public int deleteById(String id) {
        String sql = "DELETE FROM student_material_progress WHERE id_material_progress = ?";
        return jdbcTemplate.update(sql, id);
    }
}
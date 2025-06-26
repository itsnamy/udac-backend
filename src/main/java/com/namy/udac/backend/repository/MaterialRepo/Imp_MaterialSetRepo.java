package com.namy.udac.backend.repository.MaterialRepo;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.namy.udac.backend.model.learningMaterial.MaterialSet;

@Repository
public class Imp_MaterialSetRepo implements MaterialSetRepository {
    
    private final JdbcTemplate jdbcTemplate;

    public Imp_MaterialSetRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<MaterialSet> rowMapper = (rs, rowNum) -> {
        MaterialSet set = new MaterialSet();
        set.setIdMaterialSet(rs.getString("id_material_set"));
        set.setTopicTitle(rs.getString("topic_title"));
        set.setTopicDesc(rs.getString("topic_desc"));
        set.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        set.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        set.setOwnedBy(rs.getString("owned_by"));
        return set;
    };

    @Override
    public MaterialSet addSet(MaterialSet set) {
        String sql = "INSERT INTO material_set (id_material_set, topic_title, topic_desc, owned_by) VALUES (?, ?, ?, ?)";
        int rowAffected = jdbcTemplate.update(sql,  set.getIdMaterialSet(), set.getTopicTitle(), set.getTopicDesc(), set.getOwnedBy());
        return (rowAffected > 0) ? set : null;
    }

    @Override
    public int findMaxIdSet() {
        String sql = "SELECT MAX(CAST(SUBSTRING(id_material_set, 7) AS UNSIGNED)) FROM material_set WHERE id_material_set LIKE 'matset%'";
        try {
            Integer max = jdbcTemplate.queryForObject(sql, Integer.class);
            return (max != null) ? max : 0;
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }   

    @Override
    public MaterialSet findById(String id) {
        String sql = "SELECT * FROM material_set WHERE id_material_set = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    @Override
    public List<MaterialSet> findAll() {
        String sql = "SELECT * FROM material_set";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public MaterialSet update(String id, MaterialSet set) {
        String sql = "UPDATE material_set SET topic_title = ?, topic_desc = ?, updated_at = CURRENT_TIMESTAMP WHERE id_material_set = ?";
        int rowAffected = jdbcTemplate.update(sql, set.getTopicTitle(), set.getTopicDesc(), id);
        return (rowAffected > 0) ? set : null;
    }

    @Override
    public int deleteById(String id) {
        String sql = "DELETE FROM material_set WHERE id_material_set = ?";
        return jdbcTemplate.update(sql, id);
    }   
}

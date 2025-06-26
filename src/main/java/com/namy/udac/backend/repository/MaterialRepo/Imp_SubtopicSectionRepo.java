package com.namy.udac.backend.repository.MaterialRepo;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.namy.udac.backend.model.learningMaterial.SubtopicSection;

@Repository
public class Imp_SubtopicSectionRepo implements SubtopicMaterialRepository {

    private final JdbcTemplate jdbcTemplate;

    public Imp_SubtopicSectionRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<SubtopicSection> rowMapper = (rs, rowNum) -> {
        SubtopicSection section = new SubtopicSection();
        section.setIdSubtopicSection(rs.getString("id_subtopic_section"));
        section.setIdMaterialSet(rs.getString("id_material_set"));
        section.setSubtopicTitle(rs.getString("subtopic_title"));
        return section;
    };
    
    @Override
    public int findMaxId() {
        String sql = "SELECT MAX(CAST(SUBSTRING(id_subtopic_section, 9) AS UNSIGNED)) FROM subtopic_section WHERE id_subtopic_section LIKE 'subtopic%'";
        try {
            Integer max = jdbcTemplate.queryForObject(sql, Integer.class);
            return (max != null) ? max : 0;
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

    @Override
    public SubtopicSection add(SubtopicSection section) {
        String sql = "INSERT INTO subtopic_section (id_subtopic_section, id_material_set, subtopic_title) VALUES (?, ?, ?)";
        int rows = jdbcTemplate.update(sql,
                section.getIdSubtopicSection(),
                section.getIdMaterialSet(),
                section.getSubtopicTitle());
        return (rows > 0) ? section : null;
    }

    @Override
    public SubtopicSection findById(String id) {
        String sql = "SELECT * FROM subtopic_section WHERE id_subtopic_section = ?";
        try {
            return jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<SubtopicSection> findByMaterialSets(String idSet) {
        String sql = "SELECT * FROM subtopic_section WHERE id_material_set = ?";
        return jdbcTemplate.query(sql, rowMapper, idSet);
    }

    @Override
    public List<SubtopicSection> findAll() {
        String sql = "SELECT * FROM subtopic_section";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public SubtopicSection update(String id, SubtopicSection section) {
        String sql = "UPDATE subtopic_section SET subtopic_title = ?, id_material_set = ? WHERE id_subtopic_section = ?";
        int rows = jdbcTemplate.update(sql,
                section.getSubtopicTitle(),
                section.getIdMaterialSet(),
                id);
        return (rows > 0) ? section : null;
    }

    @Override
    public int deleteById(String id) {
        String sql = "DELETE FROM subtopic_section WHERE id_subtopic_section = ?";
        return jdbcTemplate.update(sql, id);
    }
    
}

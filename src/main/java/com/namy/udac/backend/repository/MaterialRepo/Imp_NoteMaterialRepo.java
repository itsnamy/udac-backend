package com.namy.udac.backend.repository.MaterialRepo;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.namy.udac.backend.model.learningMaterial.NoteMaterial;

@Repository
public class Imp_NoteMaterialRepo implements NoteMaterialRepository {

    private final JdbcTemplate jdbcTemplate;

    public Imp_NoteMaterialRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<NoteMaterial> rowMapper = (rs, rowNum) -> {
        NoteMaterial note = new NoteMaterial();
        note.setIdMaterialNote(rs.getString("id_material_note"));
        note.setIdSubtopicSection(rs.getString("id_subtopic_section"));
        note.setNoteTitle(rs.getString("note_title"));
        note.setNoteContent(rs.getString("note_content"));
        return note;
    };
    
    @Override
    public int findMaxId() {
        String sql = "SELECT MAX(CAST(SUBSTRING(id_material_note, 5) AS UNSIGNED)) FROM material_note WHERE id_material_note LIKE 'note%'";
        Integer max = jdbcTemplate.queryForObject(sql, Integer.class);
        return (max != null) ? max : 0;
    }

    @Override
    public NoteMaterial add(NoteMaterial noteMaterial) {
        String sql = "INSERT INTO material_note (id_material_note, id_subtopic_section, note_title, note_content) VALUES (?, ?, ?, ?)";
        int rows = jdbcTemplate.update(sql,
                noteMaterial.getIdMaterialNote(),
                noteMaterial.getIdSubtopicSection(),
                noteMaterial.getNoteTitle(),
                noteMaterial.getNoteContent());
        return (rows > 0) ? noteMaterial : null;
    }

    @Override
    public NoteMaterial findById(String id) {
        String sql = "SELECT * FROM material_note WHERE id_material_note = ?";
        try {
            return jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<NoteMaterial> findBySection(String idSection) {
        String sql = "SELECT * FROM material_note WHERE id_subtopic_section = ?";
        return jdbcTemplate.query(sql, rowMapper, idSection);
    }

    @Override
    public List<NoteMaterial> findAll() {
        String sql = "SELECT * FROM material_note";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public NoteMaterial update(String id, NoteMaterial noteMaterial) {
        String sql = "UPDATE material_note SET id_subtopic_section = ?, note_title = ?, note_content = ? WHERE id_material_note = ?";
        int rows = jdbcTemplate.update(sql,
                noteMaterial.getIdSubtopicSection(),
                noteMaterial.getNoteTitle(),
                noteMaterial.getNoteContent(),
                id);
        return (rows > 0) ? noteMaterial : null;
    }

    @Override
    public int deleteById(String id) {
        String sql = "DELETE FROM material_note WHERE id_material_note = ?";
        return jdbcTemplate.update(sql, id);
    }   
}

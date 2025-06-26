package com.namy.udac.backend.repository.tutorialRepo;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.namy.udac.backend.model.tutorial.SQLTutorialSet;

@Repository
public class Imp_SQLTutorialRepo implements SQLTutorialSetRepository {

    private final JdbcTemplate jdbcTemplate;

    public Imp_SQLTutorialRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<SQLTutorialSet> rowMapper = (rs, rowNum) -> {
        SQLTutorialSet set = new SQLTutorialSet();
        set.setIdTutorialSet(rs.getString("id_tutorial_set"));
        set.setTutorialTitle(rs.getString("tutorial_title"));
        set.setTutorialInstructions(rs.getString("tutorial_instructions"));
        set.setExampleCode(rs.getString("example_code"));
        set.setDatasetJson(rs.getString("dataset_json"));
        return set;
    };

    @Override
    public SQLTutorialSet addSet(SQLTutorialSet set) {
        String sql = "INSERT INTO sql_tutorial_set (id_tutorial_set, tutorial_title, tutorial_instructions, example_code, dataset_json) " +
                 "VALUES (?, ?, ?, ?, ?)";
        int rowAffected = jdbcTemplate.update(sql,
            set.getIdTutorialSet(),
            set.getTutorialTitle(),
            set.getTutorialInstructions(),
            set.getExampleCode(),
            set.getDatasetJson()
        );
        return rowAffected > 0 ? set : null;
    }

    @Override
    public int findMaxIdSet() {
        String sql = "SELECT MAX(CAST(SUBSTRING(id_tutorial_set, 4) AS UNSIGNED)) " +
                 "FROM sql_tutorial_set WHERE id_tutorial_set LIKE 'sql%'";
        try {
            Integer max = jdbcTemplate.queryForObject(sql, Integer.class);
            return (max != null) ? max : 0;
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

    @Override
    public SQLTutorialSet findById(String id) {
        String sql = "SELECT * FROM sql_tutorial_set WHERE id_tutorial_set = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    @Override
    public List<SQLTutorialSet> findAll() {
        String sql = "SELECT * FROM sql_tutorial_set ORDER BY id_tutorial_set";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public SQLTutorialSet update(String id, SQLTutorialSet set) {
        String sql = "UPDATE sql_tutorial_set SET tutorial_title = ?, tutorial_instructions = ?, example_code = ?, dataset_json = ? " +
                     "WHERE id_tutorial_set = ?";
        int rowAffected = jdbcTemplate.update(sql, set.getTutorialTitle(), set.getTutorialInstructions(),
                set.getExampleCode(), set.getDatasetJson(), id);
        return rowAffected > 0 ? set : null;
    }

    @Override
    public int deleteById(String id) {
        String sql = "DELETE FROM sql_tutorial_set WHERE id_tutorial_set = ?";
        return jdbcTemplate.update(sql, id);
    }
}

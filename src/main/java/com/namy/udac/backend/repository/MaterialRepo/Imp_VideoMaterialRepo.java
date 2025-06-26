package com.namy.udac.backend.repository.MaterialRepo;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.namy.udac.backend.model.learningMaterial.VideoMaterial;

@Repository
public class Imp_VideoMaterialRepo implements VideoMaterialRepository {

    private final JdbcTemplate jdbcTemplate;

    public Imp_VideoMaterialRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<VideoMaterial> rowMapper = (rs, rowNum) -> {
        VideoMaterial video = new VideoMaterial();
        video.setIdVideo(rs.getString("id_video"));
        video.setIdSubtopicSection(rs.getString("id_subtopic_section"));
        video.setVideoTitle(rs.getString("video_title"));
        video.setVideoPath(rs.getString("video_path"));
        return video;
    };
    
    @Override
    public int findMaxId() {
        String sql = "SELECT MAX(CAST(SUBSTRING(id_video, 6) AS UNSIGNED)) FROM material_video WHERE id_video LIKE 'video%'";
        try {
            Integer max = jdbcTemplate.queryForObject(sql, Integer.class);
            return (max != null) ? max : 0;
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

    @Override
    public VideoMaterial add(VideoMaterial videoMaterial) {
        String sql = "INSERT INTO material_video (id_video, id_subtopic_section, video_title, video_path) VALUES (?, ?, ?, ?)";
        int rows = jdbcTemplate.update(sql,
                videoMaterial.getIdVideo(),
                videoMaterial.getIdSubtopicSection(),
                videoMaterial.getVideoTitle(),
                videoMaterial.getVideoPath());
        return (rows > 0) ? videoMaterial : null;
    }

    @Override
    public VideoMaterial findById(String id) {
        String sql = "SELECT * FROM material_video WHERE id_video = ?";
        try {
            return jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<VideoMaterial> findBySection(String idSection) {
        String sql = "SELECT * FROM material_video WHERE id_subtopic_section = ?";
        return jdbcTemplate.query(sql, rowMapper, idSection);
    }

    @Override
    public List<VideoMaterial> findAll() {
        String sql = "SELECT * FROM material_video";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public VideoMaterial update(String id, VideoMaterial videoMaterial) {
        String sql = "UPDATE material_video SET video_title = ?, video_path = ?, id_subtopic_section = ? WHERE id_video = ?";
        int rows = jdbcTemplate.update(sql,
                videoMaterial.getVideoTitle(),
                videoMaterial.getVideoPath(),
                videoMaterial.getIdSubtopicSection(),
                id);
        return (rows > 0) ? videoMaterial : null;
    }

    @Override
    public int deleteById(String id) {
        String sql = "DELETE FROM material_video WHERE id_video = ?";
        return jdbcTemplate.update(sql, id);
    }
    
}

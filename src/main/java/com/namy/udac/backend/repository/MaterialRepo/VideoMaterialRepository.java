package com.namy.udac.backend.repository.MaterialRepo;

import java.util.List;

import com.namy.udac.backend.model.learningMaterial.VideoMaterial;

public interface VideoMaterialRepository {
    int findMaxId();
    VideoMaterial add(VideoMaterial videoMaterial);
    VideoMaterial findById(String id);
    List<VideoMaterial> findBySection(String idSection);
    List<VideoMaterial> findAll();
    VideoMaterial update(String id, VideoMaterial videoMaterial);
    int deleteById(String id);
}

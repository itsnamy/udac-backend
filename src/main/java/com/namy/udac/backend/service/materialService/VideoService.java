package com.namy.udac.backend.service.materialService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.namy.udac.backend.model.learningMaterial.VideoMaterial;
import com.namy.udac.backend.repository.MaterialRepo.VideoMaterialRepository;

@Service
public class VideoService {
    
    @Autowired
    private VideoMaterialRepository videoRepo;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public VideoMaterial addVideo(VideoMaterial video) {
        // Generate new idLessonVideo
        int maxId = videoRepo.findMaxId();
        String newId = "video" + String.format("%03d", maxId + 1);
        video.setIdVideo(newId);
        return videoRepo.add(video);
    }

    public VideoMaterial uploadVideoFile(MultipartFile file, String idSubtopicSection, String videoTitle) throws IOException {
        // Create upload directory if it doesn't exist
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Generate unique file name
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Prepare VideoMaterial object
        VideoMaterial video = new VideoMaterial();
        video.setIdSubtopicSection(idSubtopicSection);
        video.setVideoTitle(videoTitle);
        video.setVideoPath(filePath.toString()); 

        return addVideo(video); // Save video path and other details to the database
    }
    
    public VideoMaterial getById(String idLessonVideo) {
        return videoRepo.findById(idLessonVideo);
    }

    public List<VideoMaterial> getBySection(String idSection) {
        return videoRepo.findBySection(idSection);
    }

    public List<VideoMaterial> getAll() {
        return videoRepo.findAll();
    }

    public VideoMaterial updateVideo(VideoMaterial updatedVideo) {
        // Fetch the existing lesson video to update
        String id = updatedVideo.getIdVideo();
        VideoMaterial video = videoRepo.findById(id);
        if (video == null) {
            return null; // Video not found
        }
        // Update fields
        if (updatedVideo.getIdSubtopicSection() != null) {
            video.setIdSubtopicSection(updatedVideo.getIdSubtopicSection());
        }
        if (updatedVideo.getVideoTitle() != null) {
            video.setVideoTitle(updatedVideo.getVideoTitle());
        }
        if (updatedVideo.getVideoPath() != null) {
            video.setVideoPath(updatedVideo.getVideoPath());
        }
        return videoRepo.update(id, video);
    }

    public VideoMaterial replaceVideoFile(String id, MultipartFile file, String idSubtopicSection, String videoTitle) throws IOException {
        VideoMaterial existing = videoRepo.findById(id);
        if (existing == null) return null;
    
        // Save new file
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
    
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path newFilePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), newFilePath, StandardCopyOption.REPLACE_EXISTING);
    
        // Update video fields
        existing.setVideoTitle(videoTitle);
        existing.setIdSubtopicSection(idSubtopicSection);
        existing.setVideoPath(newFilePath.toString());
    
        return videoRepo.update(id, existing);
    }
    

    public boolean deleteById(String id) {
        return videoRepo.deleteById(id) > 0;
    }

}

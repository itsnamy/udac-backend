package com.namy.udac.backend.controller.material;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.namy.udac.backend.model.learningMaterial.VideoMaterial;
import com.namy.udac.backend.service.materialService.VideoService;

@RestController
@RequestMapping("/material/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<VideoMaterial> uploadVideo(
            @RequestParam("file") MultipartFile file,
            @RequestParam("idSubtopicSection") String idSubtopicSection,
            @RequestParam("videoTitle") String videoTitle
    ) {
        try {
            VideoMaterial uploadedVideo = videoService.uploadVideoFile(file, idSubtopicSection, videoTitle);
            return ResponseEntity.status(HttpStatus.CREATED).body(uploadedVideo);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/view/all")
    public ResponseEntity<List<VideoMaterial>> getAllVideos() {
        return ResponseEntity.ok(videoService.getAll());
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<VideoMaterial> getVideoById(@PathVariable String id) {
        VideoMaterial video = videoService.getById(id);
        if (video == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(video);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<VideoMaterial> updateVideo(
            @PathVariable String id,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam("videoTitle") String videoTitle,
            @RequestParam("idSubtopicSection") String idSubtopicSection
    ) {
        try {
            VideoMaterial updatedVideo;
            if (file != null && !file.isEmpty()) {
                // Replace video file and update metadata
                updatedVideo = videoService.replaceVideoFile(id, file, idSubtopicSection, videoTitle);
            } else {
                // Only update metadata
                updatedVideo = new VideoMaterial();
                updatedVideo.setIdVideo(id);
                updatedVideo.setVideoTitle(videoTitle);
                updatedVideo.setIdSubtopicSection(idSubtopicSection);
                updatedVideo = videoService.updateVideo(updatedVideo);
            }

            if (updatedVideo == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(updatedVideo);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteVideo(@PathVariable String id) {
        boolean deleted = videoService.deleteById(id);
        if (!deleted) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/stream/{id}")
    public ResponseEntity<Resource> streamVideo(
            @PathVariable String id,
            @RequestHeader(value = "Range", required = false) String rangeHeader
    ) throws IOException {
        VideoMaterial video = videoService.getById(id);
        if (video == null) {
            return ResponseEntity.notFound().build();
        }

        Path path = Paths.get(video.getVideoPath());
        if (!Files.exists(path)) {
            return ResponseEntity.notFound().build();
        }

        long fileSize = Files.size(path);
        String mimeType = Files.probeContentType(path);
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }

        if (rangeHeader == null) {
            Resource resource = new UrlResource(path.toUri());
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(mimeType))
                    .contentLength(fileSize)
                    .body(resource);
        }

        // Parse the range header (e.g. bytes=1000-)
        long start = 0, end = fileSize - 1;
        String[] ranges = rangeHeader.replace("bytes=", "").split("-");
        try {
            start = Long.parseLong(ranges[0]);
            if (ranges.length > 1 && !ranges[1].isEmpty()) {
                end = Long.parseLong(ranges[1]);
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if (end >= fileSize) {
            end = fileSize - 1;
        }

        long rangeLength = end - start + 1;

        byte[] buffer;
        try (InputStream inputStream = Files.newInputStream(path)) {
            inputStream.skip(start);
            buffer = inputStream.readNBytes((int) rangeLength);
        }

        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .header(HttpHeaders.CONTENT_TYPE, mimeType)
                .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(rangeLength))
                .header(HttpHeaders.CONTENT_RANGE, String.format("bytes %d-%d/%d", start, end, fileSize))
                .body(new ByteArrayResource(buffer));
    }
}

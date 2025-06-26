package com.namy.udac.backend.model.learningMaterial;

public class VideoMaterial {
    private String idVideo;
    private String idSubtopicSection;
    private String videoTitle;
    private String videoPath;

    public VideoMaterial() {
    }

    public VideoMaterial(VideoMaterial videoMaterial) {
        this.idVideo = videoMaterial.idVideo;
        this.idSubtopicSection = videoMaterial.idSubtopicSection;
        this.videoTitle = videoMaterial.videoTitle;
        this.videoPath = videoMaterial.videoPath;
    }

    public VideoMaterial(String idVideo, String idSubtopicSection, String videoTitle, String videoPath) {
        this.idVideo = idVideo;
        this.idSubtopicSection = idSubtopicSection;
        this.videoTitle = videoTitle;
        this.videoPath = videoPath;
    }

    public String getIdVideo() {
        return idVideo;
    }

    public void setIdVideo(String idVideo) {
        this.idVideo = idVideo;
    }

    public String getIdSubtopicSection() {
        return idSubtopicSection;
    }

    public void setIdSubtopicSection(String idSubtopicSection) {
        this.idSubtopicSection = idSubtopicSection;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }
}

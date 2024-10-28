package com.sarfaraz.elearning.rest;

import com.sarfaraz.elearning.model.Media;
import com.sarfaraz.elearning.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/media")
public class MediaController {

    private final MediaService mediaService;

    @Autowired
    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Media> uploadVideo(@RequestParam("file") MultipartFile videoFile) {
        Media media = mediaService.uploadVideo(videoFile);
        return ResponseEntity.ok(media);
    }
}

package com.rehman.elearning.rest;

import com.rehman.elearning.rest.dto.outbound.MediaResponseDTO;
import com.rehman.elearning.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    // Endpoint for uploading a video
    @PostMapping("/upload")
    public ResponseEntity<MediaResponseDTO> uploadVideo(
            @RequestParam("file") MultipartFile videoFile,
            @RequestParam("url") String url,
            @RequestParam("type") String type,
            @RequestParam("duration") String duration) {
        MediaResponseDTO response = mediaService.uploadVideo(videoFile,url,type,duration);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

//     Endpoint for fetching media details by ID
    @GetMapping("/{mediaId}")
    public ResponseEntity<MediaResponseDTO> getMediaById(@PathVariable Long mediaId) {
        MediaResponseDTO response = mediaService.getMediaById(mediaId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}

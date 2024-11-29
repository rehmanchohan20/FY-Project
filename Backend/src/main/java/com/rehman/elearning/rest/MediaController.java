package com.rehman.elearning.rest;

import com.rehman.elearning.rest.dto.inbound.MediaRequestDTO;
import com.rehman.elearning.rest.dto.outbound.MediaResponseDTO;
import com.rehman.elearning.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/media")
public class MediaController {

    @Autowired
    private MediaService mediaService;

    @PostMapping("/{moduleId}/upload-media")
    public ResponseEntity<MediaResponseDTO> uploadMedia(
            @PathVariable Long moduleId,        // Module ID
            @RequestParam("media") MultipartFile mediaFile) throws IOException {  // Media file

        MediaResponseDTO responseDTO = mediaService.uploadVideo(moduleId, mediaFile);
        return ResponseEntity.ok(responseDTO);
    }


    @GetMapping("/{mediaId}")
    public ResponseEntity<MediaResponseDTO> getMediaById(@PathVariable Long mediaId) {
        MediaResponseDTO response = mediaService.getMediaById(mediaId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{mediaId}")
    public ResponseEntity<Void> deleteMedia(@PathVariable Long mediaId) {
        mediaService.deleteMedia(mediaId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{mediaId}")
    public ResponseEntity<MediaResponseDTO> updateMedia(
            @PathVariable Long mediaId,
            @RequestBody MediaRequestDTO request
    ) {
        MediaResponseDTO updatedMedia = mediaService.updateMedia(mediaId, request);
        return ResponseEntity.ok(updatedMedia);
    }
}

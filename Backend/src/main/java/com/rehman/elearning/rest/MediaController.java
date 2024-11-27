package com.rehman.elearning.rest;

import com.rehman.elearning.constants.ErrorEnum;
import com.rehman.elearning.model.Media;
import com.rehman.elearning.rest.dto.inbound.MediaRequestDTO;
import com.rehman.elearning.rest.dto.inbound.MediaUploadRequestDTO;
import com.rehman.elearning.rest.dto.outbound.MediaResponseDTO;
import com.rehman.elearning.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/media")
public class MediaController {

    @Autowired
    private MediaService mediaService;

    // Endpoint for uploading a video (now accepts Base64 string)

    @PostMapping("/{courseId}/upload")
    public ResponseEntity<MediaResponseDTO> uploadMedia(
            @PathVariable Long courseId, @RequestBody MediaUploadRequestDTO mediaUploadRequest) throws IOException {
        MediaResponseDTO responseDTO = mediaService.uploadVideo(mediaUploadRequest.getMedia(), courseId);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{mediaId}")
    public ResponseEntity<MediaResponseDTO> getMediaById(@PathVariable Long mediaId) {
        MediaResponseDTO response = mediaService.getMediaById(mediaId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{mediaId}")
    public ResponseEntity<Void> deleteMedia(@PathVariable Long mediaId) {
        mediaService.deleteMedia(mediaId); // Call the service method to delete media
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Return 204 status for successful deletion
    }

    // Endpoint for updating media details
    @PutMapping("/{mediaId}")
    public ResponseEntity<MediaResponseDTO> updateMedia(
            @PathVariable Long mediaId,
            @RequestBody MediaRequestDTO request
    ) {
        MediaResponseDTO updatedMedia = mediaService.updateMedia(mediaId, request);
        return ResponseEntity.ok(updatedMedia);
    }
}

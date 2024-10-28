package com.sarfaraz.elearning.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.sarfaraz.elearning.model.Media;
import com.sarfaraz.elearning.repository.MediaRepository;
import com.sarfaraz.elearning.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class MediaServiceImpl implements MediaService {

    private final Cloudinary cloudinary;
    private final MediaRepository mediaRepository;

    @Autowired
    public MediaServiceImpl(Cloudinary cloudinary, MediaRepository mediaRepository) {
        this.cloudinary = cloudinary;
        this.mediaRepository = mediaRepository;
    }

    @Override
    public Media uploadVideo(MultipartFile videoFile) {
        try {
            // Upload the video to Cloudinary
            Map uploadResult = cloudinary.uploader().upload(videoFile.getBytes(),
                    ObjectUtils.asMap("resource_type", "video"));

            // Extract the URL and other details from the result
            String videoUrl = uploadResult.get("secure_url").toString();
            String videoType = uploadResult.get("format").toString();
            String duration = uploadResult.get("duration").toString();

            // Create a new Media entity and save it to the database
            Media media = new Media();
            media.setUrl(videoUrl);
            media.setType(videoType);
            media.setDuration(duration);

            return mediaRepository.save(media);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload video", e);
        }
    }
}

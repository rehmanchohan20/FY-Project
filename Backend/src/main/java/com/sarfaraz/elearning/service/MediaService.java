package com.sarfaraz.elearning.service;

import org.springframework.web.multipart.MultipartFile;
import com.sarfaraz.elearning.model.Media;

public interface MediaService {
    Media uploadVideo(MultipartFile videoFile);
}

package com.rehman.elearning.rest.dto.inbound;


import org.springframework.web.multipart.MultipartFile;

public class MediaRequestDTO {
    private MultipartFile file;
    private String url;
    private String type;
    private String duration;


    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}



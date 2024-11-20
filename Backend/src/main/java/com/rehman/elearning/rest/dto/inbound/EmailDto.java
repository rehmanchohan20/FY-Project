package com.rehman.elearning.rest.dto.inbound;



public class EmailDto {
    private String to;
    private String subject;
    private String text;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
 // For Sending Attachment Like JPG,PDF,ETC
//import org.springframework.web.multipart.MultipartFile;
//    private MultipartFile attachment;
//    public MultipartFile getAttachment() {
//        return attachment;
//    }
//
//    public void setAttachment(MultipartFile attachment) {
//        this.attachment = attachment;
//    }

package com.ayursetu.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequestDto {
    
    @NotNull(message = "User ID is required")
    private Long userId;
    
    @NotBlank(message = "To email is required")
    @Email(message = "Invalid email format")
    private String to;
    
    @NotBlank(message = "Subject is required")
    private String subject;
    
    @NotBlank(message = "Content is required")
    private String content;
    
    private String templateName;
    
    private Map<String, Object> templateData;
    
    private String from;
    
    private String replyTo;
    
    private String[] cc;
    
    private String[] bcc;
    
    private boolean htmlContent = false;
    
    // Additional methods for EmailServiceImpl
    public String getFrom() {
        return from;
    }
    
    public String getTo() {
        return to;
    }
    
    public String getSubject() {
        return subject;
    }
    
    public String getContent() {
        return content;
    }
    
    public boolean isHtmlContent() {
        return htmlContent;
    }
    
    public String[] getCc() {
        return cc;
    }
    
    public String[] getBcc() {
        return bcc;
    }
    
    public String getReplyTo() {
        return replyTo;
    }
    
    public String getTemplateName() {
        return templateName;
    }
    
    public Map<String, Object> getTemplateData() {
        return templateData;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setTo(String to) {
        this.to = to;
    }
    
    public void setSubject(String subject) {
        this.subject = subject;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }
    
    public void setTemplateData(Map<String, Object> templateData) {
        this.templateData = templateData;
    }
    
    public void setHtmlContent(boolean htmlContent) {
        this.htmlContent = htmlContent;
    }
}



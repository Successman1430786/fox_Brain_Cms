package com.foxbrain.model;

import java.time.LocalDateTime;

public class Enquiry {

    private long id;
    private String name;
    private String email;
    private String phone;
    private String subject;
    private String message;
    private Long courseId;
    private String status;
    private Long assignedToUserId;
    private String responseNotes;
    private LocalDateTime respondedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Enquiry() {}

    public Enquiry(
            long id,
            String name,
            String email,
            String phone,
            String subject,
            String message,
            Long courseId,
            String status,
            Long assignedToUserId,
            String responseNotes,
            LocalDateTime respondedAt,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.subject = subject;
        this.message = message;
        this.courseId = courseId;
        this.status = status;
        this.assignedToUserId = assignedToUserId;
        this.responseNotes = responseNotes;
        this.respondedAt = respondedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Long getAssignedToUserId() { return assignedToUserId; }
    public void setAssignedToUserId(Long assignedToUserId) { this.assignedToUserId = assignedToUserId; }
    public String getResponseNotes() { return responseNotes; }
    public void setResponseNotes(String responseNotes) { this.responseNotes = responseNotes; }
    public LocalDateTime getRespondedAt() { return respondedAt; }
    public void setRespondedAt(LocalDateTime respondedAt) { this.respondedAt = respondedAt; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
package com.foxbrain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Submission {

    private long id;
    private long assignmentId;
    private long studentId;
    private LocalDateTime submittedAt;
    private String submissionText;
    private String attachmentUrl;
    private String status;
    private BigDecimal marks;
    private String teacherFeedback;
    private Long reviewedByTeacherId;
    private LocalDateTime reviewedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Submission() {}

    public Submission(
            long id,
            long assignmentId,
            long studentId,
            LocalDateTime submittedAt,
            String submissionText,
            String attachmentUrl,
            String status,
            BigDecimal marks,
            String teacherFeedback,
            Long reviewedByTeacherId,
            LocalDateTime reviewedAt,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.assignmentId = assignmentId;
        this.studentId = studentId;
        this.submittedAt = submittedAt;
        this.submissionText = submissionText;
        this.attachmentUrl = attachmentUrl;
        this.status = status;
        this.marks = marks;
        this.teacherFeedback = teacherFeedback;
        this.reviewedByTeacherId = reviewedByTeacherId;
        this.reviewedAt = reviewedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public long getAssignmentId() { return assignmentId; }
    public void setAssignmentId(long assignmentId) { this.assignmentId = assignmentId; }
    public long getStudentId() { return studentId; }
    public void setStudentId(long studentId) { this.studentId = studentId; }
    public LocalDateTime getSubmittedAt() { return submittedAt; }
    public void setSubmittedAt(LocalDateTime submittedAt) { this.submittedAt = submittedAt; }
    public String getSubmissionText() { return submissionText; }
    public void setSubmissionText(String submissionText) { this.submissionText = submissionText; }
    public String getAttachmentUrl() { return attachmentUrl; }
    public void setAttachmentUrl(String attachmentUrl) { this.attachmentUrl = attachmentUrl; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public BigDecimal getMarks() { return marks; }
    public void setMarks(BigDecimal marks) { this.marks = marks; }
    public String getTeacherFeedback() { return teacherFeedback; }
    public void setTeacherFeedback(String teacherFeedback) { this.teacherFeedback = teacherFeedback; }
    public Long getReviewedByTeacherId() { return reviewedByTeacherId; }
    public void setReviewedByTeacherId(Long reviewedByTeacherId) { this.reviewedByTeacherId = reviewedByTeacherId; }
    public LocalDateTime getReviewedAt() { return reviewedAt; }
    public void setReviewedAt(LocalDateTime reviewedAt) { this.reviewedAt = reviewedAt; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
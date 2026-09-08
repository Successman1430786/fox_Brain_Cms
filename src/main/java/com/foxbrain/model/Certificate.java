package com.foxbrain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Certificate {

    private long id;
    private long studentId;
    private long courseId;
    private Long enrollmentId;
    private String certificateNumber;
    private LocalDate issueDate;
    private String certificateType;
    private String grade;
    private String verificationCode;
    private String certificateUrl;
    private String status;
    private String remarks;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Certificate() {}

    public Certificate(
            long id,
            long studentId,
            long courseId,
            Long enrollmentId,
            String certificateNumber,
            LocalDate issueDate,
            String certificateType,
            String grade,
            String verificationCode,
            String certificateUrl,
            String status,
            String remarks,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.enrollmentId = enrollmentId;
        this.certificateNumber = certificateNumber;
        this.issueDate = issueDate;
        this.certificateType = certificateType;
        this.grade = grade;
        this.verificationCode = verificationCode;
        this.certificateUrl = certificateUrl;
        this.status = status;
        this.remarks = remarks;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public long getStudentId() { return studentId; }
    public void setStudentId(long studentId) { this.studentId = studentId; }
    public long getCourseId() { return courseId; }
    public void setCourseId(long courseId) { this.courseId = courseId; }
    public Long getEnrollmentId() { return enrollmentId; }
    public void setEnrollmentId(Long enrollmentId) { this.enrollmentId = enrollmentId; }
    public String getCertificateNumber() { return certificateNumber; }
    public void setCertificateNumber(String certificateNumber) { this.certificateNumber = certificateNumber; }
    public LocalDate getIssueDate() { return issueDate; }
    public void setIssueDate(LocalDate issueDate) { this.issueDate = issueDate; }
    public String getCertificateType() { return certificateType; }
    public void setCertificateType(String certificateType) { this.certificateType = certificateType; }
    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
    public String getVerificationCode() { return verificationCode; }
    public void setVerificationCode(String verificationCode) { this.verificationCode = verificationCode; }
    public String getCertificateUrl() { return certificateUrl; }
    public void setCertificateUrl(String certificateUrl) { this.certificateUrl = certificateUrl; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
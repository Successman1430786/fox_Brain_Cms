package com.foxbrain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ExamResult {

    private long id;
    private long examId;
    private long studentId;
    private BigDecimal marksObtained;
    private String grade;
    private String resultStatus;
    private String remarks;
    private LocalDateTime publishedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ExamResult() {}

    public ExamResult(
            long id,
            long examId,
            long studentId,
            BigDecimal marksObtained,
            String grade,
            String resultStatus,
            String remarks,
            LocalDateTime publishedAt,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.examId = examId;
        this.studentId = studentId;
        this.marksObtained = marksObtained;
        this.grade = grade;
        this.resultStatus = resultStatus;
        this.remarks = remarks;
        this.publishedAt = publishedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public long getExamId() { return examId; }
    public void setExamId(long examId) { this.examId = examId; }
    public long getStudentId() { return studentId; }
    public void setStudentId(long studentId) { this.studentId = studentId; }
    public BigDecimal getMarksObtained() { return marksObtained; }
    public void setMarksObtained(BigDecimal marksObtained) { this.marksObtained = marksObtained; }
    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
    public String getResultStatus() { return resultStatus; }
    public void setResultStatus(String resultStatus) { this.resultStatus = resultStatus; }
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
    public LocalDateTime getPublishedAt() { return publishedAt; }
    public void setPublishedAt(LocalDateTime publishedAt) { this.publishedAt = publishedAt; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
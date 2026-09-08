package com.foxbrain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Enrollment {

    private long id;
    private long studentId;
    private long batchId;
    private String enrollmentNumber;
    private LocalDate enrollmentDate;
    private String status;
    private LocalDate completionDate;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Enrollment() {}

    public Enrollment(
            long id,
            long studentId,
            long batchId,
            String enrollmentNumber,
            LocalDate enrollmentDate,
            String status,
            LocalDate completionDate,
            String notes,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.studentId = studentId;
        this.batchId = batchId;
        this.enrollmentNumber = enrollmentNumber;
        this.enrollmentDate = enrollmentDate;
        this.status = status;
        this.completionDate = completionDate;
        this.notes = notes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public long getStudentId() { return studentId; }
    public void setStudentId(long studentId) { this.studentId = studentId; }
    public long getBatchId() { return batchId; }
    public void setBatchId(long batchId) { this.batchId = batchId; }
    public String getEnrollmentNumber() { return enrollmentNumber; }
    public void setEnrollmentNumber(String enrollmentNumber) { this.enrollmentNumber = enrollmentNumber; }
    public LocalDate getEnrollmentDate() { return enrollmentDate; }
    public void setEnrollmentDate(LocalDate enrollmentDate) { this.enrollmentDate = enrollmentDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDate getCompletionDate() { return completionDate; }
    public void setCompletionDate(LocalDate completionDate) { this.completionDate = completionDate; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
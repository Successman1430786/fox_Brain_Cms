package com.foxbrain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Assignment {

    private long id;
    private long batchId;
    private long teacherId;
    private String title;
    private String description;
    private LocalDate assignedDate;
    private LocalDate dueDate;
    private BigDecimal maxMarks;
    private String attachmentUrl;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Assignment() {}

    public Assignment(
            long id,
            long batchId,
            long teacherId,
            String title,
            String description,
            LocalDate assignedDate,
            LocalDate dueDate,
            BigDecimal maxMarks,
            String attachmentUrl,
            String status,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.batchId = batchId;
        this.teacherId = teacherId;
        this.title = title;
        this.description = description;
        this.assignedDate = assignedDate;
        this.dueDate = dueDate;
        this.maxMarks = maxMarks;
        this.attachmentUrl = attachmentUrl;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public long getBatchId() { return batchId; }
    public void setBatchId(long batchId) { this.batchId = batchId; }
    public long getTeacherId() { return teacherId; }
    public void setTeacherId(long teacherId) { this.teacherId = teacherId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDate getAssignedDate() { return assignedDate; }
    public void setAssignedDate(LocalDate assignedDate) { this.assignedDate = assignedDate; }
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public BigDecimal getMaxMarks() { return maxMarks; }
    public void setMaxMarks(BigDecimal maxMarks) { this.maxMarks = maxMarks; }
    public String getAttachmentUrl() { return attachmentUrl; }
    public void setAttachmentUrl(String attachmentUrl) { this.attachmentUrl = attachmentUrl; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
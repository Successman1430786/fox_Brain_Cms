package com.foxbrain.model;

import java.time.LocalDateTime;

public class BatchTeacher {

    private long id;
    private long batchId;
    private long teacherId;
    private boolean isPrimaryTeacher;
    private LocalDateTime assignedAt;

    public BatchTeacher() {}

    public BatchTeacher(
            long id,
            long batchId,
            long teacherId,
            boolean isPrimaryTeacher,
            LocalDateTime assignedAt) {
        this.id = id;
        this.batchId = batchId;
        this.teacherId = teacherId;
        this.isPrimaryTeacher = isPrimaryTeacher;
        this.assignedAt = assignedAt;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public long getBatchId() { return batchId; }
    public void setBatchId(long batchId) { this.batchId = batchId; }
    public long getTeacherId() { return teacherId; }
    public void setTeacherId(long teacherId) { this.teacherId = teacherId; }
    public boolean getIsPrimaryTeacher() { return isPrimaryTeacher; }
    public void setIsPrimaryTeacher(boolean isPrimaryTeacher) { this.isPrimaryTeacher = isPrimaryTeacher; }
    public LocalDateTime getAssignedAt() { return assignedAt; }
    public void setAssignedAt(LocalDateTime assignedAt) { this.assignedAt = assignedAt; }
}
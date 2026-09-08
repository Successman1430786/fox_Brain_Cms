package com.foxbrain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Exam {

    private long id;
    private long batchId;
    private String title;
    private String examType;
    private LocalDate examDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private BigDecimal totalMarks;
    private BigDecimal passingMarks;
    private String roomName;
    private String instructions;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Exam() {}

    public Exam(
            long id,
            long batchId,
            String title,
            String examType,
            LocalDate examDate,
            LocalTime startTime,
            LocalTime endTime,
            BigDecimal totalMarks,
            BigDecimal passingMarks,
            String roomName,
            String instructions,
            String status,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.batchId = batchId;
        this.title = title;
        this.examType = examType;
        this.examDate = examDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalMarks = totalMarks;
        this.passingMarks = passingMarks;
        this.roomName = roomName;
        this.instructions = instructions;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public long getBatchId() { return batchId; }
    public void setBatchId(long batchId) { this.batchId = batchId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getExamType() { return examType; }
    public void setExamType(String examType) { this.examType = examType; }
    public LocalDate getExamDate() { return examDate; }
    public void setExamDate(LocalDate examDate) { this.examDate = examDate; }
    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
    public BigDecimal getTotalMarks() { return totalMarks; }
    public void setTotalMarks(BigDecimal totalMarks) { this.totalMarks = totalMarks; }
    public BigDecimal getPassingMarks() { return passingMarks; }
    public void setPassingMarks(BigDecimal passingMarks) { this.passingMarks = passingMarks; }
    public String getRoomName() { return roomName; }
    public void setRoomName(String roomName) { this.roomName = roomName; }
    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
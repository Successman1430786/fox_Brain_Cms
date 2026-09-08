package com.foxbrain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Attendance {

    private long id;
    private long enrollmentId;
    private LocalDate attendanceDate;
    private String status;
    private LocalTime checkInTime;
    private LocalTime checkOutTime;
    private String remarks;
    private Long markedByTeacherId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Attendance() {}

    public Attendance(
            long id,
            long enrollmentId,
            LocalDate attendanceDate,
            String status,
            LocalTime checkInTime,
            LocalTime checkOutTime,
            String remarks,
            Long markedByTeacherId,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.enrollmentId = enrollmentId;
        this.attendanceDate = attendanceDate;
        this.status = status;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.remarks = remarks;
        this.markedByTeacherId = markedByTeacherId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public long getEnrollmentId() { return enrollmentId; }
    public void setEnrollmentId(long enrollmentId) { this.enrollmentId = enrollmentId; }
    public LocalDate getAttendanceDate() { return attendanceDate; }
    public void setAttendanceDate(LocalDate attendanceDate) { this.attendanceDate = attendanceDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalTime getCheckInTime() { return checkInTime; }
    public void setCheckInTime(LocalTime checkInTime) { this.checkInTime = checkInTime; }
    public LocalTime getCheckOutTime() { return checkOutTime; }
    public void setCheckOutTime(LocalTime checkOutTime) { this.checkOutTime = checkOutTime; }
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
    public Long getMarkedByTeacherId() { return markedByTeacherId; }
    public void setMarkedByTeacherId(Long markedByTeacherId) { this.markedByTeacherId = markedByTeacherId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
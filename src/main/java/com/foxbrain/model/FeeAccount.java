package com.foxbrain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class FeeAccount {

    private long id;
    private long studentId;
    private Long enrollmentId;
    private String feeType;
    private String description;
    private BigDecimal totalAmount;
    private BigDecimal discountAmount;
    private LocalDate dueDate;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public FeeAccount() {}

    public FeeAccount(
            long id,
            long studentId,
            Long enrollmentId,
            String feeType,
            String description,
            BigDecimal totalAmount,
            BigDecimal discountAmount,
            LocalDate dueDate,
            String status,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.studentId = studentId;
        this.enrollmentId = enrollmentId;
        this.feeType = feeType;
        this.description = description;
        this.totalAmount = totalAmount;
        this.discountAmount = discountAmount;
        this.dueDate = dueDate;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public long getStudentId() { return studentId; }
    public void setStudentId(long studentId) { this.studentId = studentId; }
    public Long getEnrollmentId() { return enrollmentId; }
    public void setEnrollmentId(Long enrollmentId) { this.enrollmentId = enrollmentId; }
    public String getFeeType() { return feeType; }
    public void setFeeType(String feeType) { this.feeType = feeType; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public BigDecimal getDiscountAmount() { return discountAmount; }
    public void setDiscountAmount(BigDecimal discountAmount) { this.discountAmount = discountAmount; }
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
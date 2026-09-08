package com.foxbrain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Payment {

    private long id;
    private long feeAccountId;
    private long studentId;
    private String receiptNumber;
    private BigDecimal amount;
    private LocalDateTime paymentDate;
    private String paymentMethod;
    private String transactionReference;
    private String status;
    private String notes;
    private LocalDateTime createdAt;

    public Payment() {}

    public Payment(
            long id,
            long feeAccountId,
            long studentId,
            String receiptNumber,
            BigDecimal amount,
            LocalDateTime paymentDate,
            String paymentMethod,
            String transactionReference,
            String status,
            String notes,
            LocalDateTime createdAt) {
        this.id = id;
        this.feeAccountId = feeAccountId;
        this.studentId = studentId;
        this.receiptNumber = receiptNumber;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.transactionReference = transactionReference;
        this.status = status;
        this.notes = notes;
        this.createdAt = createdAt;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public long getFeeAccountId() { return feeAccountId; }
    public void setFeeAccountId(long feeAccountId) { this.feeAccountId = feeAccountId; }
    public long getStudentId() { return studentId; }
    public void setStudentId(long studentId) { this.studentId = studentId; }
    public String getReceiptNumber() { return receiptNumber; }
    public void setReceiptNumber(String receiptNumber) { this.receiptNumber = receiptNumber; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public LocalDateTime getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDateTime paymentDate) { this.paymentDate = paymentDate; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public String getTransactionReference() { return transactionReference; }
    public void setTransactionReference(String transactionReference) { this.transactionReference = transactionReference; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
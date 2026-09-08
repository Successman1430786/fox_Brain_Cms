package com.foxbrain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Course {

    private long id;
    private Long categoryId;
    private String name;
    private String slug;
    private String shortDescription;
    private String description;
    private Integer durationValue;
    private String durationUnit;
    private BigDecimal fee;
    private String currencyCode;
    private Integer maxStudents;
    private String syllabus;
    private String imageUrl;
    private String status;
    private boolean isFeatured;
    private int displayOrder;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Course() {}

    public Course(
            long id,
            Long categoryId,
            String name,
            String slug,
            String shortDescription,
            String description,
            Integer durationValue,
            String durationUnit,
            BigDecimal fee,
            String currencyCode,
            Integer maxStudents,
            String syllabus,
            String imageUrl,
            String status,
            boolean isFeatured,
            int displayOrder,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.slug = slug;
        this.shortDescription = shortDescription;
        this.description = description;
        this.durationValue = durationValue;
        this.durationUnit = durationUnit;
        this.fee = fee;
        this.currencyCode = currencyCode;
        this.maxStudents = maxStudents;
        this.syllabus = syllabus;
        this.imageUrl = imageUrl;
        this.status = status;
        this.isFeatured = isFeatured;
        this.displayOrder = displayOrder;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }
    public String getShortDescription() { return shortDescription; }
    public void setShortDescription(String shortDescription) { this.shortDescription = shortDescription; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Integer getDurationValue() { return durationValue; }
    public void setDurationValue(Integer durationValue) { this.durationValue = durationValue; }
    public String getDurationUnit() { return durationUnit; }
    public void setDurationUnit(String durationUnit) { this.durationUnit = durationUnit; }
    public BigDecimal getFee() { return fee; }
    public void setFee(BigDecimal fee) { this.fee = fee; }
    public String getCurrencyCode() { return currencyCode; }
    public void setCurrencyCode(String currencyCode) { this.currencyCode = currencyCode; }
    public Integer getMaxStudents() { return maxStudents; }
    public void setMaxStudents(Integer maxStudents) { this.maxStudents = maxStudents; }
    public String getSyllabus() { return syllabus; }
    public void setSyllabus(String syllabus) { this.syllabus = syllabus; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public boolean getIsFeatured() { return isFeatured; }
    public void setIsFeatured(boolean isFeatured) { this.isFeatured = isFeatured; }
    public int getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(int displayOrder) { this.displayOrder = displayOrder; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
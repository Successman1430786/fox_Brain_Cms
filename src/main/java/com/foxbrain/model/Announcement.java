package com.foxbrain.model;

import java.time.LocalDateTime;

public class Announcement {

    private long id;
    private String title;
    private String content;
    private String targetRole;
    private LocalDateTime publishAt;
    private LocalDateTime expireAt;
    private String status;
    private long createdByUserId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Announcement() {}

    public Announcement(
            long id,
            String title,
            String content,
            String targetRole,
            LocalDateTime publishAt,
            LocalDateTime expireAt,
            String status,
            long createdByUserId,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.targetRole = targetRole;
        this.publishAt = publishAt;
        this.expireAt = expireAt;
        this.status = status;
        this.createdByUserId = createdByUserId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getTargetRole() { return targetRole; }
    public void setTargetRole(String targetRole) { this.targetRole = targetRole; }
    public LocalDateTime getPublishAt() { return publishAt; }
    public void setPublishAt(LocalDateTime publishAt) { this.publishAt = publishAt; }
    public LocalDateTime getExpireAt() { return expireAt; }
    public void setExpireAt(LocalDateTime expireAt) { this.expireAt = expireAt; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public long getCreatedByUserId() { return createdByUserId; }
    public void setCreatedByUserId(long createdByUserId) { this.createdByUserId = createdByUserId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
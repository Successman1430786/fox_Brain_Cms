package com.foxbrain.model;

import java.time.LocalDateTime;

public class Notification {

    private long id;
    private long userId;
    private Long announcementId;
    private String title;
    private String message;
    private String notificationType;
    private boolean isRead;
    private LocalDateTime readAt;
    private LocalDateTime createdAt;

    public Notification() {}

    public Notification(
            long id,
            long userId,
            Long announcementId,
            String title,
            String message,
            String notificationType,
            boolean isRead,
            LocalDateTime readAt,
            LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.announcementId = announcementId;
        this.title = title;
        this.message = message;
        this.notificationType = notificationType;
        this.isRead = isRead;
        this.readAt = readAt;
        this.createdAt = createdAt;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public long getUserId() { return userId; }
    public void setUserId(long userId) { this.userId = userId; }
    public Long getAnnouncementId() { return announcementId; }
    public void setAnnouncementId(Long announcementId) { this.announcementId = announcementId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getNotificationType() { return notificationType; }
    public void setNotificationType(String notificationType) { this.notificationType = notificationType; }
    public boolean getIsRead() { return isRead; }
    public void setIsRead(boolean isRead) { this.isRead = isRead; }
    public LocalDateTime getReadAt() { return readAt; }
    public void setReadAt(LocalDateTime readAt) { this.readAt = readAt; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
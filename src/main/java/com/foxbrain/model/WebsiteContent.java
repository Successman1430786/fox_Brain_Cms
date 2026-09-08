package com.foxbrain.model;

import java.time.LocalDateTime;

public class WebsiteContent {

    private long id;
    private String contentKey;
    private String title;
    private String content;
    private String imageUrl;
    private String status;
    private int displayOrder;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public WebsiteContent() {}

    public WebsiteContent(
            long id,
            String contentKey,
            String title,
            String content,
            String imageUrl,
            String status,
            int displayOrder,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.contentKey = contentKey;
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.status = status;
        this.displayOrder = displayOrder;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getContentKey() { return contentKey; }
    public void setContentKey(String contentKey) { this.contentKey = contentKey; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public int getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(int displayOrder) { this.displayOrder = displayOrder; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
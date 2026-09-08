package com.foxbrain.model;

import java.time.LocalDateTime;

public class ParentStudent {

    private long id;
    private long parentId;
    private long studentId;
    private String relationshipType;
    private boolean isPrimaryContact;
    private LocalDateTime createdAt;

    public ParentStudent() {}

    public ParentStudent(
            long id,
            long parentId,
            long studentId,
            String relationshipType,
            boolean isPrimaryContact,
            LocalDateTime createdAt) {
        this.id = id;
        this.parentId = parentId;
        this.studentId = studentId;
        this.relationshipType = relationshipType;
        this.isPrimaryContact = isPrimaryContact;
        this.createdAt = createdAt;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public long getParentId() { return parentId; }
    public void setParentId(long parentId) { this.parentId = parentId; }
    public long getStudentId() { return studentId; }
    public void setStudentId(long studentId) { this.studentId = studentId; }
    public String getRelationshipType() { return relationshipType; }
    public void setRelationshipType(String relationshipType) { this.relationshipType = relationshipType; }
    public boolean getIsPrimaryContact() { return isPrimaryContact; }
    public void setIsPrimaryContact(boolean isPrimaryContact) { this.isPrimaryContact = isPrimaryContact; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
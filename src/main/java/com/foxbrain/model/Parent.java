package com.foxbrain.model;

import java.time.LocalDateTime;

public class Parent {

    private long id;
    private long userId;
    private String occupation;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Parent() {}

    public Parent(
            long id,
            long userId,
            String occupation,
            String addressLine1,
            String addressLine2,
            String city,
            String state,
            String postalCode,
            String country,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.occupation = occupation;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public long getUserId() { return userId; }
    public void setUserId(long userId) { this.userId = userId; }
    public String getOccupation() { return occupation; }
    public void setOccupation(String occupation) { this.occupation = occupation; }
    public String getAddressLine1() { return addressLine1; }
    public void setAddressLine1(String addressLine1) { this.addressLine1 = addressLine1; }
    public String getAddressLine2() { return addressLine2; }
    public void setAddressLine2(String addressLine2) { this.addressLine2 = addressLine2; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
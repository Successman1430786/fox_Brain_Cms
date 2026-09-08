package com.foxbrain.model;

import java.time.LocalDateTime;

public class InstituteSetting {

    private long id;
    private String instituteName;
    private String tagline;
    private String aboutText;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String phone;
    private String alternatePhone;
    private String email;
    private String website;
    private String logoUrl;
    private String faviconUrl;
    private String facebookUrl;
    private String instagramUrl;
    private String youtubeUrl;
    private String linkedinUrl;
    private String officeHours;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public InstituteSetting() {}

    public InstituteSetting(
            long id,
            String instituteName,
            String tagline,
            String aboutText,
            String addressLine1,
            String addressLine2,
            String city,
            String state,
            String postalCode,
            String country,
            String phone,
            String alternatePhone,
            String email,
            String website,
            String logoUrl,
            String faviconUrl,
            String facebookUrl,
            String instagramUrl,
            String youtubeUrl,
            String linkedinUrl,
            String officeHours,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.instituteName = instituteName;
        this.tagline = tagline;
        this.aboutText = aboutText;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
        this.phone = phone;
        this.alternatePhone = alternatePhone;
        this.email = email;
        this.website = website;
        this.logoUrl = logoUrl;
        this.faviconUrl = faviconUrl;
        this.facebookUrl = facebookUrl;
        this.instagramUrl = instagramUrl;
        this.youtubeUrl = youtubeUrl;
        this.linkedinUrl = linkedinUrl;
        this.officeHours = officeHours;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getInstituteName() { return instituteName; }
    public void setInstituteName(String instituteName) { this.instituteName = instituteName; }
    public String getTagline() { return tagline; }
    public void setTagline(String tagline) { this.tagline = tagline; }
    public String getAboutText() { return aboutText; }
    public void setAboutText(String aboutText) { this.aboutText = aboutText; }
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
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAlternatePhone() { return alternatePhone; }
    public void setAlternatePhone(String alternatePhone) { this.alternatePhone = alternatePhone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }
    public String getLogoUrl() { return logoUrl; }
    public void setLogoUrl(String logoUrl) { this.logoUrl = logoUrl; }
    public String getFaviconUrl() { return faviconUrl; }
    public void setFaviconUrl(String faviconUrl) { this.faviconUrl = faviconUrl; }
    public String getFacebookUrl() { return facebookUrl; }
    public void setFacebookUrl(String facebookUrl) { this.facebookUrl = facebookUrl; }
    public String getInstagramUrl() { return instagramUrl; }
    public void setInstagramUrl(String instagramUrl) { this.instagramUrl = instagramUrl; }
    public String getYoutubeUrl() { return youtubeUrl; }
    public void setYoutubeUrl(String youtubeUrl) { this.youtubeUrl = youtubeUrl; }
    public String getLinkedinUrl() { return linkedinUrl; }
    public void setLinkedinUrl(String linkedinUrl) { this.linkedinUrl = linkedinUrl; }
    public String getOfficeHours() { return officeHours; }
    public void setOfficeHours(String officeHours) { this.officeHours = officeHours; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
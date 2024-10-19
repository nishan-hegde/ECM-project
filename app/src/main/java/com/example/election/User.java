package com.example.election;
public class User {

    private String firstName;
    private String section;
    private String imageUrl; // For the user's image URL

    // Default constructor for Firebase
    public User() {
    }

    // Getters and setters for each field
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

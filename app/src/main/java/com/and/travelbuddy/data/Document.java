package com.and.travelbuddy.data;


import com.google.firebase.database.Exclude;

public class Document {
    private String key;
    private String title;
    private String image;
    private String category;

    public Document() {
    }

    public Document(String title, String image, String category) {
        this.title = title;
        this.image = image;
        this.category = category;
    }

    @Exclude
    public String getKey() {
        return key;
    }

    @Exclude
    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
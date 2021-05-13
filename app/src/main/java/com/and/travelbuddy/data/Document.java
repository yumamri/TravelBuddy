package com.and.travelbuddy.data;


public class Document {
    private String title;
    private String image;
    private Tag category;

    public Document() {}

    public Document(String title, String image) {
        this.title = title;
        this.image = image;
    }

    public Document(String title, String image, Tag category) {
        this.title = title;
        this.image = image;
        this.category = category;
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

    public void setImage(String bitmap) {
        this.image = bitmap;
    }

    public Tag getCategory() {
        return category;
    }

    public void setCategory(Tag category) {
        this.category = category;
    }
}

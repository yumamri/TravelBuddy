package com.and.travelbuddy.data;

import com.and.travelbuddy.data.Tag;

public class Document {
    private String title;
    private Tag category;

    public Document(String title) {
        this.title = title;
    }

    public Document(String title, Tag category) {
        this.title = title;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Tag getCategory() {
        return category;
    }

    public void setCategory(Tag category) {
        this.category = category;
    }
}

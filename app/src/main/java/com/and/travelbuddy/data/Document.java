package com.and.travelbuddy.data;

import android.graphics.Bitmap;

import com.and.travelbuddy.data.Tag;

public class Document {
    private String title;
    private Bitmap bitmap;
    private Tag category;

    public Document(String title, Bitmap bitmap, Tag category) {
        this.title = title;
        this.bitmap = bitmap;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Tag getCategory() {
        return category;
    }

    public void setCategory(Tag category) {
        this.category = category;
    }
}

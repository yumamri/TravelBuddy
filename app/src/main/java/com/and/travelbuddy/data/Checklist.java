package com.and.travelbuddy.data;

import com.google.firebase.database.Exclude;

public class Checklist {
    String key;
    String item;
    Boolean aBoolean;

    public Checklist() {
    }

    public Checklist(String item, Boolean aBoolean) {
        this.item = item;
        this.aBoolean = aBoolean;
    }

    @Exclude
    public String getKey() {
        return key;
    }

    @Exclude
    public void setKey(String key) {
        this.key = key;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Boolean getaBoolean() {
        return aBoolean;
    }

    public void setaBoolean(Boolean aBoolean) {
        this.aBoolean = aBoolean;
    }
}



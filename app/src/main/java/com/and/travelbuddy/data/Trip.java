package com.and.travelbuddy.data;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.ArrayList;

public class Trip implements Serializable {
    private String key;
    private String country;
    private String date;
    private String image;
    private ArrayList<Checklist> checklist = new ArrayList<>();
    private ArrayList<Document> documents = new ArrayList<>();

    public Trip() {
    }

    public Trip(String country, String date, String image) {
        this.country = country;
        this.date = date;
        this.image = image;
    }

    @Exclude
    public String getKey() {
        return key;
    }

    @Exclude
    public void setKey(String key) {
        this.key = key;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<Checklist> getChecklist() {
        return checklist;
    }

    public void setChecklist(ArrayList<Checklist> checklist) {
        this.checklist = checklist;
    }

    public ArrayList<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(ArrayList<Document> documents) {
        this.documents = documents;
    }
}

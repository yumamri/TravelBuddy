package com.and.travelbuddy.data;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Trip implements Serializable {
    private int index;
    private String key;
    private String image;
    private String country;
    private String date;
    private ArrayList<Document> documentArrayList;
    private ArrayList<String> checklist;

    public Trip() {
    }

    public Trip(String country, String date) {
        this.country = country;
        this.date = date;
    }

    @Exclude
    public int getIndex() {
        return index;
    }

    @Exclude
    public void setIndex(int index) {
        this.index = index;
    }

    @Exclude
    public String getKey() {
        return key;
    }

    @Exclude
    public void setKey(String key) {
        this.key = key;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public ArrayList<Document> getDocumentArrayList() {
        return documentArrayList;
    }

    public void setDocumentArrayList(ArrayList<Document> documentArrayList) {
        this.documentArrayList = documentArrayList;
    }

    public ArrayList<String> getChecklist() {
        return checklist;
    }

    public void setChecklist(ArrayList<String> checklist) {
        this.checklist = checklist;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("country", country);
        result.put("date", date);

        return result;
    }
}

package com.and.travelbuddy.data;

import java.util.ArrayList;

public class Trip {
    private String country;
    private String city;
    private String date;
    private ArrayList<Document> documentArrayList;
    private ArrayList<String> checklist;

    public Trip() {
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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
}

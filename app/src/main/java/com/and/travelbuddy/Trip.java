package com.and.travelbuddy;

import java.util.ArrayList;
import java.util.Date;

public class Trip {
    private String country;
    private String city;
    private Date startDate;
    private Date endDate;
    private ArrayList<Document> documentArrayList;
    private ArrayList<String> checklist;

    public Trip(String country, String city, Date startDate, Date endDate, ArrayList<Document> documentArrayList, ArrayList<String> checklist) {
        this.country = country;
        this.city = city;
        this.startDate = startDate;
        this.endDate = endDate;
        this.documentArrayList = documentArrayList;
        this.checklist = checklist;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

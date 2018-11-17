package com.example.meditrack;

import java.math.BigInteger;
import java.util.Date;
import java.util.Random;

public class Problem implements ElasticsearchStorable{

    private String title;
    private String description;
    private Date date;
    private String patientId;
    private String id;

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Date getDate() { return date; }
    public String getId() { return id; }
    public String getPatientId() { return patientId; }

    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }

    public Problem(String title, String description, String patientId) {
        this.title = title;
        this.description = description;
        this.date = new Date();
        this.id = GenerateUniqueId();
        this.patientId = patientId;
    }

    private String GenerateUniqueId()
    {
        return new BigInteger(128, new Random()).toString();
    }

    @Override
    public String getElasticsearchType() {
        return "problems";
    }
}

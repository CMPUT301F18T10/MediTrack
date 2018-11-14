package com.example.meditrack;

import java.util.ArrayList;
import java.util.Date;

public class Problem implements ElasticsearchStorable{

    private String title;
    private String description;
    private Date date;
    private String patientId;
    private String problemId;

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Date getDate() { return date; }
    public String getId() { return problemId; }
    public String getPatientId() { return patientId; }

    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }

    public Problem(String title, String description, String patientId) {
        this.title = title;
        this.description = description;
        this.date = new Date();
        this.problemId = GenerateUniqueId();
        this.patientId = patientId;
    }

    private String GenerateUniqueId()
    {
        Date date = new Date();
        return date.toString();
    }

    @Override
    public String getElasticsearchType() {
        return "problems";
    }
}

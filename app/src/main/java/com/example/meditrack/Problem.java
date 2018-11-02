package com.example.meditrack;

import java.util.ArrayList;
import java.util.Date;

public class Problem {

    private String title;
    private String description;
    private Date date;
    private ArrayList<AbstractRecord> records;
    private Integer problemId;

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Date getDate() { return date; }
    public Integer getId() { return problemId; }

    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }

    public Problem(String title, String description, Date date) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.records = new ArrayList<AbstractRecord>();
        this.problemId = -1;
    }

    public Problem(String title, String description, Date date, Integer id)
    {
        this.title = title;
        this.description = description;
        this.date = date;
        this.records = new ArrayList<AbstractRecord>();
        this.problemId = id;
    }

    public Problem(String title, String description, Date date, ArrayList<AbstractRecord> records) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.records = records;
        this.problemId = -1;
    }
}

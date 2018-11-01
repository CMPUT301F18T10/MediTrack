package com.example.meditrack;

import java.util.ArrayList;
import java.util.Date;

public class Problem {

    private String title;
    private String description;
    private Date date;
    private ArrayList<AbstractRecord> records;

    public Problem(String title, String description, Date date) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.records = new ArrayList<AbstractRecord>();
    }

    public Problem(String title, String description, Date date, ArrayList<AbstractRecord> records) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.records = records;
    }
}

package com.example.meditrack;

import java.util.Date;

public abstract class AbstractRecord {

    protected Date timestamp;
    protected String problemId;
    protected String id;


    protected Date getTimestamp() {
        return timestamp;
    }
    protected String getProblemId() { return problemId; }
    protected String getId() { return id; }

    public AbstractRecord(String problemId)
    {
        this.problemId = problemId;
        this.id = GenerateUniqueId();
        this.timestamp = new Date();
    }

    private String GenerateUniqueId()
    {
        long mills = new Date().getTime();
        return new Long(mills).toString();
    }
}

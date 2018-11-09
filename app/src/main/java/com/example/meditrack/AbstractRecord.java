package com.example.meditrack;

import java.util.Date;

public abstract class AbstractRecord {

    protected Date timestamp;
    protected String problemId;
    protected String recordId;


    protected Date getTimestamp() {
        return timestamp;
    }
    protected String getProblemId() { return problemId; }
    protected String getId() { return recordId; }

    public AbstractRecord(String problemId)
    {
        this.problemId = problemId;
        this.recordId = GenerateUniqueId();
        this.timestamp = new Date();
    }

    private String GenerateUniqueId()
    {
        Date date = new Date();
        return date.toString();
    }
}

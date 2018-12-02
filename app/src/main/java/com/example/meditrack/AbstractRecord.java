package com.example.meditrack;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.Random;

public abstract class AbstractRecord implements Serializable {

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
        return new BigInteger(128, new Random()).toString();
    }
}

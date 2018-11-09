package com.example.meditrack;

import java.util.Date;

public abstract class AbstractRecord {

    protected Date timestamp;

    protected Integer recordId;

    public Date getTimestamp() {
        return timestamp;
    }

    public AbstractRecord(Date timestamp) {
        this.timestamp = timestamp;
    }
}

package com.example.meditrack;

import java.util.Date;

public abstract class AbstractRecord {

    Date timestamp;

    public Date getTimestamp() {
        return timestamp;
    }

    public AbstractRecord(Date timestamp) {
        this.timestamp = timestamp;
    }
}

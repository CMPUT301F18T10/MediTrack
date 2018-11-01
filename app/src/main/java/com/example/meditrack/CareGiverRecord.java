package com.example.meditrack;

import com.example.meditrack.AbstractRecord;

import java.util.Date;

public class CareGiverRecord extends AbstractRecord {

    private String careGiverComment;
    private String careGiverID;

    public CareGiverRecord(Date timestamp, String careGiverComment, String careGiverID) {
        super(timestamp);
        this.careGiverComment = careGiverComment;
        this.careGiverID = careGiverID;
    }

    public String getCareGiverComment() {
        return careGiverComment;
    }

    public String getCareGiverID() {
        return careGiverID;
    }
}

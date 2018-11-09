package com.example.meditrack;

import android.graphics.Bitmap;
import android.location.Location;

import java.util.ArrayList;
import java.util.Date;

public class PatientRecord extends AbstractRecord {

    private String title;
    private String description;
    private ArrayList<String> photoIds;
    private BodyLocation bodyLocation;
    private Location location;

    public PatientRecord(String problemId, String title, String description, ArrayList<String> photoIds, BodyLocation bodyLocation, Location location) {
        super(problemId);
        this.title = title;
        this.description = description;
        this.photoIds = photoIds;
        this.bodyLocation = bodyLocation;
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<String> getPhotoIds() {
        return photoIds;
    }

    public BodyLocation getBodyLocation() {
        return bodyLocation;
    }

    public Location getLocation() {
        return location;
    }
}

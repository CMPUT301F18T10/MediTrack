package com.example.meditrack;

import android.graphics.Bitmap;
import android.location.Location;

import java.util.ArrayList;
import java.util.Date;

public class PatientRecord extends AbstractRecord {

    private String title;
    private String description;
    private ArrayList<Bitmap> photos;
    private BodyLocation bodyLocation;
    private Location location;

    public PatientRecord(Date timestamp, String title, String description, ArrayList<Bitmap> photos, BodyLocation bodyLocation, Location location) {
        super(timestamp);
        this.title = title;
        this.description = description;
        this.photos = photos;
        this.bodyLocation = bodyLocation;
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Bitmap> getPhotos() {
        return photos;
    }

    public BodyLocation getBodyLocation() {
        return bodyLocation;
    }

    public Location getLocation() {
        return location;
    }
}

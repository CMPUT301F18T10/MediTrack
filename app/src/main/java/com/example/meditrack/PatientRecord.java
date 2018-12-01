package com.example.meditrack;

import android.graphics.Bitmap;
import android.location.Location;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class PatientRecord extends AbstractRecord implements ElasticsearchStorable, Serializable {

    private String title;
    private String description;
    private ArrayList<String> photoIds;
    private BodyLocation bodyLocation;
    private Location location;

    public PatientRecord(String problemId)
    {
        // Added for debug and technical reasons
        // PLEASE DON'T USE IN APPLICATION
        super(problemId);
    }

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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBodyLocation(BodyLocation bodyLocation) {
        this.bodyLocation = bodyLocation;
    }

    public void setLocation(Location location) {
        this.location = location;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PatientRecord that = (PatientRecord) o;
        return Objects.equals(getTitle(), that.getTitle()) &&
                Objects.equals(getDescription(), that.getDescription()) &&
                Objects.equals(getPhotoIds(), that.getPhotoIds()) &&
                Objects.equals(getBodyLocation(), that.getBodyLocation()) &&
                Objects.equals(getLocation(), that.getLocation());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getTitle(), getDescription(), getPhotoIds(), getBodyLocation(), getLocation());
    }

    @Override
    public String getElasticsearchType() {
        return "patient_records";
    }

    @Override
    public String getId() {
        return super.getId();
    }
}

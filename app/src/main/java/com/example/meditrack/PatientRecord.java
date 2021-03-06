package com.example.meditrack;

import android.location.Location;

import java.io.Serializable;
import java.util.Objects;

public class PatientRecord extends AbstractRecord implements ElasticsearchStorable, Serializable {

    private String title;
    private String description;
    private BodyLocation bodyLocation;
    private Location location;

    public PatientRecord(String problemId)
    {
        // Added for debug and technical reasons
        // PLEASE DON'T USE IN APPLICATION
        super(problemId);
    }

    public PatientRecord(String problemId, String title, String description, BodyLocation bodyLocation, Location location) {
        super(problemId);
        this.title = title;
        this.description = description;
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
                Objects.equals(getBodyLocation(), that.getBodyLocation()) &&
                Objects.equals(getLocation(), that.getLocation());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getTitle(), getDescription(), getBodyLocation(), getLocation());
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

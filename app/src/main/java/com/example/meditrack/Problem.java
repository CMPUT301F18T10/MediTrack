package com.example.meditrack;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigInteger;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

public class Problem implements ElasticsearchStorable{

    private String title;
    private String description;
    private Date date;
    private String patientId;
    private String id;

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Date getDate() { return date; }
    public String getId() { return id; }
    public String getPatientId() { return patientId; }

    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }

    public Problem(String title, String description, String patientId) {
        this.title = title;
        this.description = description;
        this.date = new Date();
        this.id = GenerateUniqueId();
        this.patientId = patientId;
    }

    private String GenerateUniqueId()
    {
        return new BigInteger(128, new Random()).toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Problem problem = (Problem) o;
        return  Objects.equals(getTitle(), problem.getTitle()) &&
                Objects.equals(getDescription(), problem.getDescription()) &&
                Objects.equals(getPatientId(), problem.getPatientId()) &&
                Objects.equals(getId(), problem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getDescription(), getPatientId(), getId());
    }

    @Override
    public String getElasticsearchType() {
        return "problems";
    }
}

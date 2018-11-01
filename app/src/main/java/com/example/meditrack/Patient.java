package com.example.meditrack;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Patient extends AbstractUser {

    private ArrayList<Bitmap> bodyLocationImages;
    private ArrayList<CareProvider> careProviders;
    private String contactInfo;
    private ArrayList<Problem> problems;

    public Patient(String userId, ArrayList<Bitmap> bodyLocationImages, ArrayList<CareProvider> careProviders, String contactInfo, ArrayList<Problem> problems) {
        super(userId);
        this.bodyLocationImages = bodyLocationImages;
        this.careProviders = careProviders;
        this.contactInfo = contactInfo;
        this.problems = problems;
    }

    public ArrayList<Bitmap> getBodyLocationImages() {
        return bodyLocationImages;
    }

    public ArrayList<CareProvider> getCareProviders() {
        return careProviders;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public ArrayList<Problem> getProblems() {
        return problems;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }
}

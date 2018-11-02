package com.example.meditrack;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Patient extends AbstractUser {

    private ArrayList<String> bodyLocationImages;
    private ArrayList<CareProvider> careProviders;
    private ContactInfo contactInfo;
    private ArrayList<Problem> problems;

    public Patient(String userId, ArrayList<String> bodyLocationImages, ArrayList<CareProvider> careProviders, ContactInfo contactInfo, ArrayList<Problem> problems) {
        super(userId);
        this.bodyLocationImages = bodyLocationImages;
        this.careProviders = careProviders;
        this.contactInfo = contactInfo;
        this.problems = problems;
    }

    public ArrayList<String> getBodyLocationImages() {
        return bodyLocationImages;
    }

    public ArrayList<CareProvider> getCareProviders() {
        return careProviders;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public ArrayList<Problem> getProblems() {
        return problems;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }
}

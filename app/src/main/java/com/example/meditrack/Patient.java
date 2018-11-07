package com.example.meditrack;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Patient extends AbstractUser {

    private ArrayList<String> bodyLocationImageIds;
    private ArrayList<String> careProviderIds;
    private ContactInfo contactInfo;
    private ArrayList<Integer> problemIds;

    public Patient(String userId, ArrayList<String> bodyLocationImages, ArrayList<String> careProviderIds, ContactInfo contactInfo, ArrayList<Integer> problemIds) {
        super(userId);
        this.bodyLocationImageIds = bodyLocationImages;
        this.careProviderIds = careProviderIds;
        this.contactInfo = contactInfo;
        this.problemIds = problemIds;
    }

    public ArrayList<String> getBodyLocationImages() {
        return bodyLocationImageIds;
    }

    public ArrayList<String> getCareProviders() {
        return careProviderIds;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public ArrayList<Integer> getProblems() {
        return problemIds;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }
}

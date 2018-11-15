package com.example.meditrack;

import java.util.ArrayList;

public class Patient extends AbstractUser implements ElasticsearchStorable{

    private ArrayList<String> bodyLocationImageIds;
    private ContactInfo contactInfo;

    public Patient(String userId, ArrayList<String> bodyLocationImages, ContactInfo contactInfo) {
        super(userId);
        this.bodyLocationImageIds = bodyLocationImages;
        this.contactInfo = contactInfo;
    }

    public ArrayList<String> getBodyLocationImages() {
        return bodyLocationImageIds;
    }
    public ContactInfo getContactInfo() {
        return contactInfo;
    }
    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }
    public void setBodyLocationImageIds(ArrayList<String> ImageIds){this.bodyLocationImageIds = ImageIds;}

    @Override
    public String getElasticsearchType() {
        return "patients";
    }
}

package com.example.meditrack;

import java.util.ArrayList;

public class Patient extends AbstractUser implements ElasticsearchStorable{

    private ArrayList<String> bodyLocationImageIds;
    private ArrayList<String> careProviderId;
    private ArrayList<String> approvedDeviceID;
    private ContactInfo contactInfo;
    private String shortCode;

    // Added for debug and technical reasons
    // PLEASE DON'T USE IN APPLICATION
    public Patient(String userId) {
        super(userId);
    }

    public Patient(String userId, ArrayList<String> bodyLocationImages,ArrayList<String> careProviderId, ContactInfo contactInfo,ArrayList<String> approvedDeviceID) {
        super(userId);
        this.bodyLocationImageIds = bodyLocationImages;
        this.careProviderId = careProviderId;
        this.contactInfo = contactInfo;
        this.approvedDeviceID = approvedDeviceID;
        this.shortCode = Integer.toString(Math.abs(userId.hashCode() % 10000));
    }

    public String getShortCode() { return shortCode; }
    public ArrayList<String> getBodyLocationImages() {
        return bodyLocationImageIds;
    }
    public ArrayList<String> getCareProviderId(){return this.careProviderId;}
    public ContactInfo getContactInfo() {
        return contactInfo;
    }
    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }
    public ArrayList<String> getApprovedDeviceID() {
        return approvedDeviceID;
    }

    public void setApprovedDeviceID(ArrayList<String> approvedDeviceID) {
        this.approvedDeviceID = approvedDeviceID;
    }

    public void setBodyLocationImageIds(ArrayList<String> ImageIds){this.bodyLocationImageIds = ImageIds;}
    public void setCareProviderId(ArrayList<String> ids){this.careProviderId = ids;}
    @Override
    public String getElasticsearchType() {
        return "patients";
    }

    @Override
    public String getId() {
        return super.getId();
    }
}

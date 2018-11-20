package com.example.meditrack;

import java.util.ArrayList;

public class Patient extends AbstractUser implements ElasticsearchStorable{

    private ArrayList<String> bodyLocationImageIds;
    private ArrayList<String> careProviderId;
    private ContactInfo contactInfo;

    // Added for debug and technical reasons
    // PLEASE DON'T USE IN APPLICATION
    public Patient(String userId) {
        super(userId);
    }

    public Patient(String userId, ArrayList<String> bodyLocationImages,ArrayList<String> careProviderId, ContactInfo contactInfo) {
        super(userId);
        this.bodyLocationImageIds = bodyLocationImages;
        this.careProviderId = careProviderId;
        this.contactInfo = contactInfo;
    }

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
    public void setBodyLocationImageIds(ArrayList<String> ImageIds){this.bodyLocationImageIds = ImageIds;}
    public void setCareProviderId(ArrayList<String> ids){this.careProviderId = ids;}
    public void addCareProviderId(String id){
        ArrayList<String> cpIds = getCareProviderId();
        cpIds.add(id);
        setCareProviderId(cpIds);
    }
    public void deleteId(String id){
        ArrayList<String> cpIds = getCareProviderId();
        cpIds.remove(id);
        setCareProviderId(cpIds);
    }

    @Override
    public String getElasticsearchType() {
        return "patients";
    }

    @Override
    public String getId() {
        return super.getId();
    }
}
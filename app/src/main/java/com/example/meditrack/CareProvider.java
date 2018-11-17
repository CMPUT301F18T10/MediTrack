package com.example.meditrack;

import java.util.ArrayList;

public class CareProvider extends AbstractUser implements ElasticsearchStorable{

    private ArrayList<String> patientIds;

    public CareProvider(String userId, ArrayList<String> patients) {
        super(userId);
        this.patientIds = patients;
    }

    public CareProvider(String userId) {
        super(userId);
        this.patientIds = new ArrayList<String>();
    }

    public void AddPatientId(String patientId){
        this.patientIds.add(patientId);
    }

    public ArrayList<String> getPatientIds() {
        return patientIds;
    }

    public void setPatientIds(ArrayList<String> listOfIds){this.patientIds = listOfIds;}

    @Override
    public String getElasticsearchType(){
        return "care_providers";
    }

    @Override
    public String getId() {
        return super.getId();
    }
}

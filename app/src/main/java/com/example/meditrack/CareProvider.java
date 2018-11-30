package com.example.meditrack;

import java.util.ArrayList;
import java.util.Objects;

public class CareProvider extends AbstractUser implements ElasticsearchStorable{

    private ArrayList<String> patientIds;
    private ArrayList<String> approvedDeviceIDs;
    private String shortCode;

    public CareProvider(String userId, ArrayList<String> patients,ArrayList<String> approvedDeviceIDs) {
        super(userId);
        this.patientIds = patients;
        this.approvedDeviceIDs = approvedDeviceIDs;
        this.shortCode = Integer.toString(Math.abs(userId.hashCode() % 10000));
    }

    public CareProvider(String userId) {
        super(userId);
        this.patientIds = new ArrayList<String>();

    }
    public void setApprovedDeviceIDs(ArrayList<String> approvedDeviceIDs){
        this.approvedDeviceIDs = approvedDeviceIDs;
    }


    public String getShortCode() {
        return shortCode;
    }

    public void AddPatientId(String patientId)
    {
        if (!patientIds.contains(patientId)) { this.patientIds.add(patientId); }
    }

    public ArrayList<String> getPatientIds() {
        return patientIds;
    }

    public ArrayList<String> getApprovedDeviceIDs() {
        return approvedDeviceIDs;
    }

    public void setPatientIds(ArrayList<String> listOfIds){this.patientIds = listOfIds;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CareProvider that = (CareProvider) o;
        return this.patientIds.equals(that.getPatientIds()) && super.id.equals(that.getId());
    }

    @Override
    public String getElasticsearchType(){
        return "care_providers";
    }

    @Override
    public String getId() {
        return super.getId();
    }
}

package com.example.meditrack;

import java.util.ArrayList;

public class CareProvider extends AbstractUser {

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
        patientIds.add(patientId);
    }

    public ArrayList<String> getPatientIds() {
        return patientIds;
    }
}

package com.example.meditrack;

import java.util.ArrayList;

public class CareProvider extends AbstractUser {

    private ArrayList<Patient> patients;

    public CareProvider(String userId, ArrayList<Patient> patients) {
        super(userId);
        this.patients = patients;
    }

    public CareProvider(String userId) {
        super(userId);
        this.patients = new ArrayList<Patient>();
    }

    public ArrayList<Patient> getPatients() {
        return patients;
    }
}

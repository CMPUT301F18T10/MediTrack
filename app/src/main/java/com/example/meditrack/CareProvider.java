package com.example.meditrack;

import java.util.ArrayList;

public class CareProvider extends AbstractUser {

    private ArrayList<Patient> mPatients;

    public CareProvider(String user_id){
        super.mUserId = user_id;
    }

    public CareProvider(String user_id, ArrayList<Patient> patients){
        this.mPatients = patients;
    }

}

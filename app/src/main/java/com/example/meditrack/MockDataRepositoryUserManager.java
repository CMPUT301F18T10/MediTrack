package com.example.meditrack;

import java.util.ArrayList;
import java.util.Arrays;

public class MockDataRepositoryUserManager extends DataRepositorySingleton {
    private Patient patient;
    private static CareProvider careProvider;
    private ArrayList<String> bodyImageId = new ArrayList<String>(Arrays.asList("Img1223","Img1224","Img1225"));
    private static ArrayList<String> patientId = new ArrayList<String>(Arrays.asList("12345678","22345678","32345678"));
    public Patient getUser(ContactInfo contactInfo){
        patient.userId = "12444538";
        patient.setBodyLocationImageIds(bodyImageId);
        patient.setContactInfo(contactInfo);
        return patient;
    }
    public static CareProvider getUser(){
        careProvider.userId = "54726731";
        careProvider.setPatientIds(patientId);
        return careProvider;
    }
}

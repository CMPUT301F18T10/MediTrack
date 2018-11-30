package com.example.meditrack;

import java.util.ArrayList;
import java.util.Arrays;

public class MockDataRepositoryUserManager extends DataRepositorySingleton {
    Patient patient;
    CareProvider careProvider;
    ApplicationManager.UserMode userMode;


    public MockDataRepositoryUserManager(ApplicationManager.UserMode userMode){
        String email = "testEmail@ualberta.ca";
        String phoneNumber = "7808001557";
        ArrayList<String> bodyImageId = new ArrayList<String>(Arrays.asList("Img1223","Img1224","Img1225"));
        ContactInfo contactInfo = new ContactInfo(email,phoneNumber);
        Patient patient = new Patient("12345678",bodyImageId,new ArrayList<>(),contactInfo,new ArrayList<String>());

        ArrayList<String> patientId = new ArrayList<String>(Arrays.asList("12345678","22345678","32345678"));
        CareProvider careProvider = new CareProvider("10074289",patientId,new ArrayList<String>());

        this.patient = patient;
        this.careProvider = careProvider;
        this.userMode =userMode;
    }

    public void setUserMode(ApplicationManager.UserMode userMode) {
        this.userMode = userMode;
    }

    public Patient GetPatient()
    {
        return this.patient;
    }

    public CareProvider GetCareProvider()
    {

        return careProvider;
    }
}

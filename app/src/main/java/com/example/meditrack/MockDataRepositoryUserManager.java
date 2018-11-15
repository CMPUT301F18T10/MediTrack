package com.example.meditrack;

import java.util.ArrayList;
import java.util.Arrays;

public class MockDataRepositoryUserManager extends DataRepositorySingleton {
    private static Patient patient;
    private static CareProvider careProvider;

    public static Patient GetPatient()
    {
        String email = "testEmail@ualberta.ca";
        String phoneNumber = "7808001557";
        ArrayList<String> bodyImageId = new ArrayList<String>(Arrays.asList("Img1223","Img1224","Img1225"));
        ContactInfo contactInfo = new ContactInfo(email,phoneNumber);
        patient.setContactInfo(contactInfo);
        patient.setBodyLocationImageIds(bodyImageId);
        return patient;
    }

    public static CareProvider GetCareProvider()
    {
        ArrayList<String> patientId = new ArrayList<String>(Arrays.asList("12345678","22345678","32345678"));
        careProvider.setPatientIds(patientId);
        return careProvider;
    }
}

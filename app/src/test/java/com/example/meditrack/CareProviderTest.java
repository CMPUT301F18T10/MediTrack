package com.example.meditrack;


import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

public class CareProviderTest {
    @Test
    public void TestCareProvider(){
        String ProviderUserId = "1200012";

        String userId1 = "14425631";

        String bitmapId1 = "Img146522";
        String bitmapId2 = "Img146521";
        ArrayList<String> bitmapIds = new ArrayList<String>();
        bitmapIds.add(bitmapId1);
        bitmapIds.add(bitmapId2);

        CareProvider careProvider1 = new CareProvider("12300101");
        CareProvider careProvider2 = new CareProvider("12300102");
        ArrayList<CareProvider> careProviders = new ArrayList<CareProvider>();
        careProviders.add(careProvider1);
        careProviders.add(careProvider2);

        String email = "testEmail@ualberta.ca";
        String phoneNumber = "7808001557";
        ContactInfo contactInfo = new ContactInfo(email,phoneNumber);

        String title = "test";
        String description = "test case";
        Date date = new Date();
        Problem problem1 = new Problem(title,description,date);
        Problem problem2 = new Problem(title,description,date);
        ArrayList<Problem> problems = new ArrayList<Problem>();
        problems.add(problem1);
        problems.add(problem2);


        Patient patient1 = new Patient(userId1,bitmapIds,careProviders,contactInfo,problems);

        ArrayList<Patient> patients = new ArrayList<Patient>();
        patients.add(patient1);
        CareProvider careProvider = new CareProvider(ProviderUserId,patients);
        Assert.assertTrue("Something wrong with care Provider",email.equals(careProvider.getPatients().get(0).getContactInfo().getEmail()));
    }
}

/*package com.example.meditrack;

import android.graphics.Bitmap;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

public class PatientTest {
    @Test
    public void testPatient(){
        String userid = "14425631";

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
        String description = "It's just a test case";
        Date date = new Date();
        Problem problem1 = new Problem(title,description,date);
        Problem problem2 = new Problem(title,description,date);
        ArrayList<Problem> problems = new ArrayList<Problem>();
        problems.add(problem1);
        problems.add(problem2);


        Patient patient = new Patient(userid,bitmapIds,careProviders,contactInfo,problems);

        for(int i = 0; i<bitmapIds.size();i++){
            Assert.assertTrue("body image id is not equal",bitmapIds.get(i).equals(patient.getBodyLocationImages().get(i)));
        }
        for(int i = 0; i<careProviders.size();i++){
            Assert.assertTrue("care providers are not equal",careProviders.get(i).equals(patient.getCareProviders().get(i)));
        }
        Assert.assertTrue("contact email is not equal",email.equals(patient.getContactInfo().getEmail()));
        Assert.assertTrue("contact phone number is not equal",phoneNumber.equals(patient.getContactInfo().getPhoneNumber()));
        //Assert.assertTrue("problem is not equal",title.equals(patient.getProblems()));
        String newEmail = "test@ualberta.ca";
        String newPhoneNumber = "7808001223";
        ContactInfo newContactInfo = new ContactInfo(newEmail,newPhoneNumber);
        patient.setContactInfo(newContactInfo);
        Assert.assertTrue("contact new email is not equal",newEmail.equals(patient.getContactInfo().getEmail()));
        Assert.assertTrue("contact new phone number is not equal",newPhoneNumber.equals(patient.getContactInfo().getPhoneNumber()));
    }
}
*/

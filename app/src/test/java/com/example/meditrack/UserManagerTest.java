package com.example.meditrack;

import org.junit.Assert;
import org.junit.Test;

public class UserManagerTest {
    @Test
    public void testUserManager(){


        // missing part: test addBodyLocationImage(), didn't do the body location image part yet
        String email = "Email@ualberta.ca";
        String phoneNumber = "7806554738";
        UserManager userManager = new UserManager();
        userManager.EditContactInfo(MockDataRepositoryUserManager.GetPatient(), email, phoneNumber);
        Patient patient = MockDataRepositoryUserManager.GetPatient();

        //check EditContactInfo()
        Assert.assertTrue("Edit method does not work in right way", email.equals(patient.getContactInfo().getEmail()));

        //check checkBodyImageNumber()
        Assert.assertTrue("check image number method does not work in right way", 3 == userManager.checkBodyImageNumber(MockDataRepositoryUserManager.GetPatient()));

        //check deleteBodyLocationImage()
        userManager.deleteBodyLocationImage(MockDataRepositoryUserManager.GetPatient(),"Img1224");
        Assert.assertTrue("delete image method does not work in right way",2 == userManager.checkBodyImageNumber(MockDataRepositoryUserManager.GetPatient()));

        //check addPatient()
        String id = "22345878";
        int lengthOfList = MockDataRepositoryUserManager.GetCareProvider().getPatientIds().size();
        userManager.addPatient(MockDataRepositoryUserManager.GetCareProvider(),id);
        int new_length = MockDataRepositoryUserManager.GetCareProvider().getPatientIds().size();
        Assert.assertTrue("add patient method does not work in right way", lengthOfList+1 == new_length );

    }

}

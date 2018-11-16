package com.example.meditrack;

import org.junit.Assert;
import org.junit.Test;

public class UserManagerTest {
    @Test
    public void testUserManager() throws DataRepositorySingleton.InvalidUserMode, DataRepositorySingleton.DataRepositorySingletonNotInitialized {


        // missing part: test addBodyLocationImage(), didn't do the body location image part yet
        String email = "Email@ualberta.ca";
        String phoneNumber = "7806554738";
        UserManager userManager = new UserManager();
        MockDataRepositoryUserManager mockDataRepositoryUserManager = null;
        userManager.patient = mockDataRepositoryUserManager.GetPatient();
        userManager.careProvider = mockDataRepositoryUserManager.GetCareProvider();
        userManager.EditContactInfo(userManager, email, phoneNumber);

        //check EditContactInfo()
        Assert.assertTrue("Edit method does not work in right way", email.equals(userManager.patient.getContactInfo().getEmail()));

        //check checkBodyImageNumber()
        Assert.assertTrue("check image number method does not work in right way", 3 == userManager.checkBodyImageNumber(userManager));

        //check deleteBodyLocationImage()
        userManager.deleteBodyLocationImage(userManager,"Img1224");
        Assert.assertTrue("delete image method does not work in right way",2 == userManager.checkBodyImageNumber(userManager));

        //check addPatient()
        String id = "22345878";
        int lengthOfList = userManager.careProvider.getPatientIds().size();
        userManager.addPatient(userManager,id);
        int new_length = userManager.careProvider.getPatientIds().size();
        Assert.assertTrue("add patient method does not work in right way", lengthOfList+1 == new_length );

    }

}


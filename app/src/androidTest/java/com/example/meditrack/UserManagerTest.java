package com.example.meditrack;

import org.junit.Assert;
import org.junit.Test;

public class UserManagerTest {
    String email = "Email@ualberta.ca";
    String phoneNumber = "7806554738";
    @Test
    public void testUserManager() throws DataRepositorySingleton.InvalidUserMode, DataRepositorySingleton.DataRepositorySingletonNotInitialized {

        // missing part: test addBodyLocationImage(), didn't do the body location image part yet

        UserManager userManager = new UserManager();
        MockDataRepositoryUserManager mockDRUM = new MockDataRepositoryUserManager(ApplicationManager.UserMode.Patient);
        ApplicationManager applicationManager = new ApplicationManager(ApplicationManager.UserMode.Patient);
        userManager.patient = mockDRUM.GetPatient();
        userManager.careProvider = mockDRUM.GetCareProvider();



        userManager.EditContactInfo(phoneNumber,email);

        //check EditContactInfo()
        Assert.assertTrue("Edit method does not work in right way", email.equals(userManager.patient.getContactInfo().getEmail()));

        //check checkBodyImageNumber()
        Assert.assertTrue("check image number method does not work in right way", 3 == userManager.checkBodyImageNumber());

        //check deleteBodyLocationImage()
        userManager.deleteBodyLocationImage("Img1224");
        Assert.assertTrue("delete image method does not work in right way",2 == userManager.checkBodyImageNumber());

        //check addPatient()
        String id = "22345878";
        int lengthOfList = userManager.careProvider.getPatientIds().size();
        userManager.addPatient(id);
        int new_length = userManager.careProvider.getPatientIds().size();
        Assert.assertTrue("add patient method does not work in right way", lengthOfList+1 == new_length );

    }

}


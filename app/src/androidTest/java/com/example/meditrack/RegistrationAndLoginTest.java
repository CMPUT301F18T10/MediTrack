package com.example.meditrack;
import android.util.Log;
import org.junit.Test;
import java.util.ArrayList;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.assertFalse;


public class RegistrationAndLoginTest {


    @Test
    public void testRegistrationForPatient(){
        String userId = "tester_patient";
        ArrayList<String> approvedIds = new ArrayList<String>();
        ApplicationManager applicationManager = new ApplicationManager(ApplicationManager.UserMode.Patient);
        applicationManager.RegisterUser(userId,approvedIds);
        // We expect the first registration to go through
        //assertTrue(applicationManager.RegisterUser(userId,approvedIds));
        // Now, we search our database and verify that we can find the registered user
        boolean userExist = applicationManager.DoesUserExist(userId);
        if (userExist){
            Log.e("UserCheck","The user has been added");
        }
        assertTrue(userExist);

    }
    @Test
    public void testRegistrationForCareProvider(){
        String userId = "tester_careProvider";
        ArrayList<String> approvedIds = new ArrayList<String>();
        ApplicationManager applicationManager = new ApplicationManager(ApplicationManager.UserMode.CareGiver);
        applicationManager.RegisterUser(userId,approvedIds);
        // We expect the first registration to go through
        //assertTrue(applicationManager.RegisterUser(userId,approvedIds));
        // Now, we search our database and verify that we can find the registered user
        //assertTrue(applicationManager.DoesUserExist(userId));
        boolean userExist = applicationManager.DoesUserExist(userId);
        if (userExist){
            Log.e("UserCheck","The user has been added");
        }
        assertTrue(userExist);

    }
    @Test
    public void testDoubleRegistration(){
        String userId1 = "tester1";
        ArrayList<String> approvedIds = new ArrayList<String>();
        ApplicationManager applicationManager = new ApplicationManager(ApplicationManager.UserMode.CareGiver);
        // We expect the first registration to go through
        applicationManager.RegisterUser(userId1, approvedIds);
        // We expect the registration to fail when we try to register with the same Id
        boolean userExist = applicationManager.RegisterUser(userId1, approvedIds);
        if (!userExist){
            Log.e("UserCheck","The user has been added, Register Failure");
        }
        assertFalse(userExist);

    }
    @Test
    public void testLoginForPatient(){
        String userId = "tester_patient";
        ApplicationManager applicationManager = new ApplicationManager(ApplicationManager.UserMode.Patient);
        boolean userLogin = applicationManager.LogIn(userId,DataRepositorySingleton.GetInstance());
        if (userLogin){
            Log.e("UserLogin","Login Successful");
        }
        assertTrue(userLogin);
    }
    @Test
    public void testLoginForCareprovider(){
        String userId = "tester_careProvider";
        ApplicationManager applicationManager = new ApplicationManager(ApplicationManager.UserMode.CareGiver);
        boolean userLogin = applicationManager.LogIn(userId,DataRepositorySingleton.GetInstance());
        if (userLogin){
            Log.e("UserLogin","Login Successful");
        }
        assertTrue(userLogin);

    }


}


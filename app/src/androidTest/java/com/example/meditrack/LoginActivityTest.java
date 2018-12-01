package com.example.meditrack;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;
import android.widget.EditText;

import com.robotium.solo.Solo;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class LoginActivityTest {
    @Rule
    public ActivityTestRule<LoginActivity> activityTestRule = new ActivityTestRule<>(LoginActivity.class);
    private Solo solo;
    private ApplicationManager mAM;

    @Before
    public void setUp() throws Exception{
        Log.d("LoginActivity","in setUp");
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),activityTestRule.getActivity());
    }
    @Test
    public void TestLoginUnregisterUserID(){
        //Test for Unregister user id
        //If this test failure, please change the userId To anotherOne, Since the server may have receive the userID
        solo.assertCurrentActivity("Login Activity",LoginActivity.class);
        solo.enterText((EditText) solo.getView(R.id.loginEmail),"newuserId30");
        solo.clickOnButton("Login");
        assertTrue(solo.waitForText("This User Email Is Not Registered, Please Register it!"));
        }
    @Test
    public void TestLoginregisterUserID(){
        //Test for Unregister user id
        solo.assertCurrentActivity("Login Activity",LoginActivity.class);
        solo.clearEditText((EditText) solo.getView(R.id.loginEmail));
        solo.enterText((EditText) solo.getView(R.id.loginEmail),"testchris8");
        solo.clickOnButton("Login");
        //assertTrue(solo.waitForText("This Device Is Not Authorized, Please Enter Short Code!"));
        //If you login in new device, you will be asked for a shortcode.

    }

    @Test
    public void TestregisterNewUserID(){
        //Test for Unregister user id
        //If this test failure, please change the userId To anotherOne, Since the server may have receive the userID
        solo.assertCurrentActivity("Login Activity",LoginActivity.class);
        solo.clearEditText((EditText) solo.getView(R.id.loginEmail));
        solo.enterText((EditText) solo.getView(R.id.loginEmail),"newuserID3");
        solo.clickOnButton("Register");
        solo.assertCurrentActivity("Waiting for the Short code",LoginActivity.class);
    }

    @Test
    public void TestregisterExsitUserID(){
        //Test for Unregister user id
        solo.assertCurrentActivity("Login Activity",LoginActivity.class);
        solo.clearEditText((EditText) solo.getView(R.id.loginEmail));
        solo.enterText((EditText) solo.getView(R.id.loginEmail),"testchris8");
        solo.clickOnButton("Register");
        assertTrue(solo.waitForText("This User Email Has Registered, Please Login it!"));
    }



}
package com.example.meditrack;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class ProfileInfomationActivityTest {
    @Rule
    public ActivityTestRule<ProfileInformationActivity> profileTestRule = new ActivityTestRule<>(ProfileInformationActivity.class);
    private ProfileInformationActivity profile = null;

    @Before
    public void setUp() throws Exception{
      profile = profileTestRule.getActivity();
    }

    @Test
    public void ProfileTest() throws Exception {

        View viewID = profile.findViewById( R.id.profilePhoneInput );
        View viewEmail = profile.findViewById( R.id.profileEmailInput );
        View viewPhone = profile.findViewById( R.id.profileUserIDInput );
        assertNotNull( viewID );
        assertNotNull( viewEmail );
        assertNotNull( viewPhone );

        }

    @Test
    public void saveTest() throws Exception {

        onView(withId(R.id.profileSaveButton)).perform(click());

    }


    protected void tearDown() throws Exception{
       profile = null;
    }



}
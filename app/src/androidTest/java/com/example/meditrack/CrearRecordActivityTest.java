package com.example.meditrack;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertNotNull;

public class CrearRecordActivityTest {

    @Rule
    public ActivityTestRule<CreatePatientRecordActivity> patientRecordTestRule = new ActivityTestRule<>(CreatePatientRecordActivity.class);
    private CreatePatientRecordActivity patientRecord = null;

    @Before
    public void setUp() throws Exception{
        patientRecord = patientRecordTestRule.getActivity();
    }

    @Test
    public void createRecordTest() throws Exception {

        View recordTitle = patientRecord.findViewById( R.id.createRecordTitle );
        View recordComment = patientRecord.findViewById( R.id.createRecordComment );
        assertNotNull( recordTitle );
        assertNotNull( recordComment );

        }

    @Test
    public void phototest() throws Exception {

        onView(withId(R.id.createRecordBrowsePhotos)).perform(click());
        onView(withId(R.id.createRecordSavePhoto)).perform(click());

    }

    @Test
    public void recordFinishTest() throws Exception {

        onView(withId(R.id.createRecordFinish)).perform(click());

    }


    protected void tearDown() throws Exception{
        patientRecord = null;
    }



}
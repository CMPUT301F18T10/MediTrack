package com.example.meditrack;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertNotNull;

public class PatientListActivityTest {
    @Rule
    public ActivityTestRule<PatientsListActivity> patientListTestRule = new ActivityTestRule<>(PatientsListActivity.class);
    private PatientsListActivity patientList = null;

    @Before
    public void setUp() throws Exception{
    }

    @Test
    public void addPatient() throws Exception {

        onView(withId(R.id.patientsListAddFAB)).perform(click());

    }


    protected void tearDown() throws Exception{
        patientList = null;
    }



}
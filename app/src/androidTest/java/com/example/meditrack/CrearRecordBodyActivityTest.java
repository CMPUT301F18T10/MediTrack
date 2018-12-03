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

public class CrearRecordBodyActivityTest {
    @Rule
    public ActivityTestRule<createRecordBodyActivity> patientRecordBodyTestRule = new ActivityTestRule<>(createRecordBodyActivity.class);
    private createRecordBodyActivity patientRecordBody = null;

    @Before
    public void setUp() throws Exception{
        patientRecordBody = patientRecordBodyTestRule.getActivity();
    }

    @Test
    public void createRecordBodyTest() throws Exception {

        View bodyText = patientRecordBody.findViewById( R.id.createRecordBodyText );
        assertNotNull( bodyText );

        }

    @Test
    public void recordBodySavetest() throws Exception {

        onView(withId(R.id.createRecordBodySaveButton)).perform(click());

    }



    protected void tearDown() throws Exception{
        patientRecordBody = null;
    }



}
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

public class ViewRecordActivityTest {
    @Rule
    public ActivityTestRule<viewRecordActivity> viewRecordActivityTestRule = new ActivityTestRule<>(viewRecordActivity.class);
    private viewRecordActivity viewRecordActivity = null;

    @Before
    public void setUp() throws Exception{
        viewRecordActivity = viewRecordActivityTestRule.getActivity();
    }

    @Test
    public void createRecordBodyTest() throws Exception {

        View recordComment = viewRecordActivity.findViewById( R.id.viewRecordComment );
        assertNotNull( recordComment );
        }

    @Test
    public void bodyLocationTest() throws Exception {

        onView(withId(R.id.viewRecordBodyLocactionButton)).perform(click());

    }

    @Test
    public void GPSTest() throws Exception {

        onView(withId(R.id.viewRecordGPSButton)).perform(click());

    }

    @Test
    public void deleteRecordTest() throws Exception {

        onView(withId(R.id.deleteRecordButton)).perform(click());

    }


    protected void tearDown() throws Exception{
        viewRecordActivity = null;
    }



}
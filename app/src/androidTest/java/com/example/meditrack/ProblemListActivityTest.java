package com.example.meditrack;

import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class ProblemListActivityTest {
    @Rule
    public ActivityTestRule<PatientsListActivity> patientListTestRule = new ActivityTestRule<>(PatientsListActivity.class);
    private PatientsListActivity patientList = null;

    @Before
    public void setUp() throws Exception{
    }

    @Test
    public void problemListTest() throws Exception {

        onView(withId(R.id.problemsListProfileFAB)).perform(click());
        onView(withId(R.id.problemsListSearchFAB)).perform(click());

    }
    @Test
    public void problemManipulateTest() throws Exception {

        onView(withId(R.id.problemListDeleteButton)).perform(click());
        onView(withId(R.id.problemsListAddFAB)).perform(click());

    }


    protected void tearDown() throws Exception{
        patientList = null;
    }



}
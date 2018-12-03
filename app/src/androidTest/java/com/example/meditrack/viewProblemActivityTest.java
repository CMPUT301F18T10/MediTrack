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

public class viewProblemActivityTest {
    @Rule
    public ActivityTestRule<viewProblemActivity> viewProblemTestRule = new ActivityTestRule<>(viewProblemActivity.class);
    private viewProblemActivity viewProblemActivity = null;

    @Before
    public void setUp() throws Exception{
        viewProblemActivity = viewProblemTestRule.getActivity();
    }

    @Test
    public void createRecordBodyTest() throws Exception {

        View bodyText = viewProblemActivity.findViewById( R.id.problemViewComment );
        assertNotNull( bodyText );

        }

    @Test
    public void viewProblemButtonTest() throws Exception {

        onView(withId(R.id.viewProblemDeleteProblemButton)).perform(click());
        onView(withId(R.id.viewProblemViewCommentsFAB)).perform(click());
        onView(withId(R.id.viewProblemAddFAB)).perform(click());
    }

    protected void tearDown() throws Exception{
        viewProblemActivity = null;
    }



}
package com.example.meditrack;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ElasticsearchManagerTest {

    private static final long delay = 1000;

    @Rule
    public ActivityTestRule<LoginActivity> activityTestRule = new ActivityTestRule<>(LoginActivity.class);

    private Solo solo;
    private ElasticsearchManager esm;

    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), activityTestRule.getActivity());
        esm = new ElasticsearchManager();
        esm.setTestingMode();
    }

    @After
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

    @Test
    public void testAddProblem() throws Exception {
        Problem problem = new Problem("testProblem", "testDescription", "testPatientId");
        esm.addObject(problem);

        // Wait a bit so the given problem is properly added to Elasticsearch server
        Thread.sleep(delay);

        Problem problem2 = esm.getObjectFromId(problem.getId(), problem.getElasticsearchType(), problem.getClass());
        assert(problem.getId() == problem2.getId());
    }

    @Test
    public void testAddPatientRecord() throws Exception {
        Problem problem = new Problem("testProblem", "testDescription", "testPatientId");
        PatientRecord pr_expected = new PatientRecord(problem.getId(), "title", "description", null, null, null);
        esm.addObject(pr_expected);

        Thread.sleep(delay);

        PatientRecord pr_actual = esm.getObjectFromId(pr_expected.getId(), pr_expected.getElasticsearchType(), pr_expected.getClass());
        assert(pr_actual.getId() == pr_expected.getId());
    }

}

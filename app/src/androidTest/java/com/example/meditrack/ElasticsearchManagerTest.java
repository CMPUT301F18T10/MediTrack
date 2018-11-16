package com.example.meditrack;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static junit.framework.TestCase.assertTrue;

@RunWith(AndroidJUnit4.class)
public class ElasticsearchManagerTest {

    private static final long delay = 1000;

    @Rule
    public ActivityTestRule<LoginActivity> activityTestRule = new ActivityTestRule<>(LoginActivity.class);

    private Solo solo;
    private ElasticsearchManager esm;

    private ContactInfo testContactInfo = new ContactInfo("jack2018@cmput301.com", "780-807-078");

    private Patient testPatient = new Patient("Jack", null, testContactInfo);
    private CareProvider careProvider = new CareProvider("Peter");

    private Problem testProblem = new Problem("testProblem", "testDescription", testPatient.getUserId());
    private Problem testProblem1 = new Problem("testProblem1", "testDescription1", testPatient.getUserId());
    private Problem testProblem2 = new Problem("testProblem2", "testDescription2", testPatient.getUserId());

    private PatientRecord testPatientRecord = new PatientRecord(testProblem.getId(), "title", "description", null, null, null);
    private PatientRecord testPatientRecord1 = new PatientRecord(testProblem.getId(), "title1", "description1", null, null, null);
    private PatientRecord testPatientRecord2 = new PatientRecord(testProblem.getId(), "title2", "description2", null, null, null);

    private CareProviderRecord testCareProviderRecord = new CareProviderRecord(testProblem.getId(), "careProviderComment", careProvider.getUserId());
    private CareProviderRecord testCareProviderRecord1 = new CareProviderRecord(testProblem.getId(), "careProviderComment2", careProvider.getUserId());
    private CareProviderRecord testCareProviderRecord2 = new CareProviderRecord(testProblem.getId(), "careProviderComment3", careProvider.getUserId());

    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), activityTestRule.getActivity());
        esm = new ElasticsearchManager();
        esm.setTestingMode();
        esm.resetIndex();
    }

    @After
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

    @Test
    public void testAddProblem() throws Exception {

        esm.addObject(testProblem);

        // Wait a bit so the given problem is properly added to Elasticsearch server
        Thread.sleep(delay);

        Problem obtained = esm.getObjectFromId(testProblem.getId(), testProblem.getElasticsearchType(), testProblem.getClass());
        assertTrue(testProblem.getId().equals(obtained.getId()));
    }

    @Test
    public void testAddPatientRecord() throws Exception {
        esm.addObject(testPatientRecord);

        Thread.sleep(delay);

        PatientRecord obtained = esm.getObjectFromId(testPatientRecord.getId(), testPatientRecord.getElasticsearchType(), testPatientRecord.getClass());
        assertTrue(testPatientRecord.getId().equals(obtained.getId()));
    }

    @Test
    public void testGetProblemsByPatientId() throws Exception {

        ArrayList<Problem> problems = new ArrayList<>();
        problems.add(testProblem);
        problems.add(testProblem1);
        problems.add(testProblem2);

        // getProblemsByPatientId should not pick this problem up
        Problem randomProblem = new Problem("randomProblem", "randomDescription", "weirdId");
        problems.add(randomProblem);

        esm.addObjects(problems);
        Thread.sleep(delay);
        ArrayList<Problem> obtained = esm.getProblemsByPatientId(testPatient.getUserId());

        Log.i("esm", "obtained..: " + new Integer(obtained.size()).toString());
        assertTrue(obtained.size() == 3);

        for (Problem p : obtained) {
            assertTrue(p.getPatientId().equals(testPatient.getUserId()));
        }
    }

    @Test
    public void testGetPatientRecordByProblemId() throws Exception {
        ArrayList<PatientRecord> patientRecords = new ArrayList<>();
        patientRecords.add(testPatientRecord);
        patientRecords.add(testPatientRecord1);
        patientRecords.add(testPatientRecord2);

        // getPatientRecordByProblemId should not pick this record up
        PatientRecord randomPatientRecord =  new PatientRecord("randomProblemId", "randomTitle", "randomDescription", null, null, null);
        patientRecords.add(randomPatientRecord);

        esm.addObjects(patientRecords);
        Thread.sleep(delay);
        ArrayList<PatientRecord> obtained = esm.getPatientRecordByProblemId(testProblem.getId());

        assertTrue(obtained.size() == 3);

        for (PatientRecord pr : obtained) {
            assertTrue(pr.getProblemId().equals(testProblem.getId()));
        }
    }

    @Test
    public void testGetCareProviderRecordByProblemId() throws  Exception {
        ArrayList<CareProviderRecord> careProviderRecords = new ArrayList<>();
        careProviderRecords.add(testCareProviderRecord);
        careProviderRecords.add(testCareProviderRecord1);
        careProviderRecords.add(testCareProviderRecord2);

        // getCareProviderRecordByProblemId should not pick this record up
        CareProviderRecord randomCareProviderRecord = new CareProviderRecord("randomId", "careProviderComment", "randomCareProviderId");
        careProviderRecords.add(randomCareProviderRecord);

        esm.addObjects(careProviderRecords);
        Thread.sleep(delay);
        ArrayList<CareProviderRecord> obtained = esm.getCareProviderRecordByProblemId(testProblem.getId());

        assertTrue(obtained.size() == 3);

        for (CareProviderRecord cpr : obtained) {
            assertTrue(cpr.getProblemId().equals(testProblem.getId()));
        }
    }

}

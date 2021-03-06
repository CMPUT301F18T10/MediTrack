package com.example.meditrack;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashSet;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertTrue;

@RunWith(AndroidJUnit4.class)
public class ElasticsearchManagerTest {

    private static final long delay = 1000;

    @Rule
    public ActivityTestRule<LoginActivity> activityTestRule = new ActivityTestRule<>(LoginActivity.class);

    private Solo solo;
    private ElasticsearchManager esm;

    private ContactInfo testContactInfo = new ContactInfo("jack2018@cmput301.com", "780-807-078");

    private Patient testPatient = new Patient("Jack", null, new ArrayList<>(),testContactInfo,new ArrayList<>());
    private Patient testPatient1 = new Patient("Joe", null, new ArrayList<>(), testContactInfo,new ArrayList<>());
    private Patient testPatient2 = new Patient("Mary", null, new ArrayList<>(), testContactInfo,new ArrayList<>());

    private CareProvider testCareProvider = new CareProvider("Peter");

    private Problem testProblem = new Problem("testProblem", "CMPUT301 is hard", testPatient.getId());
    private Problem testProblem1 = new Problem("CMPUT301", "testDescription1", testPatient.getId());
    private Problem testProblem2 = new Problem("testProblem2", "CMPUT401 is even harder", testPatient.getId());

    private PatientRecord testPatientRecord = new PatientRecord(testProblem.getId(), "Elasticsearch!", "I love Elasticsearch", null, null);
    private PatientRecord testPatientRecord1 = new PatientRecord(testProblem.getId(), "Hmm", "I think that was a bold statement", null, null);
    private PatientRecord testPatientRecord2 = new PatientRecord(testProblem.getId(), "OK", "I hate Elasticsearch", null, null);

    private CareProviderRecord testCareProviderRecord = new CareProviderRecord(testProblem.getId(), "Good to hear!", testCareProvider.getId());
    private CareProviderRecord testCareProviderRecord1 = new CareProviderRecord(testProblem.getId(), "Elasticsearch is good, don't hate it.", testCareProvider.getId());
    private CareProviderRecord testCareProviderRecord2 = new CareProviderRecord(testProblem.getId(), "Get over it.", testCareProvider.getId());

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
    public void testObjectDoesNotExistException() throws Exception {
        try {
            esm.deleteObject("DoesNotExist", "problems", Problem.class);
            assertTrue(false);
        } catch (ElasticsearchManager.ObjectNotFoundException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testObjectAlreadyExistsException() throws Exception {
        esm.addObject(testProblem);
        Thread.sleep(delay);
        try {
            esm.addObject(testProblem);
            assertTrue(false);
        } catch (ElasticsearchManager.ObjectAlreadyExistsException e) {
            assertTrue(true);
        }
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
        ArrayList<Problem> obtained = esm.getProblemsByPatientId(testPatient.getId());

        assertTrue(obtained.size() == 3);

        for (Problem p : obtained) {
            assertTrue(p.getPatientId().equals(testPatient.getId()));
        }
    }

    @Test
    public void testGetPatientRecordByProblemId() throws Exception {
        ArrayList<PatientRecord> patientRecords = new ArrayList<>();
        patientRecords.add(testPatientRecord);
        patientRecords.add(testPatientRecord1);
        patientRecords.add(testPatientRecord2);

        // getPatientRecordByProblemId should not pick this record up
        PatientRecord randomPatientRecord =  new PatientRecord("randomProblemId", "randomTitle", "randomDescription", null, null);
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

    @Test
    public void testExistObject() throws Exception {
        assertTrue(!esm.existObject(testPatient.getId(), testPatient.getElasticsearchType(), testPatient.getClass()));

        esm.addObject(testPatient);
        Thread.sleep(delay);

        assertTrue(esm.existObject(testPatient.getId(), testPatient.getElasticsearchType(), testPatient.getClass()));
    }

    @Test
    public void testDeleteObject() throws Exception {
        esm.addObject(testPatient);
        Thread.sleep(delay);
        assertTrue(esm.existObject(testPatient.getId(), testPatient.getElasticsearchType(), testPatient.getClass()));

        esm.deleteObject(testPatient.getId(), testPatient.getElasticsearchType(), testPatient.getClass());
        Thread.sleep(delay);
        assertTrue(!esm.existObject(testPatient.getId(), testPatient.getElasticsearchType(), testPatient.getClass()));
    }

    @Test
    public void testUpdateObject() throws Exception {
        CareProvider obtained;

        esm.addObject(testCareProvider);
        Thread.sleep(delay);
        obtained = esm.getObjectFromId(testCareProvider.getId(), testCareProvider.getElasticsearchType(), testCareProvider.getClass());
        assertTrue(obtained.equals(testCareProvider));

        ArrayList<String> patientIds = new ArrayList<>();
        patientIds.add(testPatient.getId());
        patientIds.add(testPatient1.getId());
        testCareProvider.setPatientIds(patientIds);

        esm.updateObject(testCareProvider.getId(), testCareProvider.getElasticsearchType(), testCareProvider);
        Thread.sleep(delay);
        obtained = esm.getObjectFromId(testCareProvider.getId(), testCareProvider.getElasticsearchType(), testCareProvider.getClass());
        assertTrue(obtained.equals(testCareProvider));
    }

    @Test
    public void testSearchProblems() throws Exception {
        ArrayList<Problem> problems = new ArrayList<>();
        problems.add(testProblem);
        problems.add(testProblem1);
        problems.add(testProblem2);

        esm.addObjects(problems);
        Thread.sleep(delay);

        ArrayList<Problem> obtained = esm.searchProblems("CMPUT301");
        HashSet<Problem> obtainedSet = new HashSet<>(obtained);

        HashSet<Problem> expectedSet = new HashSet();
        expectedSet.add(testProblem);
        expectedSet.add(testProblem1);
        /* Search results should not contain testProblem2 */

        assertEquals(obtainedSet, expectedSet);
    }

    @Test
    public void testSearchPatientRecords() throws Exception {
        ArrayList<PatientRecord> patientRecords = new ArrayList<>();
        patientRecords.add(testPatientRecord);
        patientRecords.add(testPatientRecord1);
        patientRecords.add(testPatientRecord2);

        esm.addObjects(patientRecords);
        Thread.sleep(delay);

        ArrayList<PatientRecord> obtained = esm.searchPatientRecords("Elasticsearch");
        HashSet<PatientRecord> obtainedSet = new HashSet<>(obtained);

        HashSet<PatientRecord> expectedSet = new HashSet<>();
        expectedSet.add(testPatientRecord);
        /* Search results should not contain testPatientRecord1 */
        expectedSet.add(testPatientRecord2);

        assertEquals(expectedSet, obtainedSet);
    }

    @Test
    public void testSearchCareProviderRecord() throws Exception {
        ArrayList<CareProviderRecord> careProviderRecords = new ArrayList<>();
        careProviderRecords.add(testCareProviderRecord);
        careProviderRecords.add(testCareProviderRecord1);
        careProviderRecords.add(testCareProviderRecord2);

        esm.addObjects(careProviderRecords);
        Thread.sleep(delay);

        ArrayList<CareProviderRecord> obtained = esm.searchCareProviderRecord("good");
        HashSet<CareProviderRecord> obtainedSet = new HashSet<>(obtained);

        HashSet<CareProviderRecord> expectedSet = new HashSet<>();
        expectedSet.add(testCareProviderRecord);
        expectedSet.add(testCareProviderRecord1);
        /* Search results should not contain testCareProviderRecord2 */

        assertEquals(expectedSet, obtainedSet);
    }

}

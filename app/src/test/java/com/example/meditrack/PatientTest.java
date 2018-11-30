package com.example.meditrack;
import org.junit.Test;
import java.util.ArrayList;
import static junit.framework.TestCase.assertTrue;


public class PatientTest {
    private ArrayList<String> bitmapIds = new ArrayList<String>();
    private ArrayList<String> careProviderIds = new ArrayList<String>();
    private ArrayList<String> deviceIds = new ArrayList<String>();
    private ArrayList<String> patientIds = new ArrayList<String>();

    private ArrayList<CareProvider> careProviders = new ArrayList<CareProvider>();
    private ArrayList<Patient> patients = new ArrayList<Patient>();
    private ArrayList<Problem> problems = new ArrayList<Problem>();

    String careProviderUserId = "1200012";
    String careProviderUserId2 = "12300102";
    String patientId1 = "14425631";
    String patientId2 = "12345678";
    String bitmapId1 = "Img146522";
    String bitmapId2 = "Img146521";
    @Test
    public void testPatient(){
        String email = "testEmail@ualberta.ca";
        String phoneNumber = "7808001557";
        ContactInfo contactInfo = new ContactInfo(email,phoneNumber);

        String title = "test";
        String description = "test case";

        Problem problem1 = new Problem(title,description,careProviderUserId);
        Problem problem2 = new Problem(title,description,careProviderUserId);
        problems.add(problem1);
        problems.add(problem2);

        Patient patient1 = new Patient(patientId1,bitmapIds,careProviderIds,contactInfo,deviceIds);
        Patient patient2 = new Patient(patientId2,bitmapIds,careProviderIds,contactInfo,deviceIds);

        assertTrue(patient1.getApprovedDeviceID() == deviceIds);
        assertTrue(patient2.getId()== patientId2);
        assertTrue(patient1.getBodyLocationImages()==bitmapIds);
        assertTrue(patient1.getCareProviderId() == careProviderIds);

    }
    public void TestPatientID(){
        //Since We disallow inputed userid less than 8 characters. Therefore,
        //if the user input userid which is less than 8 chars, we disallow this id to be register.

    }
}


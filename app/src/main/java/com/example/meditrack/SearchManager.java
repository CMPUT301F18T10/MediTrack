package com.example.meditrack;
import android.location.Location;
import com.example.meditrack.BodyLocation;
import com.example.meditrack.Problem;
import com.example.meditrack.PatientRecord;
import com.example.meditrack.CareGiverRecord;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

public class SearchManager {

    // TODO: Get rid of this class no longer needed

    /* This class allows user search problem or records by given specify attributes.
        Not just for searching as a function in the application, but every time a
        service needs to manipulate the model/data classes, the SearchManager class
        would be called to fetch the right object from ApplicationManager if a local copy is required.

        All the data is located in the DataRepositorySingleton and this class would be used to
        get the relevant ones we need
    */
        //Searching Problems
        public static void searchProblemByGeoLocation(ArrayList<Problem> problems, Location location) {
            String query = "  ";
            //Use elasticSearch
            //TODO: Finish the method
        }
        public static void searchProblemByBodyLocation(ArrayList<Problem> problems,BodyLocation bodyLocation) {
            String query = "  ";
            //Use elasticSearch
            //TODO: Finish the method
        }
        public static void searchProblemByKeyword(ArrayList<Problem> problems,String keyword){
            String query = "  ";
            //Use elasticSearch
            //TODO: Finish the method
        }


        //Searching Records
        public static void searchRecordByGeoLocation(ArrayList<AbstractRecord> records,Location location){
            String query = "  ";
            //Use elasticSearch
            //TODO: Finish the method
        }
        public static void searchRecordByBodyLocation(ArrayList<AbstractRecord> records,BodyLocation bodyLocation){
            String query = "  ";
            //Use elasticSearch
            //TODO: Finish the method
        }

        public static void searchRecordByKeyword(ArrayList<AbstractRecord> records,String keyword){
            String query = "  ";
            //Use elasticSearch
            //TODO: Finish the method
        }

        // Methods that fetch data from ElasticSearch instance
        public static Problem getProblemForPatientId(String patientUserId, Integer problemId) throws ItemNotFound
        {
            // TODO: Finish the method
            String exceptionMessage = "Problem Id: " + problemId.toString() + " does not exist for Patient Id: " + patientUserId;
            throw new ItemNotFound(exceptionMessage);
        }

        public static ArrayList<Problem> getProblemListForPatientId(String patientUserId)
        {
            // TODO: Finish the method
            return new ArrayList<>();
        }

        public static ArrayList<AbstractRecord> getRecordListForProblemId(String patientUserId, Integer problemId)
        {
            return new ArrayList<>();
        }

        public static ArrayList<CareGiverRecord> getCareGiveRecordsForCareGiverId(String careGiverId)
        {
            // TODO: Finish the method
            return new ArrayList<>();
        }

        public static ArrayList<PatientRecord> getPatientRecordsForPatientId(String patientUserId)
        {
            // TODO: Finish the method
            return new ArrayList<>();
        }

        public static Integer GetRecordCountForProblemId(Integer problemId)
        {
            // TODO: Finish the method
            return -1;
        }

}



package com.example.meditrack;
import android.location.Location;
import com.example.meditrack.BodyLocation;
import com.example.meditrack.Problem;
import com.example.meditrack.PatientRecord;
import com.example.meditrack.CareGiverRecord;
import java.util.ArrayList;
/*
This class allows user search problem or records by given specify attributes.






*/
public class SearchManager {

        //Searching Problems
        public void searchProblemByGeoLocation(ArrayList<Problem> problems, Location location) {
            String query = "  ";
            //Use elasticSearch
            //TODO: Finish the method
        }
        public void searchProblemByBodyLocation(ArrayList<Problem> problems,BodyLocation bodyLocation) {
            String query = "  ";
            //Use elasticSearch
            //TODO: Finish the method
        }
        public void searchProblemByKeyword(ArrayList<Problem> problems,String keyword){
            String query = "  ";
            //Use elasticSearch
            //TODO: Finish the method
        }


        //Searching Records
        public void searchRecordByGeoLocation(ArrayList<AbstractRecord> records,Location location){
            String query = "  ";
            //Use elasticSearch
            //TODO: Finish the method
        }
        public void searchRecordByBodyLocation(ArrayList<AbstractRecord> records,BodyLocation bodyLocation){
            String query = "  ";
            //Use elasticSearch
            //TODO: Finish the method
        }

        public void searchRecordByKeyword(ArrayList<AbstractRecord> records,String keyword){
            String query = "  ";
            //Use elasticSearch
            //TODO: Finish the method
        }

}



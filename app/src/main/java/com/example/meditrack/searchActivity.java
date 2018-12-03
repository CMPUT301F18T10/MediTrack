package com.example.meditrack;

import android.content.Intent;
import android.icu.text.AlphabeticIndex;
import android.opengl.EGL14;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;

import java.util.ArrayList;

public class searchActivity extends AppCompatActivity {
    private DataRepositorySingleton mDRS = DataRepositorySingleton.GetInstance();;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }
    public void clickGo(View view) {
        RadioButton bodyRadio = (RadioButton) findViewById(R.id.searchBodyRadio);
        RadioButton GPSRadio = (RadioButton) findViewById(R.id.searchGPSRadio);
        RadioButton recordRadio = (RadioButton) findViewById(R.id.searchRecordRadio);
        RadioButton problemRadio = (RadioButton) findViewById(R.id.searchProblemRadio);

        if(bodyRadio.isChecked()){
            //searchmanager bodysearch record
        }
        else if(GPSRadio.isChecked()){
            //searchmanager GPSsearch record
        }



        else if(recordRadio.isChecked()){
            //searchmanager recordsearch by keyword[
            ArrayList<String> recordSearchList = new ArrayList<>();
            Intent intent = getIntent();
            String PatientId = intent.getStringExtra("patientID");
            ArrayList Problems = mDRS.GetProblemsForPatientId(PatientId);
            String PatientRecordTitle = new String();
            String PatientRecordDescription = new String();
            String CareGiverComment = new String();
            EditText RecordKeywordInput = this.findViewById(R.id.searchInput);
            String RecordKeyword = RecordKeywordInput.getText().toString();
            ArrayList ProblemIds = new ArrayList<String>();
            ArrayList RecordIds = new ArrayList<String>();
            ArrayAdapter<String> RecordSearchadapter;


            for(int i = 0; i<Problems.size();i++){
                String tempProblemId = mDRS.GetProblemsForPatientId(PatientId).get(i).getId();
                String tempProblemTitle = mDRS.GetProblemsForPatientId(PatientId).get(i).getTitle();

                ArrayList PatientRecords = mDRS.GetPatientRecordsForProblem(tempProblemId);
               // ArrayList CareGiverRecords = mDRS.GetCareGiverRecordsForProblemId(tempProblemId);
                for(int j = 0; j<PatientRecords.size();j++){
                    PatientRecordTitle = mDRS.GetPatientRecordsForProblem(tempProblemId).get(j).getTitle();
                    PatientRecordDescription =  mDRS.GetPatientRecordsForProblem(tempProblemId).get(j).getDescription();
                    String tempRecordId = mDRS.GetPatientRecordsForProblem(tempProblemId).get(j).getId();

                    if(PatientRecordTitle.toLowerCase().contains(RecordKeyword.toLowerCase()) || PatientRecordDescription.toLowerCase().contains(RecordKeyword.toLowerCase())){
                        ProblemIds.add(tempProblemId);
                        RecordIds.add(tempRecordId);
                        recordSearchList.add(tempProblemTitle+" : "+PatientRecordTitle);
                    }
                }

            }
            for(int i = 0; i<Problems.size();i++){
                String tempProblemId = mDRS.GetProblemsForPatientId(PatientId).get(i).getId();

                //ArrayList PatientRecords = mDRS.GetPatientRecordsForProblem(tempProblemId);
                ArrayList CareGiverRecords = mDRS.GetCareGiverRecordsForProblemId(tempProblemId);
                for(int j = 0; j<CareGiverRecords.size();j++){
                    CareGiverComment = mDRS.GetCareGiverRecordsForProblemId(tempProblemId).get(j).getCareProviderComment();
                    String tempRecordId = mDRS.GetCareGiverRecordsForProblemId(tempProblemId).get(j).getId();

                    if(CareGiverComment.toLowerCase().contains(RecordKeyword.toLowerCase())){
                        ProblemIds.add(tempProblemId);
                        RecordIds.add(tempRecordId);
                        recordSearchList.add("Care Giver Comment :  "+CareGiverComment);
                    }
                }

            }
            //recordSearchList.add("headache");
            ListView listView = findViewById(R.id.searchRecordListView);

            RecordSearchadapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, recordSearchList);
            listView.setAdapter(RecordSearchadapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(searchActivity.this, viewRecordActivity.class);
                    intent.putExtra("problemId", ProblemIds.get(position).toString());
                    intent.putExtra("recordId",RecordIds.get(position).toString());

                    startActivity(intent);
                }
            });

        }


        else if(problemRadio.isChecked()){
            //searchmanager problemsearch by keyword
            ArrayList<String> problemSearchList = new ArrayList<>();

            String RecordTitle = new String();
            String RecordDescription = new String();
            String tempProblemId = new String();
            String tempRecordId = new String();
            String PatientId = new String();
            Intent intent = getIntent();
            ArrayList ProblemIds = new ArrayList<String>();
            ArrayList RecordIds = new ArrayList<String>();

            ArrayAdapter<String> ProblemSearchadapter;
            ArrayList Problems = new ArrayList<Problem>();
            EditText problemKeywordInput = this.findViewById(R.id.searchInput);
            String problemKeyword = problemKeywordInput.getText().toString();
            try{

                if (mDRS.GetUserMode() == ApplicationManager.UserMode.CareGiver){
                    PatientId = intent.getStringExtra("patientID");
                }


            }
            catch (DataRepositorySingleton.DataRepositorySingletonNotInitialized e)
            {
                Log.e("Search Activity", "DataRepositorySingleton not yet initialized. It is expected to be at this point");
            }

            try{

                PatientId = (mDRS.GetPatient().getId());

            }
            catch (DataRepositorySingleton.DataRepositorySingletonNotInitialized e)
            {
                Log.e("Search Activity", "DataRepositorySingleton not yet initialized. It is expected to be at this point");
            }
            catch (DataRepositorySingleton.InvalidUserMode e)
            {
                Log.e("Search Acivity", "Invalid User Mode");
            }
            Problems = mDRS.GetProblemsForPatientId(PatientId);

            for(int i = 0; i<Problems.size();i++){
                RecordTitle = mDRS.GetPatientRecordsForProblem(PatientId).get(i).getTitle();
                RecordDescription =  mDRS.GetPatientRecordsForProblem(PatientId).get(i).getDescription();
                tempRecordId = mDRS.GetPatientRecordsForProblem(PatientId).get(i).getId();

                if(RecordTitle.toLowerCase().contains(problemKeyword.toLowerCase()) || RecordDescription.toLowerCase().contains(problemKeyword.toLowerCase())){
                    ProblemIds.add(tempProblemId);
                    RecordIds.add(tempRecordId);
                    problemSearchList.add(RecordTitle);
                }

            }

            ListView listView = findViewById(R.id.searchProblemsListView);

            ProblemSearchadapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, problemSearchList);
            listView.setAdapter(ProblemSearchadapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(searchActivity.this, viewProblemActivity.class);
                    intent.putExtra("problemId", ProblemIds.get(position).toString());

                    try{
                        String RePatientId = (mDRS.GetPatient().getId());

                        intent.putExtra("patientID", RePatientId);

                    }
                    catch (DataRepositorySingleton.DataRepositorySingletonNotInitialized e)
                    {
                        Log.e("Search Activity", "DataRepositorySingleton not yet initialized. It is expected to be at this point");
                    }
                    catch (DataRepositorySingleton.InvalidUserMode e)
                    {
                        Log.e("Search Acivity", "Invalid User Mode");
                    }

                    startActivity(intent);
                }
            });
        }


        else{
            //searchmanager general
        }
        /** take results and populate listviews*/
    }

}

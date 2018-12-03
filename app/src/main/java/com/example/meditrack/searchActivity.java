package com.example.meditrack;

import android.content.Intent;
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
            //searchmanager recordsearch by keyword
        }


        else if(problemRadio.isChecked()){
            //searchmanager problemsearch by keyword
            ArrayList<String> problemSearchList = new ArrayList<>();

            String ProblemTitle = new String();
            String tempProblemId = new String();
            String PatientId = new String();
            Intent intent = getIntent();
            ArrayList ProblemIds = new ArrayList<String>();

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
                ProblemTitle = mDRS.GetProblemsForPatientId(PatientId).get(i).getTitle();
                tempProblemId = mDRS.GetProblemsForPatientId(PatientId).get(i).getId();

                if(ProblemTitle.toLowerCase().contains(problemKeyword.toLowerCase())){
                    ProblemIds.add(tempProblemId);
                    problemSearchList.add(ProblemTitle);
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

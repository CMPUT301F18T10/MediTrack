package com.example.meditrack;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class viewProblemActivity extends AppCompatActivity {
    private String selectedProblemId;
    private ArrayList<PatientRecord> patientRecordArrayList;
    private ArrayList<CareProviderRecord> careProviderRecordArrayList;
    Context mcontext;
    private ApplicationManager.UserMode userMode = ApplicationManager.UserMode.Invalid;
    private Problem problem;
    private EditText editTextTitle;
    private EditText editTextDes;
    private String problemTitle;
    private String problemDesc;
    private DataRepositorySingleton dataRepositorySingleton = DataRepositorySingleton.GetInstance();


    //Test String
    public Problem mockProblem = new Problem("TestTitle", "Test descpt", "ABC");
    public static final String testTitle = "Test Title";
    public static final String testDesc = "Test Description";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_problem);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editTextTitle = ((EditText)findViewById(R.id.problemViewTitle));
        editTextDes = ((EditText)findViewById(R.id.problemViewComment));
        ListView listView = findViewById(R.id.viewProblemListView);

        //Get problem Id from ProblemList Activity.
        Intent intent =getIntent();
        selectedProblemId = intent.getStringExtra("problemId");
        //Get problem from DRS;
        try {
            problem = dataRepositorySingleton.GetProblemForId(selectedProblemId);
            patientRecordArrayList = dataRepositorySingleton.GetPatientRecordsForProblem(selectedProblemId);
        } catch (ItemNotFound itemNotFound) {
            itemNotFound.printStackTrace();
        }

        //Get usermode from DRS;
        try {
            userMode = dataRepositorySingleton.GetUserMode();
        } catch (DataRepositorySingleton.DataRepositorySingletonNotInitialized dataRepositorySingletonNotInitialized) {
            dataRepositorySingletonNotInitialized.printStackTrace();
        }
        problemTitle = problem.getTitle();
        problemDesc = problem.getDescription();
        editTextTitle.setText(problemTitle);
        editTextDes.setText(problemDesc);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.viewProblemAddFAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PatientRecord patientRecord = new PatientRecord(selectedProblemId,"Default Title","Default descrip", new ArrayList<String>(),null,null);
                try{ ProblemManagerService.AddPatientRecord(patientRecord);}
                catch(Exception ObjectAlreadyExists){
                    System.err.println("ObjectAlreadyException Csught"+ObjectAlreadyExists);}
                ApplicationManager.UpdateDataRepository();
                Intent intent = new Intent(viewProblemActivity.this, viewRecordActivity.class);
                intent.putExtra("recordId",patientRecord.getId());
                intent.putExtra("problemId",selectedProblemId);
                startActivity(intent);

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecordsListAdapter adapter = new RecordsListAdapter(patientRecordArrayList,this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(viewProblemActivity.this, viewRecordActivity.class);
                intent.putExtra("recordId",patientRecordArrayList.get(position).getId());
                intent.putExtra("problemId",selectedProblemId);
                startActivity(intent);
            }
        });


    }


}




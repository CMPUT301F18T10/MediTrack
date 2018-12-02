package com.example.meditrack;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class viewProblemActivity extends AppCompatActivity {
    private String selectedProblemId;
    private ArrayList<PatientRecord> patientRecordArrayList;
    private ArrayList<CareProviderRecord> careProviderRecordArrayList;
    private ApplicationManager.UserMode mUserMode = ApplicationManager.UserMode.Invalid;
    private Problem currentProblem;
    private EditText editTextTitle;
    private EditText editTextDes;
    private String problemTitle;
    private String problemDesc;
    private DataRepositorySingleton dataRepositorySingleton = DataRepositorySingleton.GetInstance();


    //Test String
    public Problem mockProblem = new Problem("TestTitle", "Test descpt", "ABC");
    public static final String testTitle = "Test Title";
    public static final String testDesc = "Test Description";
    private static final String TAG = "SampleActivity";
    private static final boolean VERBOSE = true;
    private static final int CREATE_RECORD_REQUEST = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_problem);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ApplicationManager.UpdateDataRepository();
        setSupportActionBar(toolbar);

        editTextTitle = ((EditText)findViewById(R.id.problemViewTitle));
        editTextDes = ((EditText)findViewById(R.id.problemViewComment));
        ListView listView = findViewById(R.id.viewProblemListView);

        //Get problem Id from ProblemList Activity.
        Intent intent =getIntent();
        selectedProblemId = intent.getStringExtra("problemId");
        //Get problem from DRS;
        dataRepositorySingleton.RefreshDataRepositorySingleton();
        try {
            currentProblem = dataRepositorySingleton.GetProblemForId(selectedProblemId);
            patientRecordArrayList = dataRepositorySingleton.GetPatientRecordsForProblem(selectedProblemId);
        } catch (ItemNotFound itemNotFound) {
            itemNotFound.printStackTrace();
        }

        //Get usermode from DRS;
        try {
            mUserMode = dataRepositorySingleton.GetUserMode();
        } catch (DataRepositorySingleton.DataRepositorySingletonNotInitialized dataRepositorySingletonNotInitialized) {
            dataRepositorySingletonNotInitialized.printStackTrace();
        }

        problemTitle = currentProblem.getTitle();
        problemDesc = currentProblem.getDescription();
        editTextTitle.setText(problemTitle);
        editTextDes.setText(problemDesc);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.viewProblemAddFAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //SaveChangesOnDRS();
                //ShowChangesOnEditText();

                Intent intent = new Intent(viewProblemActivity.this , CreatePatientRecordActivity.class);
                intent.putExtra(PROBLEM_ID_EXTRA, selectedProblemId);
                startActivityForResult(intent, CREATE_RECORD_REQUEST);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecordsListAdapter adapter = new RecordsListAdapter(patientRecordArrayList,this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentProblem.setTitle(editTextTitle.getText().toString());
                currentProblem.setDescription(editTextDes.getText().toString());
                try {
                    ProblemManagerService.EditProblem(currentProblem);
                } catch (ProblemManagerService.ObjectNotFound objectNotFound) {
                    objectNotFound.printStackTrace();
                }
                ApplicationManager.UpdateDataRepository();
                Intent intent = new Intent(viewProblemActivity.this, viewRecordActivity.class);
                intent.putExtra("recordId",patientRecordArrayList.get(position).getId());
                intent.putExtra("problemId",selectedProblemId);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CREATE_RECORD_REQUEST && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            PatientRecord patientRecord = (PatientRecord) extras.get(CreatePatientRecordActivity.KEY);

            try{ ProblemManagerService.AddPatientRecord(patientRecord);}
            catch(Exception ObjectAlreadyExists){
                System.err.println("ObjectAlreadyException Caught"+ObjectAlreadyExists);}
            ApplicationManager.UpdateDataRepository();
        }
    }
}




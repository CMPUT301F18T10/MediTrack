package com.example.meditrack;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
    public Problem mockProblem = new Problem("TestTitle", "Test descpt", "ABC");
    public static final String testTitle = "Test Title";
    public static final String testDesc = "Test Description";
    public class RecordListAdapter extends ArrayAdapter<PatientRecord> {
        private ArrayList<PatientRecord> patientRecords;
        Context context;

        public RecordListAdapter(ArrayList<PatientRecord> patientRecords, Context context){
            super(context, R.layout.problem_list_row_item,patientRecords);
            patientRecordArrayList = patientRecords;
            mcontext = context;
        }
        @Override
        public View getView(int postion, View covertView, ViewGroup parent){


            PatientRecord patientRecord = patientRecordArrayList.get(postion);
            LayoutInflater layoutInflater = (LayoutInflater) mcontext.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            View rowView = layoutInflater.inflate(R.layout.problem_list_row_item,parent,false);

            TextView recordTitle = (TextView) rowView.findViewById(R.id.problemTitle);
            TextView recordDecp = (TextView) rowView.findViewById(R.id.problemDescription);
            TextView recordTime = (TextView) rowView.findViewById(R.id.timeStamp);

            recordTitle.setText(patientRecord.getTitle().toString());
            recordDecp.setText(patientRecord.getDescription().toString());
            recordTime.setText(patientRecord.getTimestamp().toString());

            return rowView;
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_problem);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Get problem Id from ProblemList Activity.
        Intent intent =getIntent();
        selectedProblemId = intent.getStringExtra("problemId");

        //Get problem from DRS;
        try {
            problem = dataRepositorySingleton.GetProblemForId(selectedProblemId);
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

        editTextTitle = ((EditText)findViewById(R.id.problemViewTitle));
        editTextDes = ((EditText)findViewById(R.id.problemViewComment));

        editTextTitle.setText(problemTitle);
        editTextDes.setText(problemDesc);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.viewProblemAddFAB);
        View fabButtonView = findViewById(R.id.viewProblemAddFAB);
        if (!ApplicationManager.IsFeatureAllowed("AddRecord",userMode)) fabButtonView.setVisibility(View.GONE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PatientRecord patientRecord = new PatientRecord(selectedProblemId,"Test title","Test descp", new ArrayList<String>(),null,null);
                String recordId = patientRecord.getId();
                try{
                    ProblemManagerService.AddPatientRecord(patientRecord);}
                catch(Exception ObjectAlreadyExists){
                    System.err.println("ObjectAlreadyException Csught"+ObjectAlreadyExists);}
                ApplicationManager.UpdateDataRepository();
                Intent intent = new Intent(viewProblemActivity.this, viewRecordActivity.class);
                intent.putExtra("recordId",recordId);
                startActivity(intent);

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ListView listView = findViewById(R.id.viewProblemListView);
        RecordListAdapter adapter = new RecordListAdapter(patientRecordArrayList,this);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(viewProblemActivity.this, viewRecordActivity.class);
                intent.putExtra("recordId",patientRecordArrayList.get(position).getId());
                intent.putExtra("problemId",problem.getId());
                startActivity(intent);
            }
        });
    }
    public void SaveEditedProblem(View view) {
                problem.setTitle(editTextTitle.getText().toString());
                problem.setDescription(editTextDes.getText().toString());
                dataRepositorySingleton.EditProblem(problem);
        }

    }




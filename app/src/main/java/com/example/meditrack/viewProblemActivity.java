package com.example.meditrack;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class viewProblemActivity extends AppCompatActivity {
    private String selectedProblemId;
    private ArrayList<PatientRecord> patientRecordArrayList;
    private ArrayList<CareProviderRecord> careProviderRecordArrayList;
    private String mUserID;
    private Problem currentProblem;
    private String problemTitle;
    private String problemDesc;
    private String newComment;

    //Declare for service units
    private ApplicationManager.UserMode mUserMode = ApplicationManager.UserMode.Invalid;
    private DataRepositorySingleton dataRepositorySingleton = DataRepositorySingleton.GetInstance();
    private CareProviderRecordListAdapter cAdapter;
    private RecordsListAdapter pAdapter;

    //Declare for UI units
    private EditText editTextTitle;
    private EditText editTextDes;
    private ListView recordsListView;
    private FloatingActionButton customFAButton;
    private Button deleteButton;
    private FloatingActionButton viewCommentsFAButton;




    //Test String


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
        recordsListView = (ListView)findViewById(R.id.viewProblemListView);
        customFAButton = (FloatingActionButton) findViewById(R.id.viewProblemAddFAB);
        deleteButton = (Button) findViewById(R.id.viewProblemDeleteProblemButton);
        viewCommentsFAButton = (FloatingActionButton) findViewById(R.id.viewProblemViewCommentsFAB);

        //Get problem Id from ProblemList Activity.
        Intent intent =getIntent();
        selectedProblemId = intent.getStringExtra("problemId");
        mUserID = intent.getStringExtra("patientID");
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


        switch (mUserMode){
            case Patient:
                customFAButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(viewProblemActivity.this , CreatePatientRecordActivity.class);
                        intent.putExtra("PROBLEM_ID_EXTRA", selectedProblemId);
                        startActivityForResult(intent, CREATE_RECORD_REQUEST);
                        finish();


                    }
                });
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                recordsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            ProblemManagerService.DeleteProblem(selectedProblemId);
                        } catch (ProblemManagerService.ObjectNotFound objectNotFound) {
                            objectNotFound.printStackTrace();
                        }
                        ApplicationManager.UpdateDataRepository();
                        Intent intent = new Intent(viewProblemActivity.this, ProblemsListActivity.class);
                        intent.putExtra("patientID", mUserID);
                        startActivity(intent);
                        finish();
                    }
                });
                viewCommentsFAButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder commmentDisplayBuilder = new AlertDialog.Builder(viewProblemActivity.this);
                        commmentDisplayBuilder.setTitle("Comment:");
                        commmentDisplayBuilder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        commmentDisplayBuilder.setAdapter(cAdapter, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }

                        });
                        commmentDisplayBuilder.show();
                    }
                });

                break;
            case CareGiver:
                deleteButton.setVisibility(View.INVISIBLE);
                editTextTitle.setFocusable(false);
                editTextDes.setFocusable(false);

                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                recordsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                //Add Comment to The problem
                customFAButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(viewProblemActivity.this);
                        builder.setTitle("Comment:");
                        View viewInflated = LayoutInflater.from(viewProblemActivity.this).inflate(R.layout.item_popio,findViewById(android.R.id.content), false);
                        final EditText input = (EditText) viewInflated.findViewById(R.id.input);
                        builder.setView(viewInflated);
                        input.setHint("Add Your New Comment:");
                        input.setInputType(InputType.TYPE_CLASS_TEXT);
                        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                newComment = input.getText().toString();
                                CareProviderRecord careProviderRecord = new CareProviderRecord(selectedProblemId,newComment,mUserID);
                                try {
                                    ProblemManagerService.AddCareProviderRecord(careProviderRecord);
                                    Toast.makeText(viewProblemActivity.this, "Add New Comment Successfully",Toast.LENGTH_LONG).show();
                                } catch (ProblemManagerService.ObjectIdAlreadyExists objectIdAlreadyExists) {
                                    objectIdAlreadyExists.printStackTrace();
                                }
                                dialog.dismiss();
                                ApplicationManager.UpdateDataRepository();
                                onStart();
                            }
                        });
                        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder.show();




                    }
                });
                viewCommentsFAButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder commmentDisplayBuilder = new AlertDialog.Builder(viewProblemActivity.this);
                        commmentDisplayBuilder.setTitle("Comment:");
                        commmentDisplayBuilder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        commmentDisplayBuilder.setAdapter(cAdapter, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }

                        });
                        commmentDisplayBuilder.show();
                    }
                });
                break;
            case Invalid:
                break;
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        patientRecordArrayList = dataRepositorySingleton.GetPatientRecordsForProblem(selectedProblemId);
        careProviderRecordArrayList =dataRepositorySingleton.GetCareGiverRecordsForProblemId(selectedProblemId);
        cAdapter = new CareProviderRecordListAdapter(careProviderRecordArrayList,viewProblemActivity.this);
        pAdapter = new RecordsListAdapter(patientRecordArrayList,viewProblemActivity.this);
        recordsListView.setAdapter(pAdapter);
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




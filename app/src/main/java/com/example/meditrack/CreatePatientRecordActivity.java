package com.example.meditrack;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class CreatePatientRecordActivity extends AppCompatActivity {

    // Use this key on calling activity to retrieve the PatientRecord
    public final String KEY = "PATIENT_RECORD";

    private PatientRecord patientRecord;

    private EditText titleEditText;
    private EditText commentEditText;

    private String title = "";
    private String comment = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_record);

        // Initialize toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Obtain problemId from Intent
        Intent intent = getIntent();
        String problemId = intent.getStringExtra("PROBLEM_ID");

        // TODO: Remove this dummy problemId
        problemId = "problemId";

        // Create a default record
        patientRecord = new PatientRecord(problemId);

        // Obtain UI components
        titleEditText = this.findViewById(R.id.createRecordTitle);
        commentEditText = this.findViewById(R.id.createRecordComment);
    }

    public void createRecordBrowse() {

    }
    public void createRecordAddPhoto(){

    }
    public void createRecordAddGPS() {

    }
    public void createRecordAddBody(){

    }

    public void createRecordFinish(View view) {
        // Obtain all information from UI
        title = titleEditText.getText().toString();
        comment = commentEditText.getText().toString();

        // TODO: Maybe check user input before returning
        returnRecord();
    }

    public void returnRecord() {
        // Finalize the return object
        patientRecord.setTitle(title);
        patientRecord.setDescription(comment);

        Intent result = new Intent();
        result.putExtra(KEY , patientRecord);
        setResult(RESULT_OK, result);
        finish();
    }
}

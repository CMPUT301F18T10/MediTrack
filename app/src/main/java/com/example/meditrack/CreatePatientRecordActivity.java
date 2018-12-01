package com.example.meditrack;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class CreatePatientRecordActivity extends AppCompatActivity {

    // Use this key on calling activity to retrieve the PatientRecord
    public final String KEY = "PATIENT_RECORD";
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private PatientRecord patientRecord;

    private EditText titleEditText;
    private EditText commentEditText;

    private ElasticsearchManager esm = new ElasticsearchManager();
    private String title = "";
    private String comment = "";
    private ArrayList<RecordImage> recordImages = new ArrayList<>();

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            recordImages.add(new RecordImage((Bitmap) extras.get("data"), patientRecord.getId()));
        }
    }

    public void createRecordBrowse() {

    }
    public void takePhoto(View view) {
        // TODO: Getting the full-sized image may be worthwhile.
        // ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, PERMISSION_GRANTED);
        // Android tutorial found at： https://developer.android.com/training/camera/photobasics#java
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }

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

        // Upload all recordImages
        // TODO: Maybe use DataRepositorySingleton instead?
        try {
            esm.addObjects(recordImages);
        } catch (Exception e) {
            // TODO: Add error handling
        }

        // Finalize the return object
        patientRecord.setTitle(title);
        patientRecord.setDescription(comment);

        Intent result = new Intent();
        result.putExtra(KEY , patientRecord);
        setResult(RESULT_OK, result);
        finish();
    }
}

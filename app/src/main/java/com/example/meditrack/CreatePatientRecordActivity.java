package com.example.meditrack;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class CreatePatientRecordActivity extends AppCompatActivity {

    // Use this key on calling activity to retrieve the PatientRecord
    public static final String KEY = "PATIENT_RECORD";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int PICK_IMAGE = 2;

    private PatientRecord patientRecord;

    private EditText titleEditText;
    private EditText commentEditText;

    private ElasticsearchManager esm = new ElasticsearchManager();
    private String title = "";
    private String comment = "";
    private ArrayList<RecordImage> recordImages = new ArrayList<>();
    private ArrayAdapter<RecordImage> recordImageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_record);

        // Initialize toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView listview = findViewById(R.id.test_list_view);
        recordImageAdapter = new ArrayAdapter<>(this, R.layout.image_list_row_item, R.id.image_list_row_item_text_view, recordImages);
        listview.setAdapter(recordImageAdapter);

        // Obtain problemId from Intent
        Intent intent = getIntent();
        String problemId = intent.getStringExtra(viewProblemActivity.PROBLEM_ID_EXTRA);

        //// TODO: Remove this dummy problemId
        //problemId = "problemId";

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
            recordImageAdapter.notifyDataSetChanged();
        }

        else if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            try {
                // Mark Ingram (986) : https://stackoverflow.com/questions/3879992/how-to-get-bitmap-from-an-uri
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                recordImages.add(new RecordImage(bitmap, patientRecord.getId()));
                recordImageAdapter.notifyDataSetChanged();
            } catch (java.io.IOException e) {
                Log.i("Meditrack", "IOException!!");
            }
        }
    }

    public void selectImage(View view) {
        // JMRboosties (588758) and Gabriel Checchia Vitali (6231562): https://stackoverflow.com/questions/5309190/android-pick-images-from-gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        if(intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
        }
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

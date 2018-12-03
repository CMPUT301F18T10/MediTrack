package com.example.meditrack;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class viewRecordActivity extends AppCompatActivity {

    private String mRecordId;
    private String mProblemIdForRecord;
    private PatientRecord mPatientRecord; // we only display PatientRecords in this activity
    private DataRepositorySingleton mDRS;
    private static final String tag = "ViewRecordActivity";
    private Toast mImageToast;
    private Toast mBodyLocationToast;
    private Toast mGPSToast;
    private ArrayList<RecordImage> recordImages;
    private ImageView image;
    private int currentImageIndex = 0;

    private void ReturnToViewProblemActivity()
    {
        Intent intent = new Intent(viewRecordActivity.this, viewProblemActivity.class);
        intent.putExtra("problemId",mProblemIdForRecord);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_record);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ApplicationManager.UpdateDataRepository();

        mDRS = DataRepositorySingleton.GetInstance();
        Intent intent = getIntent();
        mRecordId = intent.getStringExtra("recordId");
        mProblemIdForRecord = intent.getStringExtra("problemId");
        try { mPatientRecord = mDRS.GetPatientRecordForId(mRecordId); }
        catch(ItemNotFound e)
        {
             Log.e(tag, "ViewRecord Activity launched with non-existent record Id: " + mRecordId);
             ReturnToViewProblemActivity();
        }

        TextView recordTitle = (TextView) findViewById(R.id.viewRecordTitle);
        recordTitle.setText(mPatientRecord.getTitle());
        TextView recordDescription = (TextView) findViewById(R.id.viewRecordComment);
        recordDescription.setText(mPatientRecord.getDescription());
        TextView recordTimeStamp = (TextView) findViewById(R.id.viewRecordTimeStamp);
        recordTimeStamp.setText(mPatientRecord.getTimestamp().toString());

        // Creating toasts for unimplemented functionality
        Context context = getApplicationContext();
        CharSequence imageToastText = "No more images to show!";
        CharSequence bodyLocationToastText = "Body Location functionality will be provided in a future version";
        CharSequence viewGPSToastText = "GPS functionality will be provided in a future version";
        int duration = Toast.LENGTH_SHORT;
        mImageToast = Toast.makeText(context, imageToastText, duration);
        mBodyLocationToast = Toast.makeText(context, bodyLocationToastText, duration);
        mGPSToast = Toast.makeText(context, viewGPSToastText, duration);

        image = (ImageView) findViewById(R.id.viewRecordPhoto);
        try {
            recordImages = mDRS.GetRecordImagesForRecordId(mRecordId);
        } catch (ElasticsearchManager.OperationFailedException e) {
            recordImages = new ArrayList<>();
        }
        if (recordImages.size() > 0) {
            image.setImageBitmap(recordImages.get(currentImageIndex).getBitmap());
            currentImageIndex = (currentImageIndex + 1) % recordImages.size();
        }
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onImageClick(view);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.deleteRecordButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{ ProblemManagerService.DeletePatientRecord(mPatientRecord.getProblemId(), mPatientRecord.getId()); }
                catch (ProblemManagerService.ObjectNotFound e)
                {
                    Log.e(tag, "Attempted to delete a Patient Record that no longer exists");
                }
                ReturnToViewProblemActivity();
            }
        });

    }

    public void onImageClick(View v)
    {
        if ( recordImages != null && recordImages.size() > 1) {
            image.setImageBitmap(recordImages.get(currentImageIndex).getBitmap());
            currentImageIndex = (currentImageIndex + 1) % recordImages.size();
        } else {
            mImageToast.show();
        }
    }

    public void onBodyLocationButtonClick(View v)
    {
        mBodyLocationToast.show();
    }

    public void onViewGPSButtonClick(View v)
    {
        mGPSToast.show();
    }

}

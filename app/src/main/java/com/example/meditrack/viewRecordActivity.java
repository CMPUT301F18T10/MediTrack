package com.example.meditrack;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class viewRecordActivity extends AppCompatActivity {

    String mRecordId;
    PatientRecord mPatientRecord; // we only display PatientRecords in this activity
    DataRepositorySingleton mDRS;

    private void ReturnToViewProblemAcitivty()
    {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_record);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ApplicationManager.UpdateDataRepository();
        mDRS = DataRepositorySingleton.GetInstance();
        Intent intent = getIntent();
        mRecordId = intent.getStringExtra("recordId");
        try { mDRS.GetPatientRecordForId(mRecordId); }
        catch(ItemNotFound e)
        {
            // TODO: return to ViewProblemActivity
        }


        // TODO: Delete the record and launch the viewProblem with the ProblemId
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.deleteRecordButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

}

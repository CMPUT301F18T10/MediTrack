package com.example.meditrack;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ProblemsListActivity extends AppCompatActivity {

    ArrayList<Problem> mProblemList;
    String mPatientId;
    DataRepositorySingleton mDRS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problems_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        mPatientId = intent.getStringExtra("patientID");
        mDRS = DataRepositorySingleton.GetInstance();
        mProblemList = mDRS.GetProblemsForPatientId(mPatientId);

        FloatingActionButton addFAB = (FloatingActionButton) findViewById(R.id.problemsListAddFAB);
        addFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** !make this available to patient only
                 * takes user to problem activity with
                 */
                Intent intent = new Intent(ProblemsListActivity.this, viewProblemActivity.class);
                intent.putExtra("Item Number", -1); /** not sure best way to do this. For now, i think it would be ok to have -1 mean default values*/
                startActivity(intent);
            }
        });
        ListView listView = findViewById(R.id.problemsListListView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ProblemsListActivity.this, viewProblemActivity.class);
                intent.putExtra("Item Number", position); /** passes the position on the list to next activity*/
                startActivity(intent);
            }
        });
        FloatingActionButton searchFAB = (FloatingActionButton) findViewById(R.id.problemsListSearchFAB);
        searchFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchIntent = new Intent(ProblemsListActivity.this, searchActivity.class);
                startActivity(searchIntent);
            }
        });
        FloatingActionButton profileFAB = (FloatingActionButton) findViewById(R.id.problemsListProfileFAB);
        profileFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profileIntent = new Intent(ProblemsListActivity.this, ProfileInformationActivity.class);
                profileIntent.putExtra("Patient ID", "patientID"); //replace with actual patient id when that's created
                startActivity(profileIntent);
            }
        });

    }

}

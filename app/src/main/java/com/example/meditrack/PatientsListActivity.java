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

public class PatientsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.patientsListAddFAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** this should create alertdialog with text input https://stackoverflow.com/questions/10903754/input-text-dialog-android
                 * the input should be matched with patient name and then added to the list if patient id exists, otherwise toast invalid patient
                 * then refresh the list to include new patient if successful
                 */
                //Intent intent = new Intent(PatientsListActivity.this, ProblemsListActivity.class);
                //startActivity(intent);
            }
        });
        ListView listView = findViewById(R.id.patientsListListView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /** this should open problemslistactivity in caretaker mode with that patient's info
                 */
                Intent intent = new Intent(PatientsListActivity.this, ProblemsListActivity.class);
                startActivity(intent);
            }
        });
    }

}

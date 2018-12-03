package com.example.meditrack;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PatientsListActivity extends AppCompatActivity {
    private String patientUserName = "";
    private String patientId = "";
    private String careTakerId = "";
    private ElasticsearchManager mESM;
    ArrayList<String> patientList = new ArrayList<>();
    ArrayAdapter<String> Patientadapter;
    private DataRepositorySingleton mDRS = DataRepositorySingleton.GetInstance();
    private static final String tag = "PatientsListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        mDRS = DataRepositorySingleton.GetInstance();
        careTakerId = intent.getStringExtra("caretakerID");

        if (careTakerId == null)
        {
            try{ careTakerId = mDRS.GetStoredIntent(PatientsListActivity.class); }
            catch (DataRepositorySingleton.IntentMissingException e) { Log.e(tag, "No active or stored intent found"); }
        }
        else
        {
            mDRS.AddIntent(PatientsListActivity.class, careTakerId);
        }

        try{

            patientList = mDRS.GetCareProvider().getPatientIds();
        }
        catch (DataRepositorySingleton.DataRepositorySingletonNotInitialized e)
        {
            Log.e("Operation failed", "DataRepositorySingleton not yet initialized. It is expected to be at this point");
        }
        catch (DataRepositorySingleton.InvalidUserMode e)
        {
            Log.e("Operation failed", "Invalid User Mode");
        }
        TextView PatientListActivityTitle = (TextView) findViewById(R.id.patientListCaretakerName);
        PatientListActivityTitle.setText(careTakerId);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.patientsListAddFAB);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PatientsListActivity.this);
                builder.setTitle("Add Patient Id");
                final EditText input = new EditText(getApplicationContext());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        patientId=input.getText().toString();
                        Toast.makeText(getApplicationContext(), patientId+" added", Toast.LENGTH_SHORT).show();
                        patientList.add(patientId);
                        try{
                            CareProvider careProvider = mDRS.GetCareProvider();
                            careProvider.AddPatientId(patientId);
                            mDRS.EditCareProvider(careProvider);

                            ApplicationManager.UpdateDataRepository();
                            patientList = mDRS.GetCareProvider().getPatientIds();

                        }
                        catch (DataRepositorySingleton.DataRepositorySingletonNotInitialized e)
                        {
                            Log.e("Operation failed", "DataRepositorySingleton not yet initialized. It is expected to be at this point");
                        }
                        catch (DataRepositorySingleton.InvalidUserMode e)
                        {
                            Log.e("Operation failed", "Invalid User Mode");
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        ListView listView = findViewById(R.id.patientsListListView);

        Patientadapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,  patientList);
        listView.setAdapter(Patientadapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(PatientsListActivity.this, ProblemsListActivity.class);
                // provide the patient Id for ProblemsListActivity
                intent.putExtra("patientID", patientList.get(position));
                startActivity(intent);
            }
        });
    }

}

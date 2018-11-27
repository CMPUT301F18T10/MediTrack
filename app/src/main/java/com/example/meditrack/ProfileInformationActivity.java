package com.example.meditrack;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class ProfileInformationActivity extends AppCompatActivity {
    private String CaretakerAdd;
    private ArrayList<String> cpIds = null;
    private DataRepositorySingleton dataRepositorySingleton = DataRepositorySingleton.GetInstance();
    private ApplicationManager.UserMode mode;
    private Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_information);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ListView caretakerList = findViewById(R.id.profileCaretakerListListView);

        Intent intent = getIntent();
        String patientID = intent.getStringExtra("patientID");

        try {
            patient = dataRepositorySingleton.GetPatientForId(patientID);
        } catch (ItemNotFound itemNotFound) {
            itemNotFound.printStackTrace();
        }

        EditText phoneNumberEdit = (EditText) findViewById(R.id.profilePhoneInput);
        EditText emailAddressEdit = (EditText) findViewById(R.id.profileEmailInput);
        EditText id = (EditText)findViewById(R.id.profileUserIDInput);
        id.setText(patientID);
        phoneNumberEdit.setText(patient.getContactInfo().getPhoneNumber());
        emailAddressEdit.setText(patient.getContactInfo().getEmail());

        cpIds = patient.getCareProviderId();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,cpIds);
        caretakerList.setAdapter(adapter);

        caretakerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // remove item from data and update listView
                AlertDialog.Builder adb = new AlertDialog.Builder(getApplicationContext());
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to delete " + position);
                final int pos = position;
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        cpIds.remove(pos);
                        patient.setCareProviderId(cpIds);
                        adapter.notifyDataSetChanged();
                    }});
                adb.show();

            }
        });
        ListView imageList = findViewById(R.id.profileImageListListView);
        imageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // remove and update

            }
        });

    }
    public void addBodyImage(View v){
        // same as uploading pictures of record, implement in later part
    }

    public void addCaretaker(View v) {
        /*AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        // actually not sure if this is required or not, we can probably skip it
        builder.setTitle("Add Caretaker");
        final EditText input = new EditText(getApplicationContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        ListView caretakerList = findViewById(R.id.profileCaretakerListListView);
        ArrayList<String> cpIds;

        Intent intent = getIntent();
        String patientID = intent.getStringExtra("patientID");
        try {
            patient = dataRepositorySingleton.GetPatientForId(patientID);
        } catch (ItemNotFound itemNotFound) {
            itemNotFound.printStackTrace();
        }
        cpIds = patient.getCareProviderId();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,cpIds);
        caretakerList.setAdapter(adapter);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CaretakerAdd = input.getText().toString();
                // check if caretaker exists, if so, add this patient to caretaker list

                ApplicationManager.UserMode userMode = ApplicationManager.UserMode.CareGiver;
                ApplicationManager applicationManager = new ApplicationManager(userMode);
                if(applicationManager.DoesUserExist(CaretakerAdd) == true){
                    cpIds.add(CaretakerAdd);
                    patient.setCareProviderId(cpIds);
                    adapter.notifyDataSetChanged();
                };
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });*/
    }
    public void profileSave(View v) throws DataRepositorySingleton.DataRepositorySingletonNotInitialized, DataRepositorySingleton.InvalidUserMode {

        EditText phoneNumberEdit = (EditText) findViewById(R.id.profilePhoneInput);
        EditText emailAddressEdit = (EditText) findViewById(R.id.profileEmailInput);
        String phoneNumber = phoneNumberEdit.getText().toString();
        String emailAddress = emailAddressEdit.getText().toString();
        ContactInfo contactInfo = new ContactInfo(emailAddress,phoneNumber);
        // update these values data repository
        if(dataRepositorySingleton.GetUserMode() == ApplicationManager.UserMode.Patient){

            Button saveButton = findViewById(R.id.profileSaveButton);
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = getIntent();
                    String patientID = intent.getStringExtra("patientID");
                    try {
                        patient = dataRepositorySingleton.GetPatientForId(patientID); //works
                    } catch (ItemNotFound itemNotFound) {
                        itemNotFound.printStackTrace();
                    }

                    patient.setContactInfo(contactInfo);
                    try {
                        DataRepositorySingleton.GetInstance().EditPatient(patient); //works
                    } catch (DataRepositorySingleton.InvalidUserMode invalidUserMode) {
                        invalidUserMode.printStackTrace();
                    }
                    DataRepositorySingleton.GetInstance().RefreshDataRepositorySingleton();//not work

                    //Patient patientTest = null;     // test case
                    //EditText edT = findViewById(R.id.profileUserIDInput);
                    //try {
                    //   patientTest = dataRepositorySingleton.GetPatientForId(patientID);
                    //} catch (ItemNotFound itemNotFound) {
                    //    itemNotFound.printStackTrace();
                    //}

                    //edT.setText(patientTest.getContactInfo().getPhoneNumber());
                }
            });
        }

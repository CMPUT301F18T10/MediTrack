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
import android.widget.Toast;

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
        EditText id = (EditText) findViewById(R.id.profileUserIDInput);
        id.setText(patientID);
        phoneNumberEdit.setText(patient.getContactInfo().getPhoneNumber());
        emailAddressEdit.setText(patient.getContactInfo().getEmail());

    }


    public void profileSave(View v) throws DataRepositorySingleton.DataRepositorySingletonNotInitialized, DataRepositorySingleton.InvalidUserMode
    {
        EditText phoneNumberEdit = (EditText) findViewById(R.id.profilePhoneInput);
        EditText emailAddressEdit = (EditText) findViewById(R.id.profileEmailInput);
        String phoneNumber = phoneNumberEdit.getText().toString();
        String emailAddress = emailAddressEdit.getText().toString();
        ContactInfo contactInfo = new ContactInfo(emailAddress, phoneNumber);
        // update these values data repository
        if (dataRepositorySingleton.GetUserMode() == ApplicationManager.UserMode.Patient)
        {

            if (dataRepositorySingleton.GetPatient().getId().equals(patient.getId()))
            {
                patient.setContactInfo(contactInfo);
                dataRepositorySingleton.EditPatient(patient);
                ApplicationManager.UpdateDataRepository();
            }
            else { DenyAndRefresh(); }
        }
        else { DenyAndRefresh(); }
    }

    private void DenyAndRefresh()
    {
        Toast.makeText(this, "You don't have permission to edit profile information for this account",Toast.LENGTH_LONG).show();

        // refresh edit text
        EditText phoneNumberEdit = (EditText) findViewById(R.id.profilePhoneInput);
        EditText emailAddressEdit = (EditText) findViewById(R.id.profileEmailInput);
        phoneNumberEdit.setText(patient.getContactInfo().getPhoneNumber());
        emailAddressEdit.setText(patient.getContactInfo().getEmail());
    }
}

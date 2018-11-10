package com.example.meditrack;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import static com.example.meditrack.ApplicationManager.UserMode.CareGiver;

public class loginActivity extends AppCompatActivity {
    EditText userEmailEdit = (EditText) findViewById(R.id.loginEmail);
    String userEmail = userEmailEdit.getText().toString();
    RadioButton caretakerRadio = (RadioButton) findViewById(R.id.caretakerRadioButton);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }
    public void loginClick(View v){
        if(caretakerRadio.isChecked()){
            Intent caretakerIntent = new Intent(loginActivity.this, PatientsListActivity.class);
            //caretakerIntent.putExtra("caretakerID", userEmail);
            ApplicationManager.LogIn(CareGiver, userEmail);
            startActivity(caretakerIntent);


        }
        else{
            Intent patientIntent = new Intent(loginActivity.this, ProblemsListActivity.class);
            //patientIntent.putExtra("patientID", userEmail);
            ApplicationManager.LogIn(ApplicationManager.UserMode.Patient, userEmail);
            startActivity(patientIntent);
        }

    }

}

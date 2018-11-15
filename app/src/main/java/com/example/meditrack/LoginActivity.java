package com.example.meditrack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import static com.example.meditrack.ApplicationManager.UserMode.CareGiver;

public class LoginActivity extends AppCompatActivity {

    EditText userEmailEdit;
    String userEmail;
    RadioButton caretakerRadio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText userEmailEdit = (EditText) findViewById(R.id.loginEmail);
        String userEmail = userEmailEdit.getText().toString();
        RadioButton caretakerRadio = (RadioButton) findViewById(R.id.caretakerRadioButton);

    }
    public void loginClick(View v){
        if(caretakerRadio.isChecked()){
            Intent caretakerIntent = new Intent(LoginActivity.this, PatientsListActivity.class);
            //caretakerIntent.putExtra("caretakerID", userEmail);
            ApplicationManager.LogIn(CareGiver, userEmail);
            startActivity(caretakerIntent);
        }
        else{
            Intent patientIntent = new Intent(LoginActivity.this, ProblemsListActivity.class);
            //patientIntent.putExtra("patientID", userEmail);
            ApplicationManager.LogIn(ApplicationManager.UserMode.Patient, userEmail);
            startActivity(patientIntent);
        }

    }

}

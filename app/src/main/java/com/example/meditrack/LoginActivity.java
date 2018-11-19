package com.example.meditrack;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import static com.example.meditrack.ApplicationManager.UserMode.CareGiver;

public class LoginActivity extends AppCompatActivity {

    EditText mUserEmailEdit;
    String mUserEmail;
    RadioButton mCaretakerRadio;
    ApplicationManager mApplicationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    public void loginClick(View v){

        mUserEmailEdit = (EditText) findViewById(R.id.loginEmail);
        mUserEmail = mUserEmailEdit.getText().toString();
        mCaretakerRadio = (RadioButton) findViewById(R.id.caretakerRadioButton);

        // TODO: We need exception handling at this stage
        if(mCaretakerRadio.isChecked()){
            mApplicationManager = new ApplicationManager(ApplicationManager.UserMode.CareGiver);
            if (mApplicationManager.DoesUserExist(mUserEmail))
            {
                // Log in
                mApplicationManager.LogIn(mUserEmail, DataRepositorySingleton.GetInstance());
            }
            else
            {
                // Register user and then log in
                mApplicationManager.RegisterUser(mUserEmail);
                mApplicationManager.LogIn(mUserEmail, DataRepositorySingleton.GetInstance());
            }
            Intent caretakerIntent = new Intent(LoginActivity.this, PatientsListActivity.class);
            caretakerIntent.putExtra("caretakerID", mUserEmail);
            startActivity(caretakerIntent);
        }
        else{
            mApplicationManager = new ApplicationManager(ApplicationManager.UserMode.Patient);
            if(mApplicationManager.DoesUserExist(mUserEmail))
            {
                // Log in
                mApplicationManager.LogIn(mUserEmail, DataRepositorySingleton.GetInstance());
            }
            else
            {
                // Register user and then log in
                mApplicationManager.RegisterUser(mUserEmail);
                mApplicationManager.LogIn(mUserEmail, DataRepositorySingleton.GetInstance());
            }
            Intent patientIntent = new Intent(LoginActivity.this, ProblemsListActivity.class);
            patientIntent.putExtra("patientID", mUserEmail);
            startActivity(patientIntent);
        }

    }

}

package com.example.meditrack;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

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
            {   // Log in
                mApplicationManager.LogIn(mUserEmail, DataRepositorySingleton.GetInstance());
                Intent caretakerIntent = new Intent(LoginActivity.this, PatientsListActivity.class);
                caretakerIntent.putExtra("caretakerID", mUserEmail);
                startActivity(caretakerIntent);
            }
            else{
                Toast.makeText(this, "This User Email Is Not Registered, Please Register it!",Toast.LENGTH_LONG).show();
            }


        }
        else{
            mApplicationManager = new ApplicationManager(ApplicationManager.UserMode.Patient);
            if(mApplicationManager.DoesUserExist(mUserEmail))
            {
                // Log in
                mApplicationManager.LogIn(mUserEmail, DataRepositorySingleton.GetInstance());
                Intent caretakerIntent = new Intent(LoginActivity.this,ProblemsListActivity.class);
                caretakerIntent.putExtra("patientID", mUserEmail);
                startActivity(caretakerIntent);
            }

            else
            {// Register user and then log in
                Toast.makeText(this, "This User Email Is Not Registered, Please Register it!",Toast.LENGTH_LONG).show();
            }

        }


    }
    public void RegisterClick(View v){
        mUserEmailEdit = (EditText) findViewById(R.id.loginEmail);
        mUserEmail = mUserEmailEdit.getText().toString();
        mCaretakerRadio = (RadioButton) findViewById(R.id.caretakerRadioButton);
        if (mCaretakerRadio.isChecked()) {
            mApplicationManager = new ApplicationManager(ApplicationManager.UserMode.CareGiver);
            // Register user and then log in
            if (mApplicationManager.DoesUserExist(mUserEmail)){
                Toast.makeText(this, "This User Email Has Registered, Please Login it!",Toast.LENGTH_LONG).show();
            }
            else
            {
                mApplicationManager.RegisterUser(mUserEmail);
                mApplicationManager.LogIn(mUserEmail, DataRepositorySingleton.GetInstance());
                Intent patientIntent = new Intent(LoginActivity.this, PatientsListActivity.class);
                patientIntent.putExtra("caretakerID", mUserEmail);
                startActivity(patientIntent);

            }
        }
        else{
            mApplicationManager = new ApplicationManager(ApplicationManager.UserMode.Patient);
            if(mApplicationManager.DoesUserExist(mUserEmail)){
                Toast.makeText(this, "This User Email Has Registered, Please Login it!",Toast.LENGTH_LONG).show();
            }
            else{
                mApplicationManager.RegisterUser(mUserEmail);
                mApplicationManager.LogIn(mUserEmail, DataRepositorySingleton.GetInstance());
                Intent patientIntent = new Intent(LoginActivity.this, ProblemsListActivity.class);
                patientIntent.putExtra("patientID", mUserEmail);
                startActivity(patientIntent);
            }
        }

    }
}

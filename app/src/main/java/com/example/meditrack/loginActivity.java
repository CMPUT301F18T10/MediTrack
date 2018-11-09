package com.example.meditrack;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class loginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    public void loginClick(View v){
        /** checks the username in the emailLogin edit text
         *      >if it exists, logs in and loads that information
         *      >if not exist, create the account with default contact information
         * also checks if caretaker button is checked
         *      >take user to patient list if caretaker
         *      >take user to problem list if paitent
         */
    }

}

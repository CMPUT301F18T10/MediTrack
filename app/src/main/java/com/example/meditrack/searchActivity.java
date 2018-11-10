package com.example.meditrack;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;

public class searchActivity extends AppCompatActivity {
    RadioButton bodyRadio = (RadioButton) findViewById(R.id.searchBodyRadio);
    RadioButton GPSRadio = (RadioButton) findViewById(R.id.searchGPSRadio);
    RadioButton recordRadio = (RadioButton) findViewById(R.id.searchRecordRadio);
    RadioButton problemRadio = (RadioButton) findViewById(R.id.searchProblemRadio);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }
    public void clickGo(View view) {
        if(bodyRadio.isChecked()){
            //searchmanager bodysearch
        }
        else if(GPSRadio.isChecked()){
            //searchmanager GPSsearch
        }
        else if(recordRadio.isChecked()){
            //searchmanager recordsearch
        }
        else if(problemRadio.isChecked()){
            //searchmanager problemsearch
        }
        else{
            //searchmanager general
        }
        /** take results and populate listviews*/
    }

}

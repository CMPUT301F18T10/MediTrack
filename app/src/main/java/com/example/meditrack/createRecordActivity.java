package com.example.meditrack;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class createRecordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_record);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void createRecordBrowse() {

    }
    public void createRecordAddPhoto(){

    }
    public void createRecordAddGPS() {

    }
    public void createRecordAddBody(){

    }
    public void createRecordFinish(){
        
    }
}

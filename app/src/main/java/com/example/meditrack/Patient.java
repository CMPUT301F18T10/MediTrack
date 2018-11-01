package com.example.meditrack;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Patient extends AbstractUser {

    private Bitmap mBodyLocationImage;
    private ArrayList<CareProvider> mCareProviders;
    private String mContactInfo;
    private ArrayList<Problem> mProblems;

    public Patient(String user_id){
        super.mUserId = user_id;
        this.mCareProviders = new ArrayList<CareProvider>();
        this.mProblems = new ArrayList<Problem>();
    }

    public Patient(String user_id, ArrayList<CareProvider> care_providers, ArrayList<Problem> problems, String contact_info){
        super.mUserId = user_id;
        this.mCareProviders = care_providers;
        this.mProblems = problems;
        this.mContactInfo = contact_info;
    }

}

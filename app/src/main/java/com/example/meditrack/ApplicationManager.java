package com.example.meditrack;


import android.util.Log;

import java.util.ArrayList;

public class ApplicationManager extends ElasticsearchManager
{
    /* This class will hold state information about the app
        It would hold the userMode: {Patient, CareGiver},
        the dirty flag etc
        This class would be the first to be instantiated,
        when the app starts. Depending on the userMode,
        it would initialize the DataRepositorySingleton
        with the appropriate parameters
        This class would handle registration and login
        and call UserManagerService if needed
        It also handles permissions, i.e buttons would
        query the ApplicationManager class on whether
        they should be displayed
     */

    enum UserMode { Patient, CareGiver, Invalid;}
    private UserMode mUserMode;
    private static ContactInfo defaultContactInfo = new ContactInfo("default@email","000-000-0000");
    private ElasticsearchManager mESM;

    ApplicationManager(UserMode userMode)
    {
        mUserMode = userMode;
        mESM = new ElasticsearchManager();
    }

    public boolean DoesUserExist(String userName) {
        boolean doesExist = false;
        Patient tempPatient = new Patient(userName,new ArrayList<String>(),new ArrayList<String>(), defaultContactInfo);
        CareProvider tempCareProvider = new CareProvider(userName,new ArrayList<String>());
        switch (mUserMode){
            case Patient:
                try {
                    doesExist = mESM.existObject(userName,tempPatient.getElasticsearchType(),tempPatient.getClass());
                } catch (OperationFailedException eof) {
                    eof.printStackTrace();
                }
                break;

            case CareGiver:
                try {
                    doesExist = mESM.existObject(userName,tempCareProvider.getElasticsearchType(),tempCareProvider.getClass());
                }  catch (OperationFailedException eof) {
                    eof.printStackTrace();
                }
                break;
            case Invalid:
                if (mUserMode == UserMode.Invalid)  {
                    Log.e("Failure","Invalid Operations");

                }
                break;
        }

        return doesExist;
    }

    public boolean LogIn(String userName, DataRepositorySingleton dataRepositorySingleton)
    {
        boolean login = false;

        if (DoesUserExist(userName)){
            login= true;
            dataRepositorySingleton.Initialize(mUserMode, userName, mESM);
        }
        else{
            Log.e("Failure","Login Failure");
        }

        return login;
    }

    public boolean RegisterUser(String userName)
    {
        // TODO: ApplicationManager shouldn't be talking to ElasticSearchManager, there should be a wrapper method in DRS

        Patient patient = new Patient(userName,new ArrayList<String>(),new ArrayList<String>(), defaultContactInfo);
        CareProvider careProvider = new CareProvider(userName,new ArrayList<String>());
        boolean register = false;
        if (!DoesUserExist(userName)){
            register= true;
            switch (mUserMode){
                case Invalid:
                    Log.e("Failure","Invalid Operations");
                    break;
                case Patient:
                    try{
                        mESM.addObject(patient);
                    }catch (OperationFailedException eof) {
                        eof.printStackTrace();
                    }catch (ObjectAlreadyExistsException eae){
                        eae.printStackTrace();
                    }
                    break;
                case CareGiver:
                    try{
                        mESM.addObject(careProvider);
                    }catch (OperationFailedException eof) {
                        eof.printStackTrace();
                    }catch (ObjectAlreadyExistsException eae){
                        eae.printStackTrace();
                    }
                    break;
            }
        }
        else{
            //Given userName exists in database.
            Log.e("Failure","Object Already Exists");
        }
        return register;
    }

    public static void UpdateDataRepository()
    {
        // This is just to provide a static context for Activities to update DRS
        // before pulling any data, and also acts as a gatekeeper since I don't
        // want the activities talking to DRS directly unless it's for queries
        DataRepositorySingleton.GetInstance().RefreshDataRepositorySingleton();
    }

    public static boolean IsFeatureAllowed(String feature, UserMode userMode)
    {
        // TODO: I want to have a xml file holding all the permissions eventually - Adit
        switch (feature)
        {
            case "AddProblem":
            {
                if (userMode == UserMode.Patient) { return true; }
                else { return false; }
            }
        }
        return false;
    }
}

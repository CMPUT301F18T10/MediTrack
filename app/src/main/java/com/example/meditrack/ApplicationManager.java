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

    private boolean mDirty;
    private UserMode mUserMode;



    ApplicationManager(UserMode userMode)
    {
        // TODO: Finish the constructor
        this.mUserMode = userMode;
        mDirty = false;
    }




    public static boolean IsUserExits(UserMode userMode, String userName, ElasticsearchManager elasticsearchManager) {
        boolean isExits = false;
        Patient patient = null;
        CareProvider careProvider = null;
        switch (userMode){
            case Patient:
                try {
                    isExits = elasticsearchManager.existObject(userName,"Patient");
                } catch (OperationFailedException eof) {
                    eof.printStackTrace();
                }
                break;

            case CareGiver:
                try {
                    isExits = elasticsearchManager.existObject(userName,"CareGiver");
                }  catch (OperationFailedException eof) {
                    eof.printStackTrace();
                }
                break;
            case Invalid:
                if (userMode == UserMode.Invalid)  {
                    Log.e("Failure","Invalid Operations");

                }
                break;
        }

        return isExits;
    }

    public static boolean LogIn(UserMode userMode, String userName,ElasticsearchManager elasticsearchManager,DataRepositorySingleton dataRepositorySingleton)
    {
        // TODO: Attempts to log in and returns True upon success
        // Make sure to go through ElasticsearchManager for any
        // interaction with the server

        // Make sure to call DataRepositorySingleton.Initialize
        // to populate with appropriate data
        Patient patient = null;
        CareProvider careProvider = null;
        boolean login = false;


        if (IsUserExits(userMode,userName,elasticsearchManager)){
            login= true;
            dataRepositorySingleton.Initialize(userMode,userName,elasticsearchManager);
        }
        else{
            Log.e("Failure","Login Failure");
        }

        return login;
    }

    public boolean RegisterUser(UserMode userMode, String userName,ElasticsearchManager elasticsearchManager)
    {
        // TODO: Attempts to register and returns True upon success
        // Make sure to go through ElasticsearchManager for any
        // interaction with the server
        ContactInfo testContactInfo = new ContactInfo("Test@email","TestNumber");
        Patient patient = new Patient(userName,new ArrayList<String>(), testContactInfo);
        CareProvider careProvider = new CareProvider(userName,new ArrayList<String>());
        boolean register = false;
        if (!IsUserExits(userMode,userName,elasticsearchManager)){
            register= true;
            switch (userMode){
                case Invalid:
                    Log.e("Failure","Invalid Operations");
                    break;
                case Patient:
                    try{
                        elasticsearchManager.addObject(patient);
                    }catch (OperationFailedException eof) {
                        eof.printStackTrace();
                    }catch (ObjectAlreadyExistsException eae){
                        eae.printStackTrace();
                    }
                    break;
                case CareGiver:
                    try{
                        elasticsearchManager.addObject(careProvider);
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

    private void SetUser(UserMode userMode, AbstractUser user)
    {
        // TODO:
    }

    public void UpdateDataRepository(DataRepositorySingleton dataRepositorySingleton)

    {
        // TODO: Finish this method
        // It will get the updated information from ElasticSearch
        dataRepositorySingleton.RefreshDataRepositorySingleton();

    }

    public boolean IsFeatureAllowed(String feature)
    {
        // TODO: The design for this is not finalized
        // We could potentially just have a map of permissions
        return false;
    }
}

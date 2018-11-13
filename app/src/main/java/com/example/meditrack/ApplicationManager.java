package com.example.meditrack;

import java.util.ArrayList;

public class ApplicationManager extends ElasticSearchManager
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

    enum UserMode { Patient, CareGiver;}

    private boolean mDirty;
    private UserMode mUserMode;

    ApplicationManager()
    {
        // TODO: Finish the constructor
        mDirty = false;
    }

    public static boolean LogIn(UserMode userMode, String userName)
    {
        // TODO: Attempts to log in and returns True upon success
        // Make sure to go through ElasticSearchManager for any
        // interaction with the server

        // Make sure to call DataRepositorySingleton.Initialize
        // to populate with appropriate data
        String userNameInServer;
        Patient patient;
        CareProvider careProvider;
        DataRepositorySingleton dataRepositorySingleton = null;
        boolean login = false;


        if (userMode == UserMode.Patient){
            try{ patient = GetPatient(userName);
                //may do someting
                login = true;
                dataRepositorySingleton.Initialize(userMode,userName);
                //do someting else.
            }catch (ItemNotFound e){ }

            //Need the ElasticSearch to find userName in patient database.
        }
        if (userMode == UserMode.CareGiver){
            //Need the ElasticSearch to find userName in patient database.
            try{
                careProvider = GetCareProvider(userName);
                //may do someting
                login = true;
                dataRepositorySingleton.Initialize(userMode,userName);
                //do someting else.
            }catch (ItemNotFound e){ }


        }



        // Make sure to call DataRepositorySingleton.Initialize
        // to populate with appropriate data
        return login;


    }

    public boolean RegisterUser(UserMode userMode, String userName)
    {
        // TODO: Attempts to register and returns True upon success
        // Make sure to go through ElasticSearchManager for any
        // interaction with the server
        Patient patient = null;
        CareProvider careProvider = null;
        boolean regtiser = false;

        if (userMode == UserMode.Patient){
            //Initialize patient

            regtiser = true;



        }
        if (userMode == UserMode.CareGiver) {
            //Initialize careGiver
            regtiser = true;


        }

        return regtiser;


    }

    private void SetUser(UserMode userMode, AbstractUser user)
    {
        // TODO:
    }

    public void UpdateDataRepository()
    {
        // TODO: Finish this method
        // It will get the updated information from ElasticSearch
    }

    public boolean IsFeatureAllowed(String feature)
    {
        // TODO: The design for this is not finalized
        // We could potentially just have a map of permissions
        return false;
    }
}

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
        Patient tempPatient = new Patient(userName,new ArrayList<String>(),new ArrayList<String>(), defaultContactInfo,new ArrayList<String>());
        CareProvider tempCareProvider = new CareProvider(userName,new ArrayList<String>(),new ArrayList<String>());
        final String tmDevice, tmSerial, androidId;

        switch (mUserMode){
            case Patient:
                try {
                    doesExist = mESM.existObject(userName,tempPatient.getElasticsearchType(),tempPatient.getClass());

                } catch (NullPointerException npe){
                    npe.printStackTrace();
                }
                catch (OperationFailedException eof) {
                    eof.printStackTrace();
                }
                break;

            case CareGiver:
                try {
                    doesExist = mESM.existObject(userName,tempCareProvider.getElasticsearchType(),tempCareProvider.getClass());
                } catch (NullPointerException npe){
                    npe.printStackTrace();
                } catch (OperationFailedException eof) {
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

    public void InitializeDataRepositorySingleton(DataRepositorySingleton drs, String userName)
    {
        drs.Initialize(mUserMode, userName, mESM);
    }

    /**
     * Get an ArrayList of approvedDevicesID by given a userID
     * @param   userName: The input userID
     * @param   dataRepositorySingleton: The local Datebase we used to.
     * @return An ArrayList of objects with the given class, null if operation failed.
     */
    public ArrayList<String> getapprovedDevicesID(String userName,DataRepositorySingleton dataRepositorySingleton){
        ArrayList<String> approvedDevicesID = null;
        if (DoesUserExist(userName)){
            dataRepositorySingleton.Initialize(mUserMode, userName, mESM);
            switch (mUserMode){
                case CareGiver:
                    try {
                        approvedDevicesID = dataRepositorySingleton.GetCareProvider().getApprovedDeviceIDs();
                    }catch (NullPointerException nep){
                        nep.printStackTrace();
                    } catch (DataRepositorySingleton.DataRepositorySingletonNotInitialized dataRepositorySingletonNotInitialized) {
                        dataRepositorySingletonNotInitialized.printStackTrace();
                    } catch (DataRepositorySingleton.InvalidUserMode invalidUserMode) {
                        invalidUserMode.printStackTrace();
                    }
                case Patient:
                    try {
                        approvedDevicesID = dataRepositorySingleton.GetPatient().getApprovedDeviceID();
                    } catch (DataRepositorySingleton.DataRepositorySingletonNotInitialized dataRepositorySingletonNotInitialized) {
                        dataRepositorySingletonNotInitialized.printStackTrace();
                    } catch (DataRepositorySingleton.InvalidUserMode invalidUserMode) {
                        invalidUserMode.printStackTrace();
                    }
            }

        }
        return approvedDevicesID;
    }

    /**
     *
     * @param userName:The Input user name
     * @param DRS: The local database
     * @return: The short code to authorize the device and share profile.
     */
    public String getUserShortCode(String userName,DataRepositorySingleton DRS){
        String shortCode = null;
        if (DoesUserExist(userName)){
            DRS.Initialize(mUserMode, userName, mESM);
            switch (mUserMode){
                case CareGiver:
                    try {
                        shortCode = DRS.GetCareProvider().getShortCode();
                    } catch (NullPointerException nep){
                        nep.printStackTrace();

                    } catch (DataRepositorySingleton.DataRepositorySingletonNotInitialized dataRepositorySingletonNotInitialized) {
                        dataRepositorySingletonNotInitialized.printStackTrace();
                    } catch (DataRepositorySingleton.InvalidUserMode invalidUserMode) {
                        invalidUserMode.printStackTrace();
                    }
                case Patient:
                    try {
                        shortCode = DRS.GetPatient().getShortCode();
                    } catch (DataRepositorySingleton.DataRepositorySingletonNotInitialized dataRepositorySingletonNotInitialized) {
                        dataRepositorySingletonNotInitialized.printStackTrace();
                    } catch (DataRepositorySingleton.InvalidUserMode invalidUserMode) {
                        invalidUserMode.printStackTrace();
                    }
            }

        }
        return shortCode;
    }


    public boolean RegisterUser(String userName,ArrayList<String> approvedDevicesID)
    {
        // TODO: ApplicationManager shouldn't be talking to ElasticSearchManager, there should be a wrapper method in DRS

        Patient patient = new Patient(userName,new ArrayList<String>(),new ArrayList<String>(),defaultContactInfo, approvedDevicesID);
        CareProvider careProvider = new CareProvider(userName,new ArrayList<String>(),approvedDevicesID);
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

package com.example.meditrack;


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
    enum UserMode {Patient, CareGiver;}
    private UserMode mUserMode;
    DataRepositorySingleton dataRepositorySingleton;

    public ApplicationManager(UserMode userMode)
    {
        // TODO: Finish the constructor
        this.mUserMode = userMode;
    }


    /**
     *
     * @param userMode: To decide which mode to execute.
     * @param userID: user inputs userName.
     * @return a boolean value which determines login successfully or not.
     */


    public static boolean LogIn(UserMode userMode, String userID)
    {
        // TODO: Attempts to log in and returns True upon success
        // Make sure to go through ElasticSearchManager for any
        // interaction with the server

        // Make sure to call DataRepositorySingleton.Initialize
        // to populate with appropriate data
        String testindex;
        Patient patient = null;
        CareProvider careProvider = null;
        DataRepositorySingleton dataRepositorySingleton = null;
        boolean login = false;


        if (userMode == UserMode.Patient){//To handle Patient Mode.
                patient = getObjectFromId(userID,testindex);//Get patient object from Database.
                if (patient != null){
                    login = true;   //Change login target to True.
                    dataRepositorySingleton.Initialize(userMode,userID);  //Initialize dataRepo for this patient.
                }
                else{
                    //Login failure
                    //May prompt user to login again
                    //Or enter the password they have.

                }


                //do someting else
        }
        else { //userMode == UserMode.CareGiver
            //Need the ElasticSearch to find userName in patient database.
                careProvider = getObjectFromId(userID,testindex);
                if (careProvider != null){
                    login =true;
                    dataRepositorySingleton.Initialize(userMode,userID);
                }
                else{
                    //Login failure
                    //May prompt user to login again
                    //Or enter the password they have
                }

        }


        // Make sure to call DataRepositorySingleton.Initialize
        // to populate with appropriate data
        return login;


    }

    /**
     *
     * @param userMode To decide which mode to execute.
     * @param userId user inputs userName.
     * @return a boolean value which determines register successfully or not.
     */

    public boolean RegisterUser(UserMode userMode, String userId)
    {
        // TODO: Attempts to register and returns True upon success
        // Make sure to go through ElasticSearchManager for any
        // interaction with the server
        Patient patient = null;
        CareProvider careProvider = null;
        boolean regtiser = false;

        if (userMode == UserMode.Patient){
            patient = getObjectFromId(userId,testindex);
            if (patient == null){
                regtiser = true;
                patient.userId = userId;
                addObject(patient);
                //prompt user to regiser
                //upload user
            }
            else{
                //This userID has existed in Database.
                //Prompt the user to login

            }
        }
        else{
            careProvider = getObjectFromId(userId,testindex);
            //Initialize careGiver
            if (patient== null){
                regtiser = true;
                careProvider.userId = userId;
                addObject(careProvider);

            }else {
                //This userID has existed in Database.
                //Prompt the user to login

            }

        }

        return regtiser;
    }

    private void SetUser(UserMode userMode, AbstractUser user)
    {
        // TODO:
    }

    public void UpdateDataRepository()
    {
        dataRepositorySingleton.RefreshDataRepositorySingleton();



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

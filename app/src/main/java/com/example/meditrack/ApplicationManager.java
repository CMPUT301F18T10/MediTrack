package com.example.meditrack;

import java.util.ArrayList;

public class ApplicationManager
{
    /* This class will hold state information about the app
        It would hold the userMode: {Patient, CareGiver},
        the dirty flag etc

        It would hold all the information about the
        user, including problems, records, etc
        This will one of only global objects in the app

        This class would be the first to be instantiated,
        when the app starts. Depending on the userMode,
        it would either instantiate Patient or Caregiver

        This class would handle registration and login
        and call UserManagerService if needed

        It also handles permissions, i.e buttons would
        query the ApplicationManager class on whether
        they should be displayed
     */

    enum UserMode { Patient, CareGiver;}

    private boolean mDirty;
    private UserMode mUserMode;
    private AbstractUser mUser;

    ApplicationManager()
    {
        // TODO: Finish the constructor
        mDirty = false;
    }

    public boolean LogIn(String userName, String password)
    {
        // TODO: Attempts to log in and returns True upon success
        // Gets the user object from the ElasticSearch instance
        // Sets the user afterwards
        // Maybe return exceptions instead of boolean
        return false;
    }

    public boolean RegisterUser(String userName, String password)
    {
        // TODO: Attempts to register and returns True upon success
        // Create the right user object and saves it to the database
        // Then set mUserMode and mUser
        // Maybe return exceptions instead of boolean
        return false;
    }

    private void SetUser(UserMode userMode, AbstractUser user)
    {
        // set the information from the server
    }

    public void UpdateUserInformation()
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

    // These functions would be used by QueryService to get and process
    // the problems in case it doesn't want to get it from the server
    public ArrayList<Problem> GetProblemListForCurrentUser()
    {
        // TODO: Finish this method
        return new ArrayList<Problem>();
    }

    public ArrayList<AbstractRecord> GetRecordListForCurrentUser()
    {
        // TODO: Finish this method
        return new ArrayList<AbstractRecord>();
    }

    public ArrayList<AbstractRecord> GetRecordListForProblemId(Integer problemId)
    {
        // TODO: Finish this method
        return new ArrayList<AbstractRecord>();
    }

    public boolean IsDirty()
    {
        return mDirty;
    }

    public void GetDirty(boolean isDirty)
    {
        mDirty = isDirty;
    }
}

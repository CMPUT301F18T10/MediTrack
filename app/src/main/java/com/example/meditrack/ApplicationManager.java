package com.example.meditrack;

import java.util.ArrayList;

public class ApplicationManager
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

    public boolean LogIn(UserMode userMode, String userName)
    {
        // TODO: Attempts to log in and returns True upon success
        // Gets the user object from the ElasticSearch instance
        // Sets the user afterwards
        // Maybe return exceptions instead of boolean
        return false;
    }

    public boolean RegisterUser(UserMode userMode, String userName)
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

    public boolean IsDirty()
    {
        return mDirty;
    }

    public void GetDirty(boolean isDirty)
    {
        mDirty = isDirty;
    }
}

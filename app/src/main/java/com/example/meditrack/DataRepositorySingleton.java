package com.example.meditrack;

import java.util.ArrayList;

public class DataRepositorySingleton
{
    /*
        This singleton class will hold all the "business" data for the application
        and will in a very loose sense act as the model. Making it a singleton ensures
        there's only one copy of it which is useful since the amount of data needed
        might be large

        Only the ApplicationManager and the static services are allowed to interact with
        this and the ApplicationManager will initialize it when the user is logged in
        according to the userMode
     */

    private static DataRepositorySingleton instance = null;

    private ArrayList<Problem> mProblemList;
    private ArrayList<AbstractRecord> mRecordList;
    private AbstractUser mUser;

    protected DataRepositorySingleton()
    {

    }


    // lazy construction of the instance
    public static DataRepositorySingleton GetInstance()
    {
        if (instance == null) instance = new DataRepositorySingleton();
        return instance;
    }

    private void PopulateUser(ApplicationManager.UserMode userMode, String userName)
    {

    }

    private void PopulateProblemList()
    {

    }

    private void PopulateRecordList()
    {

    }

    public void Initialize(ApplicationManager.UserMode userMode, String userName)
    {
        // TODO: This function would populate all data in the class

        // Get the appropriate user object

        // Get the appropriate list of problems

        // Get the appropriate list of records
    }
}

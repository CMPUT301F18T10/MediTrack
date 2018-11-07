package com.example.meditrack;

import java.util.ArrayList;

public class ElasticSearchManager
{
    /*
        This is a static service class that is responsible for interacting
        with the ElasticSearchInstance

        The DataRepositorySingleton will call functions here to populate itself
        with all the necessary data when an update is necessary and also rely
        on this class to upload any changes made in the application
     */

    public static ArrayList<Problem> GetProblems(ArrayList<Integer> problemIds)
    {
        // TODO: Get all the problems matching the problem Ids in the list
        return new ArrayList<>();
    }

    public static ArrayList<AbstractRecord> GetRecords(ArrayList<Integer> recordsIds)
    {
        // TODO: Get all the records matching the record Ids in the list
        return new ArrayList<>();
    }

    public static CareProvider GetCareProvider(String userName)
    {
        // TODO: Get the CareProvider, return null if doesn't exist
        return null;
    }

    public static Patient GetPatient(String userName)
    {
        // TODO: Get the Patient
        return null;
    }

    public static void PostProblems(ArrayList<Problem> problems)
    {
        // TODO: For all the problems, if it exists add else edit
    }

    public static void PostRecords(ArrayList<AbstractRecord> records)
    {
        // TODO: For all the records, if it exists add else edit
    }

}

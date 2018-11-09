package com.example.meditrack;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

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
    private static final String elasticSearchInstancePath = "http://cmput301.softwareprocess.es:8080/cmput301f18t10test";
    private static JestDroidClient mClient;

    public static ArrayList<Problem> GetProblems(ArrayList<Integer> problemIds) throws ItemNotFound
    {
        // TODO: Get all the problems matching the problem Ids in the list
        return new ArrayList<>();
    }

    public static ArrayList<AbstractRecord> GetRecords(ArrayList<Integer> recordsIds) throws ItemNotFound
    {
        // TODO: Get all the records matching the record Ids in the list
        return new ArrayList<>();
    }

    public static CareProvider GetCareProvider(String userName) throws ItemNotFound
    {
        // TODO: Get the CareProvider, return null if doesn't exist
        return null;
    }

    public static Patient GetPatient(String userName) throws ItemNotFound
    {
        // TODO: Get the Patient
        return null;
    }

    public static void PostCareProvider(CareProvider careProvider)
    {
        // TODO: Add the new CareProvider user class
    }

    public static void PostPatient(Patient patient)
    {
        // TODO: Add the new patient user class
    }

    public static void PostProblems(ArrayList<Problem> problems)
    {
        // TODO: For all the problems, if it exists add else edit
    }

    public static void PostRecords(ArrayList<AbstractRecord> records)
    {
        // TODO: For all the records, if it exists add else edit
    }


    private static void ConfigureClient()
    {
        if (mClient == null)
        {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder(elasticSearchInstancePath);
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            mClient = (JestDroidClient) factory.getObject();
        }
    }

}

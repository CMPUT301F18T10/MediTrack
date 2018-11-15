package com.example.meditrack;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Collection;

import io.searchbox.client.JestClient;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;

public class ElasticsearchManager {

    private final String ELASTICSEARCH_URL = "http://cmput301.softwareprocess.es:8080";
    private final String ELASTICSEARCH_INDEX = "cmput301f18t10";
    private final String ELASTICSEARCH_TEST_INDEX = "cmput301f18t10test";
    private final String tag = "esm";

    private JestDroidClient client;

    private String elasticsearchIndex;

    public ElasticsearchManager() {
        initElasticsearch();
        this.elasticsearchIndex = ELASTICSEARCH_INDEX;
    }

    private void initElasticsearch() {
        if (client == null){

            DroidClientConfig.Builder builder = new DroidClientConfig.Builder(ELASTICSEARCH_URL);
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }

    public class ObjectNotFoundException extends Exception {

    }

    public class ObjectAlreadyExistsException extends Exception {

    }

    public class OperationFailedException extends Exception {

    }

    private class GenericAddTask<T extends ElasticsearchStorable> extends AsyncTask<T, Void, Void> {
        @Override
        protected Void doInBackground(T... ts) {
            initElasticsearch();
            for (T t : ts){
                Index index = new Index.Builder(t).index(elasticsearchIndex).type(t.getElasticsearchType()).build();
                try {
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()){
                        Log.i(tag, "Success! id: " + result.getId());
                    } else {
                        Log.i(tag, "Failure: " + result.getErrorMessage());
                    }
                } catch(java.io.IOException e){
                    Log.i(tag, "Failure: IOException");
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    public void setTestingMode() {
        this.elasticsearchIndex = ELASTICSEARCH_TEST_INDEX;
    }

    public void unsetTestingMode() {
        this.elasticsearchIndex = ELASTICSEARCH_INDEX;
    }

    /**
     * Add an object to the Elasticsearch server
     * @param t object to add
     * @param <T> type of the object
     */
    public <T extends ElasticsearchStorable> void addObject (T t) throws ObjectAlreadyExistsException, OperationFailedException {
        GenericAddTask<T> task = new GenericAddTask<>();
        task.execute(t);
    }

    /**
     * Add objects to the Elasticsearch server
     * @param ts objects to add
     * @param <T> type of the objects
     */
    public <T extends ElasticsearchStorable> void addObjects(Collection<T> ts) throws ObjectAlreadyExistsException, OperationFailedException {
        for (T t : ts) {
            addObject(t);
        }
    }

    /**
     * Search the given type for exact match of the field "id".  This method handles getting
     * objects with a known id.
     * @param <T> Class of the object
     * @param id The String object that the "id" attribute of the Object holds
     * @param type The type to search for
     * @return Matched object
     */
    public <T extends ElasticsearchStorable> T getObjectFromId(String id, String type) throws ObjectNotFoundException, OperationFailedException {
        return null;
    }

    /**
     * Search for all problems containing the given text
     * @param text text to search
     * @return an ArrayList of Problems that contain the given text
     */
    public ArrayList<Problem> searchProblems (String text) throws OperationFailedException {
        return null;
    }


    /**
     * Search for all patient records that contain the given text
     * @param text text to search
     * @return an ArrayList of PatientRecord
     */
    public ArrayList<PatientRecord> searhPatientRecords(String text) throws OperationFailedException {
        return null;
    }

    /**
     * Search for all care provider records that contain the given text
     * @param text text to search
     * @return an ArrayList of CareProviderRecord
     */
    public ArrayList<CareProviderRecord> searchCareProviderRecord(String text) throws OperationFailedException {
        return null;
    }

    /**
     * Get an ArrayList of problems by providing the patient id
     * @param patientId the Id of the patient
     * @return the problems of the patient
     */
    public ArrayList<Problem> getProblemsByPatientId(String patientId) throws OperationFailedException {
        return null;
    }


    /**
     * Get an ArrayList of records by providing the problem id
     * @param problemId the Id of the problem
     * @return the records of the problem
     */
    public ArrayList<AbstractRecord> getRecordsByProblemId(String problemId) throws OperationFailedException {
        return null;
    }

    /**
     * Delete an object that matches the given id in the given type
     * @param id the id of the Object
     * @param type the type to search search for
     */
    public void deleteObject(String id, String type) throws ObjectNotFoundException, OperationFailedException {

    }

    /**
     * Replace the object with the given id in the given type with the given obj
     * @param id the id of the object to replace
     * @param type the type the object resides in
     * @param obj the object to replace the old object
     * @param <T>
     */
    public <T extends ElasticsearchStorable> void updateObject(String id, String type, T obj) throws ObjectNotFoundException, OperationFailedException {

    }

    /**
     * Given an id and type, determin wheather this object exists or not
     * @param id the id of the object
     * @param type the type to search for
     * @return true if the object with the given id exists in the given type
     */
    public boolean existObject(String id, String type) throws OperationFailedException {
        return false;
    }

}

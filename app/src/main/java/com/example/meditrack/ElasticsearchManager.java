package com.example.meditrack;

import android.media.VolumeShaper;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import org.w3c.dom.Document;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.DeleteIndex;

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
                Index index = new Index.Builder(t).index(elasticsearchIndex).type(t.getElasticsearchType()).id(t.getId()).build();
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


    private class QueryTask<T extends ElasticsearchStorable> extends AsyncTask<Object, Void, ArrayList<T>> {
        /**
         * Get an ArrayList of objects by sending a given query to Elasticsearch server
         * @param params a list of params where the first param is the query, second param is the
         *               type to search for, and the last param is the actual class of the object
         * @return An ArrayList of objects with the given class, null if operation failed.
         */
        @Override
        protected ArrayList<T> doInBackground(Object... params) {

            String query = (String) params[0];
            String type = (String) params[1];
            Class cls = (Class) params[2];
            ArrayList<T> result = null;

            Search search = new Search.Builder(query).addIndex(elasticsearchIndex).addType(type).build();

            try {
                SearchResult sr = client.execute(search);
                if (sr.isSucceeded()) {
                    result = (ArrayList<T>)sr.getSourceAsObjectList(cls);
                }
            } catch (java.io.IOException e) {
                Log.i(tag, "Failure: IOException");
                e.printStackTrace();
            }
            return result;
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
    public <T extends ElasticsearchStorable> T getObjectFromId(String id, String type, Class<? extends ElasticsearchStorable> cls) throws ObjectNotFoundException, OperationFailedException {

        T result;

        String query =
        "{\n" +
            "\"query\": {\n" +
              "\"term\" : {" + "\"" + "id" + "\"" + ":" + "\"" + id.toLowerCase() + "\"" + "}\n" +
            "}\n" +
        "}";
        QueryTask<T> task = new QueryTask<>();

        try {
            // TODO: Maybe throw another exception in case there are multiple objects of the same Id
            ArrayList<T> results = task.execute(query, type, cls).get();
            if (results.size() < 1) {
                throw new ObjectNotFoundException();
            } else {
                Log.i(tag, "Found: " + results.size());
                result = results.get(0);
            }
        } catch (java.util.concurrent.ExecutionException e) {
            e.printStackTrace();
            throw new OperationFailedException();
        } catch (java.lang.InterruptedException e) {
            e.printStackTrace();
            throw new OperationFailedException();
        }
        return result;
    }

    private <T extends ElasticsearchStorable> ArrayList<T> searchObjects(String string, String type, Class<T> cls) throws OperationFailedException {
        ArrayList<T> result;
        String query =
        "{\n" +
            "\"query\": {\n" +
                "\"term\" : {" + "\"" + "_all" + "\"" + ":" + "\"" + string.toLowerCase() + "\"" + "}\n" +
            "}\n" +
        "}";
        QueryTask<T> task = new QueryTask<>();
        try {
            result = task.execute(query, type, cls).get();
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperationFailedException();
        }
        return result;
    }

    /**
     * Search for all problems containing the given text
     * @param text text to search
     * @return an ArrayList of Problems that contain the given text
     */
    public ArrayList<Problem> searchProblems (String text) throws OperationFailedException {
        // TODO: remove hard-coded type
        return searchObjects(text, "problems", Problem.class);
    }

    /**
     * Search for all patient records that contain the given text
     * @param text text to search
     * @return an ArrayList of PatientRecord
     */
    public ArrayList<PatientRecord> searchPatientRecords(String text) throws OperationFailedException {
        // TODO: remove hard-coded type
        return searchObjects(text, "patient_records", PatientRecord.class);
    }

    /**
     * Search for all care provider records that contain the given text
     * @param text text to search
     * @return an ArrayList of CareProviderRecord
     */
    public ArrayList<CareProviderRecord> searchCareProviderRecord(String text) throws OperationFailedException {
        return new ArrayList<>();
    }

    /**
     * Get an ArrayList of problems by providing the patient id
     * @param patientId the Id of the patient
     * @return the problems of the patient
     */
    public ArrayList<Problem> getProblemsByPatientId(String patientId) throws OperationFailedException {
        ArrayList<Problem> problems;
        String query =
        "{\n" +
            "\"query\": {\n" +
              "\"term\" : {" + "\"" + "patientId" + "\"" + ":" + "\"" + patientId.toLowerCase() + "\"" + "}\n" +
            "}\n" +
        "}";
        QueryTask<Problem> task = new QueryTask<>();
        try {
            problems = task.execute(query, "problems", Problem.class).get();
        } catch (Exception e) {
            throw new OperationFailedException();
        }
        return problems;
    }

    /**
     * Find PatientRecords that correspond to the given problemId
     * @param problemId problemId to search for
     * @return An ArrayList of PatientRecords that correspond to the given problemId
     * @throws OperationFailedException
     */
    public ArrayList<PatientRecord> getPatientRecordByProblemId(String problemId) throws OperationFailedException {
        ArrayList<PatientRecord> patientRecords;
        String query =
                "{\n" +
                        "\"query\": {\n" +
                        "\"term\" : {" + "\"" + "problemId" + "\"" + ":" + "\"" + problemId.toLowerCase() + "\"" + "}\n" +
                        "}\n" +
                        "}";
        QueryTask<PatientRecord> task = new QueryTask<>();
        try {
            patientRecords = task.execute(query, "patient_records", PatientRecord.class).get();
        } catch (Exception e) {
            throw new OperationFailedException();
        }
        return patientRecords;
    }

    /**
     * Find CareProviderRecords that correspond to the given problemId
     * @param problemId problemId to search for
     * @return An ArrayList of CareProviderRecords that correspond to the given problemId
     * @throws OperationFailedException
     */
    public ArrayList<CareProviderRecord> getCareProviderRecordByProblemId(String problemId) throws OperationFailedException {
        ArrayList<CareProviderRecord> careProviderRecords;
        String query =
                "{\n" +
                        "\"query\": {\n" +
                        "\"term\" : {" + "\"" + "problemId" + "\"" + ":" + "\"" + problemId.toLowerCase() + "\"" + "}\n" +
                        "}\n" +
                        "}";
        QueryTask<CareProviderRecord> task = new QueryTask<>();
        try {
            careProviderRecords = task.execute(query, "care_provider_records", CareProviderRecord.class).get();
        } catch (Exception e) {
            throw new OperationFailedException();
        }
        return careProviderRecords;
    }

    /**
     * Delete an object that matches the given id in the given type
     * @param id the id of the Object
     * @param type the type to search search for
     * @param cls class of the object
     */
    public void deleteObject(String id, String type, Class<? extends ElasticsearchStorable> cls) throws ObjectNotFoundException, OperationFailedException {
        if (!existObject(id, type, cls)) {
            throw new ObjectNotFoundException();
        }
        try {
            client.execute(new Delete.Builder(id).index(elasticsearchIndex).type(type).build());
        } catch (java.io.IOException e) {
            throw new OperationFailedException();
        }
    }

    /**
     * Replace the object with the given id in the given type with the given obj
     * @param id the id of the object to replace
     * @param type the type the object resides in
     * @param obj the object to replace the old object
     * @param <T>
     */
    public <T extends ElasticsearchStorable> void updateObject(String id, String type, T obj) throws ObjectNotFoundException, OperationFailedException {
        deleteObject(obj.getId(), obj.getElasticsearchType(), obj.getClass());
        try {
            addObject(obj);
        } catch (ObjectAlreadyExistsException e) {
            Log.i(tag, "Object with exact same id added while it's deleted");
            throw new OperationFailedException();
        }
    }

    /**
     * Given an id and type, determin wheather this object exists or not
     * @param id the id of the object
     * @param type the type to search for
     * @param cls the class of the object
     * @return true if the object with the given id exists in the given type
     */
    public boolean existObject(String id, String type, Class<? extends ElasticsearchStorable> cls) throws OperationFailedException {
        try {
            getObjectFromId(id, type, cls);
        } catch (ObjectNotFoundException e) {
            return false;
        }
        return true;
    }

    private boolean deleteIndex() throws java.io.IOException {
        DeleteIndex idx = new DeleteIndex.Builder(elasticsearchIndex).build();
        JestResult r  = client.execute(idx);
        return r.isSucceeded();
    }

    private boolean createIndex() throws java.io.IOException {
        CreateIndex idx = new CreateIndex.Builder(elasticsearchIndex).build();
        JestResult r = client.execute(idx);
        return r.isSucceeded();
    }

    public void resetIndex() throws OperationFailedException {
        try {
            deleteIndex();
            if (!createIndex()){
                throw new OperationFailedException();
            }
        } catch (java.io.IOException e) {
            throw new OperationFailedException();
        }
    }

}

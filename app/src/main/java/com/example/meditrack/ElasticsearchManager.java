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

    public <T extends ElasticsearchStorable> void addObject(T t) {
        GenericAddTask<T> task = new GenericAddTask<>();
        task.execute(t);
    }

    public <T extends ElasticsearchStorable> void addObjects(Collection<T> ts) {
        for (T t : ts) {
            GenericAddTask<T> task = new GenericAddTask<>();
            task.execute(t);
        }
    }

}

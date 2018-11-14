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

    private static final String elasticsearchUrl = "http://cmput301.softwareprocess.es:8080";
    private static final String elasticsearchIndex = "cmput301f18t10";
    private static final String elasticsearchTestIndex = "cmput301f18t10test";
    private static final String tag = "esm";

    private static JestDroidClient client;

    private static void initElasticsearch(){
        if (client == null){

            DroidClientConfig.Builder builder = new DroidClientConfig.Builder(elasticsearchUrl);
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }

    public static class GenericAddTask<T extends ElasticsearchStorable> extends AsyncTask<T, Void, Void> {

        @Override
        protected Void doInBackground(T... ts){

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

}

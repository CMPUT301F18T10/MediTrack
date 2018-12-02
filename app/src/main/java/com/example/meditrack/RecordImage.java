package com.example.meditrack;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.util.Random;

import io.searchbox.annotations.JestId;

public class RecordImage implements ElasticsearchStorable {

    public static final String type = "images";

    private String id;
    private String recordId;
    private byte[] byteArray;

    public RecordImage(Bitmap bitmap, String recordId) {
        this.recordId = recordId;
        this.id = new BigInteger(128, new Random()).toString();

        // Mezm (611062) and Jay (3779042): https://stackoverflow.com/questions/4989182/converting-java-bitmap-to-byte-array
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byteArray = stream.toByteArray();
        bitmap.recycle();
    }

    @Override
    public String getElasticsearchType() {
        return this.type;
    }

    @Override
    public String getId() {
        return this.id;
    }

    public Bitmap getBitmap() {
        // automaton (2966528): https://stackoverflow.com/questions/4989182/converting-java-bitmap-to-byte-array
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }
}

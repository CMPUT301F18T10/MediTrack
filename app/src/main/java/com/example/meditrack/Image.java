package com.example.meditrack;

import android.graphics.Bitmap;

import java.math.BigInteger;
import java.util.Random;

public class Image implements ElasticsearchStorable {

    public static final String type = "images";

    private Bitmap bitmap;
    private String id;

    public Image(Bitmap bitmap) {
        this.bitmap = bitmap;
        this.id = new BigInteger(128, new Random()).toString();
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
        return this.bitmap;
    }
}

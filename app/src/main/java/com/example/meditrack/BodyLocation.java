package com.example.meditrack;

import android.graphics.Bitmap;
import android.graphics.Point;

public class BodyLocation {

    private Bitmap bodyLocationImage;
    private Point point;
    private String id;

    public BodyLocation(Bitmap bodyLocationImage, Point point,String id) {
        this.id = id;
        this.bodyLocationImage = bodyLocationImage;
        this.point = point;
    }

    public Bitmap getBodyLocationImage() {
        return bodyLocationImage;
    }

    public Point getPoint() {
        return point;
    }
}

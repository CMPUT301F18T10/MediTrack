package com.example.meditrack;

import android.graphics.Bitmap;
import android.graphics.Point;

public class BodyLocation {

    private Bitmap bodyLocationImage;
    private Point point;

    public BodyLocation(Bitmap bodyLocationImage, Point point) {
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

package com.example.sendandreturn;

import android.graphics.Bitmap;

/**
 * Created by ananthagarwal on 5/28/17.
 */

public class PurchaseItem {
    private String name;
    private String notes;
    private String store;
    private String imagePath;

    public PurchaseItem(String n, String note, String loc, String image) {
        name = n;
        notes = note;
        store = loc;
        imagePath = image;
        // Add ability to specify location on Google Maps.
        //Image should be attained using the phone camera.
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return imagePath;
    }
    public String getStore() {
        return store;
    }
    public String getNotes() { return notes; }


    @Override
    public String toString() {
        return name;
    }
}

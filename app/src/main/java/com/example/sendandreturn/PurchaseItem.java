package com.example.sendandreturn;

/**
 * Created by ananthagarwal on 5/28/17.
 */

public class PurchaseItem {
    private String name;
    private String notes;
    private String store;
    private String image;

    public PurchaseItem(String n, String note, String loc) {
        name = n;
        notes = note;
        store = loc;
        // Add ability to specify location on Google Maps.
        //Image should be attained using the phone camera.
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }
    public String getStore() {
        return store;
    }


    @Override
    public String toString() {
        return name;
    }
}

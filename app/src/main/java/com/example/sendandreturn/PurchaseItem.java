package com.example.sendandreturn;

/**
 * Created by ananthagarwal on 5/28/17.
 */

public class PurchaseItem {
    private String name;
    private String notes;
    private String location;

    public PurchaseItem(String n, String note, String loc) {
        name = n;
        notes = note;
        location = loc;
        // Add ability to specify location on Google Maps.
        //Image should be attained using the phone camera.
    }



    @Override
    public String toString() {
        return name;
    }
}

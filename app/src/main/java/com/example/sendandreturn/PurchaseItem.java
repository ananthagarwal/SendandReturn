package com.example.sendandreturn;

/**
 * Created by ananthagarwal on 5/28/17.
 */

public class PurchaseItem {
    private String name;
    private double price;
    private String buyReason;
    private String location;

    public PurchaseItem(String n, double p, String b, String l) {
        name = n;
        price = p;
        buyReason = b;
        location = l;
        // Add ability to specify location on Google Maps.
        //Image should be attained using the phone camera.
    }



    @Override
    public String toString() {
        return name;
    }
}

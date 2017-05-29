package com.example.sendandreturn;

/**
 * Created by ananthagarwal on 5/28/17.
 */

public class ReturnItem {
    private String name;
    private String location;
    private String returnReason;

    public ReturnItem(String n, String l, String r) {
        name = n;
        location = l;
        returnReason = r;
    }


    @Override
    public String toString() {
        return name;
    }
}

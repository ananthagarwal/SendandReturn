package com.example.sendandreturn;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ananthagarwal on 5/28/17.
 */

public class Item implements Parcelable{
    private String name;
    private String notes;
    private String store;
    private String imagePath;

    public static final Parcelable.Creator<Item> CREATOR
            = new Parcelable.Creator<Item>() {
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
    public Item(String n, String loc, String note, String image) {
        name = n;
        notes = note;
        store = loc;
        imagePath = image;
        // Add ability to specify location on Google Maps.
        //Image should be attained using the phone camera.
    }

    public Item(Parcel parcel) {
        String[] input = new String[4];
        parcel.readStringArray(input);

        name = input[0];
        notes = input[1];
        store = input[2];
        imagePath = input[3];
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {name, notes, store, imagePath});


    }
}

package com.example.sendandreturn;

import android.provider.BaseColumns;

/**
 * Created by ananthagarwal on 6/6/17.
 */

public class DatabaseContract {

    private DatabaseContract() {}

    public static class Row implements BaseColumns {
        public static final String TABLE_NAME = "PurchaseandReturnItems";
        public static final String COLUMN_NAME_NAME = "Name";
        public static final String COLUMN_NAME_NOTES = "Notes";
        public static final String COLUMN_NAME_STORE = "Store";
        public static final String COLUMN_NAME_IMAGE = "Image";


    }
}

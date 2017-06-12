package com.example.sendandreturn;

import android.provider.BaseColumns;

/**
 * Created by ananthagarwal on 6/6/17.
 */

class DatabaseContract {

    private DatabaseContract() {}

     static class Row implements BaseColumns {
        static final String TABLE_NAME = "PurchaseandReturnItems";
        static final String COLUMN_NAME_NAME = "Name";
        static final String COLUMN_NAME_NOTES = "Notes";
        static final String COLUMN_NAME_STORE = "Store";
        static final String COLUMN_NAME_IMAGE = "Image";
        static final String COLUMN_NAME_PURCHASE = "Purchase";

    }
}

package com.example.sendandreturn;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ananthagarwal on 6/6/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "ItemManager";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DatabaseContract.Row.TABLE_NAME + " (" +
                    DatabaseContract.Row._ID + " INTEGER PRIMARY KEY, " +
                    DatabaseContract.Row.COLUMN_NAME_NAME + " TEXT, " +
                    DatabaseContract.Row.COLUMN_NAME_NOTES + " TEXT, " +
                    DatabaseContract.Row.COLUMN_NAME_STORE + " TEXT, " +
                    DatabaseContract.Row.COLUMN_NAME_IMAGE + " TEXT, " +
                    DatabaseContract.Row.COLUMN_NAME_PURCHASE + " TEXT" + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DatabaseContract.Row.TABLE_NAME;

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over

        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    //From stack overflow: https://stackoverflow.com/questions/27003486/printing-all-rows-of-a-sqlite-database-in-android
    public String getTableAsString(SQLiteDatabase db, String tableName) {
        Log.d("Purchase Activity", "getTableAsString called");
        String tableString = String.format("Table %s:\n", tableName);
        Cursor allRows  = db.rawQuery("SELECT * FROM " + tableName, null);
        if (allRows.moveToFirst() ){
            String[] columnNames = allRows.getColumnNames();
            do {
                for (String name: columnNames) {
                    tableString += String.format("%s: %s\n", name,
                            allRows.getString(allRows.getColumnIndex(name)));
                }
                tableString += "\n";

            } while (allRows.moveToNext());
        }
        allRows.close();
        return tableString;
    }

}

package com.example.sendandreturn;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;

import java.util.ArrayList;
import java.util.Arrays;

import static java.security.AccessController.getContext;

public class PurchaseActivity extends AppCompatActivity {

    private ArrayList<PurchaseItem> purchaseItemList = new ArrayList<>();
    private static CustomAdapter adapter;
    private PurchaseItem editedItem;
    DatabaseHelper mDbHelper;
    SQLiteDatabase db;
    SQLiteDatabase dbRead;

    static final int ADD_ITEM = 1;
    static final int EDIT_ITEM = 2;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_buy:
                    //mTextMessage.setText(R.string.title_home);
                    return true;

                case R.id.navigation_return:
                    changeAct();
                    //mTextMessage.setText(R.string.title_notifications);
                    return true;

            }
            return false;
        }

    };


    public void changeAct() {
        Intent intent = new Intent(this, ReturnActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);

        mDbHelper = new DatabaseHelper(getApplicationContext());
        dbRead = mDbHelper.getReadableDatabase();

        Cursor cursor = dbRead.rawQuery("SELECT * FROM " + DatabaseContract.Row.TABLE_NAME, null);
        if (cursor.getCount() != 0) {
            while(cursor.moveToNext()) {
                Log.d("Purchase Activity", "Inside the Database Reader");
                String name = cursor.getString(cursor.getColumnIndexOrThrow("Name"));
                String notes = cursor.getString(cursor.getColumnIndexOrThrow("Notes"));
                String store = cursor.getString(cursor.getColumnIndexOrThrow("Store"));
                String picture = cursor.getString(cursor.getColumnIndexOrThrow("Image"));
                PurchaseItem purchaseItem = new PurchaseItem(name, store, notes, picture);
                purchaseItemList.add(purchaseItem);
            }
            cursor.close();
            dbRead.close();
        }


        if (savedInstanceState != null) {
            PurchaseItem[] purchaseItems = (PurchaseItem[]) savedInstanceState.getParcelableArray("Saved List Elements");
            purchaseItemList = new ArrayList<>(Arrays.asList(purchaseItems));
            Log.d("Hi", "HIIIII");

        }



        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        //Possibly add a calendar feature later


        //purchaseItemList.add(new PurchaseItem("Video Game", "Best Buy", "New Mario Game", "/storage/sdcard1/Pictures/wallpaper_13.jpg"));
        //purchaseItemList.add(new PurchaseItem("IPhone","Target", "Need a new phone", "/storage/sdcard1/Pictures/wallpaper_13.jpg"));


        displayList();

    }

    public void addItem(View view) {
        Intent intent = new Intent(this, ItemDetails.class);
        intent.putExtra("EDIT", false);
        startActivityForResult(intent, ADD_ITEM);
    }

    public void editItem(View view, PurchaseItem purchaseItem) {
        editedItem = purchaseItem;
        Intent intent = new Intent(this, ItemDetails.class);
        intent.putExtra("EDIT", true);
        intent.putExtra("Name", purchaseItem.getName());
        intent.putExtra("Location", purchaseItem.getStore());
        intent.putExtra("Notes", purchaseItem.getNotes());
        intent.putExtra("BitmapImage", purchaseItem.getImage());
        startActivityForResult(intent, EDIT_ITEM);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        String name = data.getStringExtra("Name");
        String location = data.getStringExtra("Location");
        String notes = data.getStringExtra("Notes");
        String picturePath = data.getStringExtra("BitmapImage");
        if (requestCode == ADD_ITEM && resultCode == Activity.RESULT_OK) {

            PurchaseItem newItem = new PurchaseItem(name, location, notes, picturePath);
            purchaseItemList.add(newItem);

        } else if (requestCode == EDIT_ITEM && resultCode == Activity.RESULT_OK) {

            editedItem.setImagePath(picturePath);
            editedItem.setName(name);
            editedItem.setStore(location);
            editedItem.setNotes(notes);

        }
        displayList();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.Row.COLUMN_NAME_NAME, name);
        values.put(DatabaseContract.Row.COLUMN_NAME_NOTES, notes);
        values.put(DatabaseContract.Row.COLUMN_NAME_STORE, location);
        values.put(DatabaseContract.Row.COLUMN_NAME_IMAGE, picturePath);
        db = mDbHelper.getWritableDatabase();
        long newRowId = db.insert(DatabaseContract.Row.TABLE_NAME, null, values);
        db.close();



    }

    private void displayList() {
        adapter = new CustomAdapter(purchaseItemList, getApplicationContext(), this);


        ListView listView = (ListView) findViewById(R.id.purchaseListView);
        listView.setAdapter(adapter);
    }


    @Override
    public void onSaveInstanceState(Bundle outstate) {
        super.onSaveInstanceState(outstate);
        Log.d("HIII", "I am in saveInstanceState");
        outstate.putParcelableArray("Saved List Elements", purchaseItemList.toArray(new PurchaseItem[purchaseItemList.size()]));
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("HIII", "I WAS STOPPED");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("HIII", "I WAS Destroyed");
    }

}

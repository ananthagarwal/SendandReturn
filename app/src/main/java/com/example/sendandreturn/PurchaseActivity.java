package com.example.sendandreturn;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PurchaseActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private ArrayList<PurchaseItem> purchaseItemList = new ArrayList<>();
    private static CustomAdapter adapter;

    static final int ADD_ITEM = 1;

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

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        //Possibly add a calendar feature later


        purchaseItemList.add(new PurchaseItem("Hi", "bye", "hi", null));
        purchaseItemList.add(new PurchaseItem("Ananth","bye", "hi", null));
        purchaseItemList.add(new PurchaseItem("Hi", "bye", "hi", null));


        displayList();

    }

    public void addItem(View view) {
        Intent intent = new Intent(this, AddItemActivity.class);
        startActivityForResult(intent, ADD_ITEM);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_ITEM && resultCode == Activity.RESULT_OK) {
            String name = data.getStringExtra("Name");
            String location = data.getStringExtra("Location");
            String notes = data.getStringExtra("Notes");
            String picturePath = data.getStringExtra("BitmapImage");

            PurchaseItem newItem = new PurchaseItem(name, location, notes, picturePath);
            purchaseItemList.add(newItem);
            displayList();

        }
    }

    private void displayList() {
        adapter = new CustomAdapter(purchaseItemList, getApplicationContext());

        ListView listView = (ListView) findViewById(R.id.purchaseListView);
        listView.setAdapter(adapter);
    }

}

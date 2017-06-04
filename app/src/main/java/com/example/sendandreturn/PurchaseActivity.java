package com.example.sendandreturn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class PurchaseActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private ArrayList<PurchaseItem> purchaseItemList = new ArrayList<>();
    private static CustomAdapter adapter;
    private PurchaseItem editedItem;

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

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        //Possibly add a calendar feature later


        purchaseItemList.add(new PurchaseItem("Hi", "bye", "hi", null));
        purchaseItemList.add(new PurchaseItem("Ananth","bye", "hi", null));


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
    }

    private void displayList() {
        adapter = new CustomAdapter(purchaseItemList, getApplicationContext(), this);

        ListView listView = (ListView) findViewById(R.id.purchaseListView);
        listView.setAdapter(adapter);

    }

}

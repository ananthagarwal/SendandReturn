package com.example.sendandreturn;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ReturnActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private List<ReturnItem> returnItemList;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_buy:
                    changeAct();
                    //onBackPressed();
                    //mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_return:
                    //mTextMessage.setText(R.string.title_notifications);

                    return true;
            }
            return false;
        }

    };
    public void changeAct() {
        Intent intent = new Intent(this, PurchaseActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return);

        //mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        returnItemList = new ArrayList<>();
        returnItemList.add(new ReturnItem("Dame", "hi", "DAME"));
        ArrayAdapter<ReturnItem> adapterReturn = new ArrayAdapter<ReturnItem>(this,
                android.R.layout.simple_list_item_1, returnItemList);

        ListView listView = (ListView) findViewById(R.id.returnListView);
        listView.setAdapter(adapterReturn);

    }

}

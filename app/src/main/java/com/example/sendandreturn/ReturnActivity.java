package com.example.sendandreturn;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReturnActivity extends AppCompatActivity {

    private ArrayList<Item> returnItemList;
    private CustomAdapter adapter;
    private Item editedItem;
    DatabaseHelper mDbHelper2;
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


        mDbHelper2 = new DatabaseHelper(getApplicationContext());
        dbRead = mDbHelper2.getReadableDatabase();

        Cursor cursor = dbRead.rawQuery("SELECT * FROM " + DatabaseContract.Row.TABLE_NAME + " WHERE "
                + DatabaseContract.Row.COLUMN_NAME_PURCHASE + " = ?", new String[]{"No"});
        if (cursor.getCount() != 0) {
            while(cursor.moveToNext()) {
                Log.d("Purchase Activity", "Inside the Database Reader");
                String name = cursor.getString(cursor.getColumnIndexOrThrow("Name"));
                String notes = cursor.getString(cursor.getColumnIndexOrThrow("Notes"));
                String store = cursor.getString(cursor.getColumnIndexOrThrow("Store"));
                String picture = cursor.getString(cursor.getColumnIndexOrThrow("Image"));
                Item item = new Item(name, store, notes, picture);
                returnItemList.add(item);
            }
            cursor.close();
            dbRead.close();
        }

        if (savedInstanceState != null) {
            Item[] items = (Item[]) savedInstanceState.getParcelableArray("Saved List Elements");
            returnItemList = new ArrayList<>(Arrays.asList(items));
            Log.d("Hi", "HIIIII");
        }

        displayList();

    }

    public void addItem(View view) {
        Intent intent = new Intent(this, ItemDetails.class);
        intent.putExtra("EDIT", false);
        startActivityForResult(intent, ADD_ITEM);
    }



    public void editItem(View view, Item item) {
        editedItem = item;
        Intent intent = new Intent(this, ItemDetails.class);
        intent.putExtra("EDIT", true);
        intent.putExtra("Name", item.getName());
        intent.putExtra("Location", item.getStore());
        intent.putExtra("Notes", item.getNotes());
        intent.putExtra("BitmapImage", item.getImage());
        startActivityForResult(intent, EDIT_ITEM);
    }

    @Override
    public void onSaveInstanceState(Bundle outstate) {
        super.onSaveInstanceState(outstate);
        Log.d("HIII", "I am in saveInstanceState");
        outstate.putParcelableArray("Saved List Elements", returnItemList.toArray(new Item[returnItemList.size()]));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        String name = data.getStringExtra("Name");
        String location = data.getStringExtra("Location");
        String notes = data.getStringExtra("Notes");
        String picturePath = data.getStringExtra("BitmapImage");
        String purchase = "No";

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.Row.COLUMN_NAME_NAME, name);
        values.put(DatabaseContract.Row.COLUMN_NAME_NOTES, notes);
        values.put(DatabaseContract.Row.COLUMN_NAME_STORE, location);
        values.put(DatabaseContract.Row.COLUMN_NAME_IMAGE, picturePath);
        values.put(DatabaseContract.Row.COLUMN_NAME_PURCHASE, purchase);

        if (requestCode == ADD_ITEM && resultCode == Activity.RESULT_OK) {

            Item newItem = new Item(name, location, notes, picturePath);
            returnItemList.add(newItem);

            db = mDbHelper2.getWritableDatabase();
            long newRowId = db.insert(DatabaseContract.Row.TABLE_NAME, null, values);
            db.close();

        } else if (requestCode == EDIT_ITEM && resultCode == Activity.RESULT_OK) {

            dbRead = mDbHelper2.getReadableDatabase();
            Log.d("Purchase Activity", "Looking for updates");
            Log.d("Purchase Activity", values.toString() + editedItem.getName() + editedItem.getImage());
            int count = dbRead.update(DatabaseContract.Row.TABLE_NAME,
                    values, DatabaseContract.Row.COLUMN_NAME_NAME + " = ? AND " + DatabaseContract.Row.COLUMN_NAME_NOTES + " = ? AND " +
                            DatabaseContract.Row.COLUMN_NAME_STORE + " = ?",
                    new String[]{editedItem.getName(), editedItem.getNotes(), editedItem.getStore()
                    });
            Log.d("Purchase Activity", mDbHelper2.getTableAsString(dbRead, DatabaseContract.Row.TABLE_NAME));
            Log.d("Purchase Activity", "" + count);
            System.out.println("HI this works");
            editedItem.setImagePath(picturePath);
            editedItem.setName(name);
            editedItem.setStore(location);
            editedItem.setNotes(notes);
            dbRead.close();
        }
        displayList();

    }

    private void displayList() {
        adapter = new CustomAdapter(returnItemList, getApplicationContext(), this);
        ListView listView = (ListView) findViewById(R.id.returnListView);
        registerForContextMenu(listView);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.delete:
                item.setChecked(true);
                delete(info.position);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void delete(int position) {
        Log.d("Purchase Activity", "Deleting " + position);
        Item toDelete = returnItemList.get(position);
        db = mDbHelper2.getWritableDatabase();

        String selection = DatabaseContract.Row.COLUMN_NAME_NAME + " = ? AND " +
                DatabaseContract.Row.COLUMN_NAME_NOTES + " = ? AND " + DatabaseContract.Row.COLUMN_NAME_STORE + " = ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { toDelete.getName(), toDelete.getNotes(), toDelete.getStore() };
        // Issue SQL statement.
        db.delete(DatabaseContract.Row.TABLE_NAME, selection, selectionArgs);

        returnItemList.remove(position);
        displayList();
    }

}

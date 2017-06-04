package com.example.sendandreturn;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import static com.example.sendandreturn.PurchaseActivity.ADD_ITEM;

public class AddItemActivity extends AppCompatActivity {

    static final int SELECT_IMAGE = 1;
    private String itemName;
    private String itemLocation;
    private String notes;
    private String picturePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

    }

    public void openGallery(View view) {

        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, SELECT_IMAGE);


    }

    public void done(View view) {
        EditText editTextName = (EditText) findViewById(R.id.editTextName);
        itemName = editTextName.getText().toString();

        EditText editTextLocation = (EditText) findViewById(R.id.editTextLocation);
        itemLocation = editTextLocation.getText().toString();

        EditText editTextNotes = (EditText) findViewById(R.id.editTextNotes);
        notes = editTextNotes.getText().toString();

        Intent intent = new Intent(this, PurchaseActivity.class);
        intent.putExtra("Name", itemName);
        intent.putExtra("Location", itemLocation);
        intent.putExtra("Notes", notes);
        intent.putExtra("BitmapImage", picturePath);
        setResult(Activity.RESULT_OK, intent);
        finish();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == SELECT_IMAGE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                super.onActivityResult(requestCode, resultCode, data);

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                picturePath = cursor.getString(columnIndex);
                cursor.close();
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.


                ImageView imageView = (ImageView) findViewById(R.id.purchaseItem);
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageURI(selectedImage);
                imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                // Do something with the contact here (bigger example below)



            }
        }
    }
}

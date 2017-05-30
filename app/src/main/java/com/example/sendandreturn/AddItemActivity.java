package com.example.sendandreturn;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AddItemActivity extends AppCompatActivity {

    static final int SELECT_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
    }

    public void openGallery(View view) {

        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, SELECT_IMAGE);


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
                String picturePath = cursor.getString(columnIndex);
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

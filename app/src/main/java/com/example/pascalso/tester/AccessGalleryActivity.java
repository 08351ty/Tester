package com.example.pascalso.tester;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

/**
 * Created by owner on 6/26/15.
 */
public class AccessGalleryActivity extends Activity {
    private static Bitmap image;
    private int REQUEST_GALLERY = 2;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        dispatchGalleryIntent();
    }

    private void dispatchGalleryIntent(){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if(galleryIntent.resolveActivity(getPackageManager()) != null)
             startActivityForResult(galleryIntent, REQUEST_GALLERY);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        //if(requestCode == REQUEST_GALLERY && resultCode == MEDIA_TYPE_IMAGE){
        Uri pickedImage = data.getData();
        String[] filePath = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
        cursor.moveToFirst();
        String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
        cursor.close();
        image = BitmapFactory.decodeFile(imagePath);
        Intent startSelectedImageFragment = new Intent(AccessGalleryActivity.this, SelectedImageFragment.class);
        startSelectedImageFragment.putExtra("calling-activity", ActivityConstants.ACCESS_GALLERY_ACTIVITY);
        startActivity(startSelectedImageFragment);
    }

    public void onPause(){
        super.onPause();
    }

    public void onResume(){
        super.onResume();
    }

    public static Bitmap getImage(){
        return image;
    }
}

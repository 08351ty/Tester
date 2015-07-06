package com.example.pascalso.tester;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * Created by owner on 6/26/15.
 */
public class AccessGalleryActivity extends Activity {
    public static Bitmap image;
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
        setContentView(R.layout.activity_selectedimage);
        ImageView imageView = (ImageView) findViewById(R.id.selectedimage);
        imageView.setImageBitmap(image);
        homeClick();
        commentClick();
        sendClick();
        //startActivity(new Intent(AccessGalleryActivity.this, SelectedImageFragment.class));
        //Make onActivityResult start SelectedImageFragment activity, put image processing in SelectedImageFragment
        //}
    }

    public void homeClick(){
        ImageButton home = (ImageButton)findViewById(R.id.returnhome);
        home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                startActivity(new Intent(AccessGalleryActivity.this, MainActivity.class));
            }
        });
    }

    public void commentClick(){
        ImageButton comment = (ImageButton)findViewById(R.id.addcomment);
        comment.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0){
                //Dialog dialog = new Dialog(context);
            }
        });
    }

    public void sendClick(){
        ImageButton send = (ImageButton) findViewById(R.id.sendimage);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                startActivity(new Intent(AccessGalleryActivity.this, PhotoInfoFragment.class));
            }
        });
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

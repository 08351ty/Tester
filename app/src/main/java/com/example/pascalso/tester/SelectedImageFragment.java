package com.example.pascalso.tester;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * Created by owner on 7/2/15.
 */
public class SelectedImageFragment extends FragmentActivity {

    private static Bitmap selectedimage;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectedimage);
        getActionBar().hide();
        drawImage();
        homeClick();
        sendClick();
        commentClick();
    }

    private void drawImage(){
        ImageView imageView = (ImageView) findViewById(R.id.selectedimage);
        int callingActivity = getIntent().getIntExtra("calling-activity", 0);
        switch (callingActivity){
            case ActivityConstants.MAIN_ACTIVITY:
                imageView.setRotation(90);
                imageView.setImageBitmap(MainActivity.getImage());
                selectedimage = MainActivity.getImage();
                break;
            case ActivityConstants.ACCESS_GALLERY_ACTIVITY:
                imageView.setImageBitmap(AccessGalleryActivity.getImage());
                selectedimage = AccessGalleryActivity.getImage();
                break;
        }

    }

    public void homeClick(){
        ImageButton home = (ImageButton)findViewById(R.id.returnhome);
        home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                startActivity(new Intent(SelectedImageFragment.this, MainActivity.class));
            }
        });
    }

    public void commentClick(){
        ImageButton comment = (ImageButton)findViewById(R.id.addcomment);
        comment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                //Dialog dialog = new Dialog(context);
            }
        });
    }

    public void sendClick(){
        ImageButton send = (ImageButton) findViewById(R.id.sendimage);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                startActivity(new Intent(SelectedImageFragment.this, PhotoInfoFragment.class));
            }
        });
    }

    public static Bitmap getImage(){
        return selectedimage;
    }

}

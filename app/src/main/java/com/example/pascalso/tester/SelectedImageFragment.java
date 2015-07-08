package com.example.pascalso.tester;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
        drawImage();
        setImage(MainActivity.getImage());
        homeClick();
        sendClick();
        commentClick();
    }

    private void drawImage(){
        ImageView imageView = (ImageView) findViewById(R.id.selectedimage);
        imageView.setImageBitmap(MainActivity.getImage());
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

    private void setImage(Bitmap image){
        selectedimage = image;
    }

    public static Bitmap getImage(){
        return selectedimage;
    }
}

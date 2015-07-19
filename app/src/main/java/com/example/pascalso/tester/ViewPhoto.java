package com.example.pascalso.tester;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.parse.ParseException;

/**
 * Created by Pascal So on 7/17/2015.
 */
public class ViewPhoto extends FragmentActivity {

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_photos);
        try {
            drawImage();
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    private void drawImage() throws ParseException {
        ImageView imageView = (ImageView) findViewById(R.id.viewphoto);
        byte[] bitmapdata = ReceivedFragment.getImage().getData();
        Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
        imageView.setImageBitmap(bitmap);
    }

    public void commentClick(){
        ImageButton comment = (ImageButton)findViewById(R.id.comment);

    }
}
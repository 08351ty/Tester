package com.example.pascalso.tester;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseUser;

/**
 * Created by Pascal So on 7/17/2015.
 */
public class StudentViewPhoto extends FragmentActivity {

    byte [] bitmapdata;
    private String comment;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentviewphotos);
        try {
            drawImage();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    commentClick();
    }

    private void drawImage() throws ParseException {
        ImageView imageView = (ImageView) findViewById(R.id.viewphoto);
        bitmapdata = StudentActivity.getImage().getData();
        Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageBitmap(bitmap);
    }

    private void commentClick() {
        ImageButton viewComment = (ImageButton)findViewById(R.id.viewreply);
        viewComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(StudentViewPhoto.this);
                alertDialog.setTitle("Comment");
                comment = StudentActivity.getComment();
                if(comment.equals(null)){
                    alertDialog.setMessage("No Comment");
                }
                else {
                    alertDialog.setMessage(comment);
                }
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.show();
            }
        });

    }
}
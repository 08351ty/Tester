package com.example.pascalso.tester;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * Created by Pascal So on 7/17/2015.
 */
public class StudentViewPhoto extends FragmentActivity {

    byte [] bitmapdata;
    private String comment;
    private static String studentId;
    private ImageButton viewChat;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentviewphotos);
        try {
            drawImage();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        commentClick();
        //showChat();
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
                if (comment.equals(null)) {
                    alertDialog.setMessage("No Comment");
                } else {
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
    /**
    private void showChat(){
        studentId = ParseUser.getCurrentUser().getObjectId();
        Toast.makeText(StudentViewPhoto.this, StudentActivity.getAnswered(), Toast.LENGTH_SHORT).show();
        if(StudentActivity.getAnswered().equals("yes")){
            viewChat = (ImageButton)findViewById(R.id.chat);
            viewChat.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Intent studentViewPhoto = new Intent(StudentViewPhoto.this, ChatActivity.class);
                    studentViewPhoto.putExtra("calling-activity", ActivityConstants.STUDENT_VIEW_PHOTO);
                    startActivity(studentViewPhoto);
                }
            });
            ParseQuery query = new ParseQuery("Message");
            query.whereEqualTo("channel", studentId + "/" + StudentActivity.getTutorId());
            query.orderByDescending("createdAt");
            query.getFirstInBackground(new GetCallback<ParseObject>() {
                public void done(ParseObject message, ParseException e) {
                    if(e == null && !message.get("userId").equals(studentId)) {
                        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(50, 50);
                        params.gravity = Gravity.CENTER_VERTICAL;
                        params.gravity = Gravity.END;
                        viewChat.setLayoutParams(params);
                        viewChat.setImageResource(R.drawable.ic_action_hasmessage);
                    }
                }
            });
        }
    }*/

    public static String getSenderId(){
        return studentId;
    }
}
package com.example.pascalso.tester;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;

/**
 * Created by Pascal So on 7/17/2015.
 */
public class TutorViewPhoto extends FragmentActivity {

    byte [] bitmapdata;
    private String[] answeroptions = {"Take A Photo", "Choose From Gallery", "Cancel"};
    private String[] sendoptions = {"Confirm", "Cancel"};
    private static String selectedObject;
    private String objectID;
    private static Bitmap selectedimage;
    private ParseFile file;
    private String answercomment;
    private static String username;
    private EditText editText;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorviewphotos);
        try {
            drawImage();
        } catch (ParseException e){
            e.printStackTrace();
        }
        replyClick();
        commentClick();
    }

    private void drawImage() throws ParseException {
        ImageView imageView = (ImageView) findViewById(R.id.viewphoto);
        bitmapdata = QuestionPick.getImage().getData();
        Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageBitmap(bitmap);
    }

    private void commentClick(){
        ImageButton comment = (ImageButton)findViewById(R.id.insertcomment);
        comment.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(TutorViewPhoto.this);
                alertDialog.setTitle("Student Comment");
                alertDialog.setMessage(QuestionPick.getComment());
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.show();
            }
        });
    }

    private void replyClick(){
        ImageButton reply = (ImageButton)findViewById(R.id.replycomment);
        reply.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (SelectedImageFragment.getImage() != null) {
                    selectedimage = SelectedImageFragment.getImage();
                    answercomment = SelectedImageFragment.getComment();
                    AlertDialog.Builder builder = new AlertDialog.Builder(TutorViewPhoto.this);
                    builder.setTitle("Send Answer?")
                            .setItems(sendoptions, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // The 'which' argument contains the index position
                                    // of the selected item
                                    switch (which) {
                                        case 0:
                                            addResponseToParse();
                                            sendPushToStudent();
                                            clearData();
                                            Toast.makeText(getApplicationContext(), "Your Reply Has Been Posted",
                                                    Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(TutorViewPhoto.this, MainActivity.class));
                                            break;
                                        case 1:
                                            break;
                                    }
                                }
                            });
                    builder.show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(TutorViewPhoto.this);
                    builder.setTitle("Provide Answer")
                            .setItems(answeroptions, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // The 'which' argument contains the index position
                                    // of the selected item
                                    switch (which) {
                                        case 0:
                                            username = QuestionPick.getUsername();
                                            startActivity(new Intent(TutorViewPhoto.this, AnswerCapture.class));
                                            break;
                                        case 1:
                                            username = QuestionPick.getUsername();
                                            startActivity(new Intent(TutorViewPhoto.this, ChoosePhotoFromGallery.class));
                                            break;
                                        case 2:
                                            break;
                                    }
                                }
                            });
                    builder.show();
                }
            }
        });
    }

    private void addResponseToParse(){
        objectID = QuestionPick.getObjectID();
        username = QuestionPick.getUsername();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        selectedimage.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bytearray = stream.toByteArray();
        if (bytearray != null) {
            file = new ParseFile("picture.png", bytearray);
            file.saveInBackground();
        }
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Questions");
        query.getInBackground(objectID, new GetCallback<ParseObject>() {
            public void done(ParseObject image, ParseException e) {
                if (e == null) {
                    String tutorId = ParseUser.getCurrentUser().getObjectId();
                    String tutorname = ParseUser.getCurrentUser().get("firstname").toString();
                    image.put("Answer", file);
                    image.put("TutorComment", answercomment + "");
                    image.put("tutorname", tutorname);
                    image.put("tutorId", tutorId);
                    image.saveInBackground();
                    Toast.makeText(getApplicationContext(), "Your Reply Has Been Posted",
                            Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void sendPushToStudent(){
        ParsePush push = new ParsePush();
        push.setChannel(username);
        push.setMessage("A tutor has responded to your question!");
        push.sendInBackground();
    }

    public static String getUsername(){
        return username;
    }

    private void clearData(){
        selectedimage = null;
        answercomment = null;
        SelectedImageFragment.setImage(null);
    }
}
package com.example.pascalso.tester;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
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
 * Created by owner on 7/2/15.
 */
public class ImageResponse extends Activity {

    private static Bitmap selectedimage;
    private EditText editText;
    private String objectID;
    private ParseFile file;
    private static String answercomment;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        setContentView(R.layout.activity_imageresponse);
        drawImage();
        sendClick();
        commentClick();
        homeClick();
    }

    private void drawImage(){
        ImageView imageView = (ImageView) findViewById(R.id.imageanswer);
        int callingActivity = getIntent().getIntExtra("calling-activity", 0);
        switch (callingActivity){
            case ActivityConstants.CHOOSE_PHOTO_FROM_GALLERY:
                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                Bitmap image = ChoosePhotoFromGallery.getImage();
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(image,1080,1920,true);
                Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap , 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setImageBitmap(rotatedBitmap);
                selectedimage = ChoosePhotoFromGallery.getImage();
                break;
            case ActivityConstants.ANSWER_CAPTURE:
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setImageBitmap(AnswerCapture.getImage());
                selectedimage = AnswerCapture.getImage();
                break;
        }
    }

    public void homeClick(){
        ImageButton home = (ImageButton)findViewById(R.id.returnhome);
        home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                startActivity(new Intent(ImageResponse.this, MainActivity.class));
                finish();
            }
        });
    }

    public void commentClick(){
        ImageButton comment = (ImageButton)findViewById(R.id.addcomment);
        comment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                //Dialog dialog = new Dialog(context);
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.activity_addcomment, null);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ImageResponse.this);
                alertDialog.setView(dialogView);
                alertDialog.setTitle("Add Comment");
                editText = (EditText)dialogView.findViewById(R.id.comment);
                editText.setText(answercomment);
                alertDialog.setPositiveButton("Add Comment", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        answercomment = editText.getText().toString();
                    }
                });
                alertDialog.show();
            }
        });
    }

    public void sendClick(){
        ImageButton send = (ImageButton) findViewById(R.id.sendimage);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //startActivity(new Intent(SelectedImageFragment.this, PhotoInfoFragment.class));
                addResponseToParse();
                sendPushToStudent();
                startActivity(new Intent(ImageResponse.this, TutorActivity.class));
                finish();
            }
        });
    }

    public static String getComment() { return answercomment; }

    public static Bitmap getImage(){
        return selectedimage;
    }

    private void addResponseToParse(){
        objectID = QuestionPick.getObjectID();
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
                    image.put("Answer", file);
                    image.put("TutorComment", answercomment);
                    image.saveInBackground();
                    Toast.makeText(getApplicationContext(), "Your Reply Has Been Posted",
                            Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    private void sendPushToStudent(){
        String username = TutorViewPhoto.getUsername();
        ParsePush push = new ParsePush();
        push.setChannel(username);
        push.setMessage("A tutor has responded to your question!");
        push.sendInBackground();
    }
}

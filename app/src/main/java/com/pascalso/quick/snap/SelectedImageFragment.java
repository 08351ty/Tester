package com.pascalso.quick.snap;

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

import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;

/**
 * Created by owner on 7/2/15.
 */
public class SelectedImageFragment extends Activity {

    private static Bitmap selectedimage;
    private static String[] subjects;
    private String subject;
    private ParseFile file;
    private String grade;
    private String username;
    private String curriculum;
    private EditText editText;
    private static String answercomment;
    private String userID;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        setContentView(R.layout.activity_selectedimage);
        drawImage();
        homeClick();
        sendClick();
    }

    private void drawImage(){
        ImageView imageView = (ImageView) findViewById(R.id.selectedimage);
        int callingActivity = getIntent().getIntExtra("calling-activity", 0);
        switch (callingActivity){
            case ActivityConstants.MAIN_ACTIVITY:
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setImageBitmap(MainActivity.getImage());
                selectedimage = MainActivity.getImage();
                break;
            case ActivityConstants.ACCESS_GALLERY_ACTIVITY:
                Bitmap image = AccessGalleryActivity.getImage();
                if (image.getHeight() > image.getWidth()){
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    imageView.setImageBitmap(image);
                }
                else {
                    Matrix matrix = new Matrix();
                    matrix.postRotate(90);
                    Bitmap scaledBitmap = Bitmap.createScaledBitmap(image, 1080, 1920, true);
                    Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    imageView.setImageBitmap(rotatedBitmap);
                }
                //imageView.setImageBitmap(AccessGalleryActivity.getImage());
                selectedimage = AccessGalleryActivity.getImage();

                break;
        }
    }

    public void homeClick(){
        ImageButton home = (ImageButton)findViewById(R.id.returnhome);
        home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                finish();
            }
        });
    }

    public void sendClick(){
        ImageButton send = (ImageButton) findViewById(R.id.sendimage);
        ParseUser user = ParseUser.getCurrentUser();
        if(user.get("usertype").equals("student")) {
            userID = ParseUser.getCurrentUser().getObjectId();
            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    //startActivity(new Intent(SelectedImageFragment.this, PhotoInfoFragment.class));
                    subjects = TutorActivity.getSubjects();
                    LayoutInflater inflater = getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.activity_choosesubject, null);
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(SelectedImageFragment.this);
                    alertDialog.setView(dialogView);
                    alertDialog.setTitle("Send Photo");
                    Spinner spinner = (Spinner)dialogView.findViewById(R.id.subjectspinner);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(SelectedImageFragment.this, android.R.layout.simple_spinner_item, subjects);
                    spinner.setAdapter(adapter);
                    editText = (EditText)dialogView.findViewById(R.id.additionalcomment);
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            Object item = parent.getItemAtPosition(position);
                            subject = item.toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    alertDialog.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            answercomment = editText.getText().toString();
                            sendPushToTutors();
                            saveImageToParse();
                        }
                    });
                    alertDialog.show();

                }
            });
        }
        else{
            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0){
                    LayoutInflater inflater = getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.activity_addcomment, null);
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(SelectedImageFragment.this);
                    alertDialog.setView(dialogView);
                    alertDialog.setTitle("Add Comment");
                    editText = (EditText)dialogView.findViewById(R.id.comment);
                    alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            answercomment = editText.getText().toString();
                            Intent tutorActivity = new Intent(SelectedImageFragment.this, TutorActivity.class);
                            tutorActivity.putExtra("calling-activity", ActivityConstants.SELECTED_IMAGE_FRAGMENT);
                            startActivity(tutorActivity);
                            finish();
                        }
                    });
                    alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(SelectedImageFragment.this, MainActivity.class));
                            finish();
                        }
                    });
                    alertDialog.show();
                }
            });
        }
    }
    public static Bitmap getImage(){
        return selectedimage;
    }

    public static void setImage(Bitmap image) {
        selectedimage = image;
    }

    public static String getComment() { return answercomment; }

    private void saveImageToParse(){
        ParseUser user = ParseUser.getCurrentUser();
        grade = user.get("grade").toString();
        username = user.get("username").toString();
        curriculum = user.get("curriculum").toString();

        /**
         if (SelectedImageFragment.getImage() == null) {
         selectedImage = AccessGalleryActivity.getImage();
         }
         else {
         selectedImage = SelectedImageFragment.getImage();
         }
         */
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        selectedimage.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bytearray = stream.toByteArray();
        if (bytearray != null) {
            file = new ParseFile("picture.png", bytearray);
            file.saveInBackground();
        }
        ParseObject x = new ParseObject("Questions");
        x.put("mediatype", "image");
        x.put("username", username);
        x.put("subject", subject);
        x.put("grade", grade);
        x.put("curriculum", curriculum);
        x.put("comment", answercomment);
        x.put("ImageFile", file);
        x.put("studentID", userID);
        x.saveInBackground();
        Toast.makeText(getApplicationContext(), "Your Image Has Been Sent!",
                Toast.LENGTH_SHORT).show();
        Intent i = new Intent(SelectedImageFragment.this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }



    private void sendPushToTutors(){
        ParsePush push = new ParsePush();
        push.setChannel("Tutor");
        push.setMessage("A new question has been posted!");
        push.sendInBackground();
    }
}

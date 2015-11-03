package com.pascalso.quick.snap;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by owner on 7/3/15.
 */
public class PhotoInfoFragment extends FragmentActivity{
    String grade;
    String subject;
    String working = "no";
    Bitmap selectedImage;
    String username;
    File imageFile;
    ParseFile file;
    private static String imageLink;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ParseUser currentUser = ParseUser.getCurrentUser();
        username = currentUser.getUsername();
        selectSubject();
        selectGrade();
        showWorking();
        sendPic();

    }

    private void selectSubject(){
        Spinner dropdown = (Spinner)findViewById(R.id.spinner1);
        String[] items = new String[]{"--Select--","Math", "Physics", "English"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                subject = item.toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void selectGrade(){
        Spinner dropdown = (Spinner)findViewById(R.id.spinner2);
        String[] items = new String[]{"--Select--","1", "2", "3", "4", "5","6","7","8","9","10","11","12"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                grade = item.toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void sendPic(){
        FrameLayout goButton = (FrameLayout) findViewById(R.id.gobutton);
        goButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (grade.equals("--Select--") && subject.equals("--Select--")) {
                    Toast.makeText(getApplicationContext(), "Please select your grade and subject",
                            Toast.LENGTH_SHORT).show();
                } else if (grade.equals("--Select--") && !subject.equals("--Select--")) {
                    Toast.makeText(getApplicationContext(), "Please select your grade",
                            Toast.LENGTH_SHORT).show();
                } else if (!grade.equals("--Select--") && subject.equals("--Select--")) {
                    Toast.makeText(getApplicationContext(), "Please select your subject",
                            Toast.LENGTH_SHORT).show();
                } else {
                    saveImageToParse();
                    /**
                    String [] args = {};

                    BestImage bestImage = new BestImage();
                    Toast.makeText(getApplicationContext(), bestImage.getHIT(),
                            Toast.LENGTH_SHORT).show();
                     */

                    //startActivity(new Intent(PhotoInfoFragment.this, MainActivity.class));
                }
            }
        });
    }

    private void saveImageToParse(){
        /**
        if (SelectedImageFragment.getImage() == null) {
            selectedImage = AccessGalleryActivity.getImage();
        }
        else {
            selectedImage = SelectedImageFragment.getImage();
        }
         */

        selectedImage = SelectedImageFragment.getImage();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        selectedImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bytearray = stream.toByteArray();
        if (bytearray != null) {
            file = new ParseFile("picture.png", bytearray);
            file.saveInBackground();
        }
        ParseObject x = new ParseObject("Questions");
        x.put("mediatype", "image");
        x.put("grade", grade);
        x.put("subject", subject);
        x.put("working", working);
        x.put("username", username);
        x.put("ImageFile", file);
        x.put("posted", false);
        x.saveInBackground();
        setImageLink();
        Toast.makeText(getApplicationContext(), imageLink,
                Toast.LENGTH_SHORT).show();
        startActivity(new Intent(PhotoInfoFragment.this, MainActivity.class));
    }

    public File postImageToMTurk(){
        selectedImage = SelectedImageFragment.getImage();
        persistImage(selectedImage, "image file");
        return imageFile;

    }

    private void persistImage(Bitmap bitmap, String name){
        File filesdir = this.getFilesDir();
        imageFile = new File(filesdir, name + "png");
        OutputStream os;
        try{
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
            os.flush();
            os.close();
        }
        catch (Exception e){
            Log.e(getClass().getSimpleName(), "Error compiling bitmap", e);
        }
    }

    private void setImageLink(){
        ParseQuery<ParseObject> query = new ParseQuery<>(username);
        query.orderByDescending("updatedAt");
        try {
            ParseFile image = query.getFirst().getParseFile("ImageFile");
            imageLink = image.getUrl();
        }
        catch(ParseException e) {
            Log.d("ERROR", "could not find image");
        }
    }

    public static String getImageLink(){
        return imageLink;
    }

    protected void onPause(){
        super.onPause();
    }

    protected void onResume(){
        super.onResume();
    }

    protected void onRestart(){
        super.onRestart();
    }

    private void showWorking(){
        Switch showWorkingSwitch = (Switch) findViewById(R.id.switch1);
        showWorkingSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if(isChecked) working = "yes";
                else working = "no";
            }
        });
    }

    private class PublishHIT extends AsyncTask<String, Integer , String> {
        protected String doInBackground(String... params){
            return "";
        }
    }
}

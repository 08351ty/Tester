package com.example.pascalso.tester;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.parse.ParseFile;
import com.parse.ParseObject;

import java.io.ByteArrayOutputStream;

/**
 * Created by owner on 7/3/15.
 */
public class PhotoInfoFragment extends FragmentActivity{
    String grade;
    String subject;
    String working = "no";
    Bitmap selectedImage;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
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
        Button goButton = (Button) findViewById(R.id.gobutton);
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
                    Toast.makeText(getApplicationContext(), "Your problem has been sent!",
                            Toast.LENGTH_SHORT).show();
                    saveImageToParse();
                    startActivity(new Intent(PhotoInfoFragment.this, MainActivity.class));

                }

            }
        });

    }

    private void saveImageToParse() {
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
        selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bytearray = stream.toByteArray();
        ParseObject x = new ParseObject("ReceivedPictures");
        x.put("mediatype", "image");
        x.put("grade", grade);
        x.put("subject", subject);
        x.put("working", working);
        if (bytearray != null) {
            ParseFile file = new ParseFile("Picture", bytearray);
            file.saveInBackground();
            x.put("TestPic1", file);
        }
        x.saveInBackground();
    }

    protected void onPause(){
        super.onPause();
    }

    protected void onResume(){
        super.onResume();
    }

    protected void onRestart(){

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
}

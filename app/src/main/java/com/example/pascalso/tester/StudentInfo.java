package com.example.pascalso.tester;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * Created by Pascal So on 7/17/2015.
 */
public class StudentInfo extends Activity {
    String grade;
    String curriculum;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        selectGrade();
        selectCurriculum();
        confirmInfo();
    }

    private void selectGrade(){
        Spinner dropdown = (Spinner)findViewById(R.id.spinner1);
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

    private void selectCurriculum(){
        Spinner dropdown = (Spinner)findViewById(R.id.spinner2);
        String[] items = new String[]{"--Select--","IB", "IBMYP", "GCSE", "AP", "A Levels", "O Levels", "HKDSE", "Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                curriculum = item.toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void confirmInfo(){
        Button confirminfo = (Button)findViewById(R.id.confirmbutton);
        confirminfo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ParseUser user = new ParseUser();
                user.setUsername(NewUserActivity.getFirstName());
                user.setEmail(NewUserActivity.getEmail());
                user.setPassword(NewUserActivity.getPassword());
                user.put("usertype", "student");
                user.put("grade", grade);
                user.put("curriculum", curriculum);
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e == null){
                            startActivity(new Intent(StudentInfo.this, SplashActivity.class));
                        }
                        else{

                        }
                    }
                });
            }
        });
    }
}

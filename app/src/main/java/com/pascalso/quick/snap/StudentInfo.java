package com.pascalso.quick.snap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * Created by Pascal So on 7/17/2015.
 */
public class StudentInfo extends Activity {
    String grade;
    String curriculum;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);
        selectGrade();
        selectCurriculum();
        confirmInfo();
    }

    private void selectGrade(){
        Spinner dropdown = (Spinner)findViewById(R.id.gradespinner);
        String[] items = new String[]{"--Grade--","1", "2", "3", "4", "5","6","7","8","9","10","11","12"};
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
        Spinner dropdown = (Spinner)findViewById(R.id.curriculumspinner);
        String[] items = new String[]{"--Curriculum--","IB", "IBMYP", "GCSE", "AP", "A Levels", "O Levels", "HKDSE", "Other"};
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
        Button confirminfo = (Button)findViewById(R.id.savestudentinfobutton);
        confirminfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logInInBackground(NewUserActivity.getUserName(), NewUserActivity.getPassword(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (!user.getBoolean("emailVerified")) {
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(StudentInfo.this);
                            alertDialog.setTitle("Verify Email");
                            alertDialog.setMessage("It looks like you haven't verified your email yet. Please click the link in the email sent.");
                            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            alertDialog.show();
                        } else {
                            user.put("usertype", "student");
                            user.put("grade", grade);
                            user.put("curriculum", curriculum);
                            user.saveInBackground(new SaveCallback() {
                                public void done(ParseException e) {
                                    if (e == null) {
                                        Toast.makeText(getApplicationContext(), "Yay! Your New Account Has Been Created",
                                                Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(StudentInfo.this, SplashActivity.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(i);
                                    } else {
                                        Log.e("Error", e.getMessage());
                                    }
                                }

                                ;
                            });
                        }
                    }
                });
            }
        });
    }

            protected void onStart() {
                super.onStart();
            }

            protected void onPause() {
                super.onPause();
            }

            protected void onResume() {
                super.onResume();
            }

            protected void onStop() {
                super.onStop();
            }

            protected void onDestroy() {
                super.onDestroy();
            }

            protected void onRestart() {
                super.onRestart();
            }
        }
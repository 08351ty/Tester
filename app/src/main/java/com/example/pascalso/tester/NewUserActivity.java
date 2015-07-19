package com.example.pascalso.tester;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by Pascal So on 7/17/2015.
 */
public class NewUserActivity extends Activity {
    public static String email;
    public static String password;
    public static String firstname;
    public static String username;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        confirmInfo();
        checkUsername();
        checkEmail();
    }

    private void confirmInfo(){
        ImageButton confirminfo = (ImageButton)findViewById(R.id.right);
        confirminfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText viewemail = (EditText) findViewById(R.id.email);
                EditText viewpassword = (EditText) findViewById(R.id.password);
                EditText viewusername = (EditText) findViewById(R.id.username);
                EditText viewfirstname = (EditText) findViewById(R.id.firstname);
                email = viewemail.getText().toString();
                password = viewpassword.getText().toString();
                firstname = viewfirstname.getText().toString();
                username = viewusername.getText().toString();
                String verification = email.substring(email.length() - 3);
                if (verification.equals("edu")) {
                    startActivity(new Intent(NewUserActivity.this, TutorInfo.class));
                } else {
                    startActivity(new Intent(NewUserActivity.this, StudentInfo.class));
                }
            }
        });
    }

    private void checkUsername(){
        ((EditText)findViewById(R.id.username)).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    ParseQuery<ParseUser> query = ParseUser.getQuery();
                    query.whereEqualTo("username", username);
                    query.findInBackground(new FindCallback<ParseUser>() {
                        public void done(List<ParseUser> objects, ParseException e) {
                            if (e == null){
                                if(objects.size() != 0){
                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(NewUserActivity.this);
                                    alertDialog.setTitle("Username Taken");
                                    alertDialog.setMessage("This Username has already been taken. Please choose another Username.");
                                    alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                                    alertDialog.show();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "ParseUser size = 0",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Log.e("Error", e.getMessage());
                                Toast.makeText(getApplicationContext(), "Your problem has been sent!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                    });
                }
            }
        });
    }

    private void checkEmail(){
        ((EditText)findViewById(R.id.email)).setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    ParseQuery<ParseUser> query = ParseUser.getQuery();
                    query.whereEqualTo("email", email);
                    query.findInBackground(new FindCallback<ParseUser>(){
                        public void done(List<ParseUser> objects, ParseException e){
                            if(e != null){
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(NewUserActivity.this);
                                alertDialog.setTitle("Email used");
                                alertDialog.setMessage("This Email is already in use. Have you already created an account?");
                                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                alertDialog.show();
                            }
                        }
                    });
                }
            }
        });
    }

    public static String getEmail(){
        return email;
    }

    public static String getPassword(){
        return password;
    }

    public static String getFirstName(){
        return firstname;
    }

    public static String getUserName() {
        return username;
    }


    protected void onStart(){
        super.onStart();
    }

    protected void onPause(){
        super.onPause();
    }

    protected void onResume(){
        super.onResume();
        EditText viewemail = (EditText)findViewById(R.id.email);
        EditText viewpassword = (EditText)findViewById(R.id.password);
        EditText viewusername = (EditText)findViewById(R.id.username);
        EditText viewfirstname = (EditText)findViewById(R.id.firstname);
        viewemail.setText(email, TextView.BufferType.EDITABLE);
        viewpassword.setText(password, TextView.BufferType.EDITABLE);
        viewusername.setText(username, TextView.BufferType.EDITABLE);
        viewfirstname.setText(firstname, TextView.BufferType.EDITABLE);

    }

    protected void onStop() { super.onStop(); }

    protected void onDestroy() { super.onDestroy(); }

    protected void onRestart(){
        super.onRestart();

    }
}

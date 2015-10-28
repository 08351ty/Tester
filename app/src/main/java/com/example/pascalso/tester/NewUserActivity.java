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

import com.parse.CountCallback;
import com.parse.ParseException;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

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
        getActionBar().setDisplayHomeAsUpEnabled(true);
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
                final String verification = email.substring(email.length() - 3);
                ParseUser user = new ParseUser();
                user.setUsername(username);
                user.setEmail(email);
                user.setPassword(password);
                user.put("firstname", firstname);

                //user.put("college", viewcollege.getText().toString());
                user.signUpInBackground(new SignUpCallback(){
                    @Override
                    public void done(ParseException e){
                        if (e == null) {
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(NewUserActivity.this);
                            alertDialog.setTitle("Verify Email");
                            alertDialog.setMessage("We've sent you an Email at " + email + ". Please take a second to verify your account");
                            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    if (verification.equals("edu")) {
                                        ParsePush.subscribeInBackground("Tutor");
                                        startActivity(new Intent(NewUserActivity.this, TutorInfo.class));
                                    } else {
                                        //ParsePush.subscribeInBackground("Student");
                                        ParsePush.subscribeInBackground(username);
                                        startActivity(new Intent(NewUserActivity.this, StudentInfo.class));
                                    }
                                }
                            });
                            alertDialog.show();

                        } else {
                            Log.e("Error", e.getMessage());
                        }
                    }

                });

            }
        });
    }

    private void checkUsername(){
        ((EditText)findViewById(R.id.username)).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    EditText viewusername = (EditText) findViewById(R.id.username);
                    username = viewusername.getText().toString();
                    ParseQuery<ParseUser> query = ParseUser.getQuery();
                    query.whereEqualTo("username", username);
                    query.countInBackground(new CountCallback(){
                        public void done(int count, ParseException e) {
                            if (e == null){
                                if(count != 0){
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
                                    char c = username.charAt(0);
                                    if(Character.isLetter(c)){
                                    }
                                    else{
                                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(NewUserActivity.this);
                                        alertDialog.setTitle("Username");
                                        alertDialog.setMessage("Your username must start with a letter.");
                                        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });
                                        alertDialog.show();
                                    }
                                }
                            }
                            else{
                                Log.e("Error", e.getMessage());
                                Toast.makeText(getApplicationContext(), "There has been an error",
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
                    EditText viewemail = (EditText) findViewById(R.id.email);
                    email = viewemail.getText().toString();
                    ParseQuery<ParseUser> query = ParseUser.getQuery();
                    query.whereEqualTo("email", email);
                    query.countInBackground(new CountCallback(){
                        public void done(int count, ParseException e) {
                            if (e == null) {
                                if (count != 0) {
                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(NewUserActivity.this);
                                    alertDialog.setTitle("Email In Use");
                                    alertDialog.setMessage("This Email is already in use. Have you already created an account?");
                                    alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            startActivity(new Intent(NewUserActivity.this, LoginActivity.class));
                                        }
                                    });
                                    alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener(){
                                        public void onClick(DialogInterface dialog, int which){
                                            dialog.cancel();
                                        }
                                    });
                                    alertDialog.show();
                                } else{
                                    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
                                    {
                                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(NewUserActivity.this);
                                        alertDialog.setTitle("Invalid Email");
                                        alertDialog.setMessage("Please enter a valid email.");
                                        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        });
                                        alertDialog.show();
                                    }
                                }
                            }
                            else{
                                Log.e("Error", e.getMessage());
                                Toast.makeText(getApplicationContext(), "There has been an error",
                                        Toast.LENGTH_SHORT).show();
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

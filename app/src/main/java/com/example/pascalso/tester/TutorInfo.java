package com.example.pascalso.tester;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * Created by Pascal So on 7/17/2015.
 */
public class TutorInfo extends Activity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_info);
        confirmInfo();

    }
    private void confirmInfo(){
        Button confirminfo = (Button)findViewById(R.id.savetutorinfobutton);
        confirminfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logInInBackground(NewUserActivity.getUserName(), NewUserActivity.getPassword(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if(!user.getBoolean("emailVerified")){
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(TutorInfo.this);
                            alertDialog.setTitle("Verify Email");
                            alertDialog.setMessage("It looks like you haven't verified your email yet. Please click the link in the email sent.");
                            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            alertDialog.show();
                        }
                        else{
                            EditText viewcollege = (EditText)findViewById(R.id.collegename);
                            user.put("college", viewcollege.getText().toString());
                            user.put("usertype", "tutor");
                            user.saveInBackground(new SaveCallback(){
                                public void done(ParseException e){
                                    if(e == null){
                                        startActivity(new Intent(TutorInfo.this, MainActivity.class));
                                    }
                                    else{
                                        Log.e("Error", e.getMessage());
                                    }
                                }
                                ;
                            });
                        }
                    }
                });

                /**
                ParseUser user = new ParseUser();
                user.setUsername(NewUserActivity.getUserName());
                user.setEmail(NewUserActivity.getEmail());
                user.setPassword(NewUserActivity.getPassword());
                user.put("firstname", NewUserActivity.getFirstName());
                user.put("usertype", "tutor");
                user.put("college", viewcollege.getText().toString());
                user.signUpInBackground(new SignUpCallback(){
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(getApplicationContext(), "Yay! Your New Account Has Been Created",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(TutorInfo.this, MainActivity.class));
                        }
                        else {
                            Log.e("Error", e.getMessage());
                        }
                    }
                });
                 */
            }
        });
    }

    protected void onStart(){
        super.onStart();
    }

    protected void onPause(){
        super.onPause();
    }

    protected void onResume(){
        super.onResume();
    }

    protected void onStop() {
        super.onStop();
    }

    protected void onDestroy() { super.onDestroy(); }

    protected void onRestart(){
        super.onRestart();
    }
}
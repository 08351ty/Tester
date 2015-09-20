package com.example.pascalso.tester;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by Pascal So on 7/20/2015.
 */
public class LoginActivity extends Activity {
    private static ParseQuery<ParseObject> query;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        login();
    }
    private void login(){
        FrameLayout login = (FrameLayout)findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                EditText viewusername = (EditText)findViewById(R.id.loginusername);
                EditText viewpassword = (EditText)findViewById(R.id.loginpassword);
                String username = viewusername.getText().toString();
                String password = viewpassword.getText().toString();
                ParseUser.logInInBackground(username, password, new LogInCallback() {
                    @Override
                    public void done(ParseUser parseUser, ParseException e) {
                        if (parseUser != null){
                            startActivity(new Intent(LoginActivity.this, SplashActivity.class));
                            finish();
                        }

                        else{
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this);
                            alertDialog.setTitle("Account doesn't exist");
                            alertDialog.setMessage("This account doesn't exist in our database.");
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
        });
    }
}

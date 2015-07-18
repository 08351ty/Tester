package com.example.pascalso.tester;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Pascal So on 7/17/2015.
 */
public class NewUserActivity extends Activity {
    public static String email;
    public static String password;
    public static String firstname;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        confirmInfo();
    }

    private void confirmInfo(){
        Button confirminfo = (Button)findViewById(R.id.confirmbutton);
        confirminfo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                EditText viewemail = (EditText)findViewById(R.id.email);
                EditText viewpassword = (EditText)findViewById(R.id.password);
                EditText viewfirstname = (EditText)findViewById(R.id.firstname);
                email = viewemail.getText().toString();
                password = viewpassword.getText().toString();
                firstname = viewfirstname.getText().toString();
                String email = viewemail.getText().toString();
                String verification = email.substring(email.length() - 3);
                if(verification.equals("edu")){
                    startActivity(new Intent(NewUserActivity.this, TutorInfo.class));
                }
                else{
                    startActivity(new Intent(NewUserActivity.this, StudentInfo.class));
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
}

package com.example.pascalso.tester;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by Pascal So on 7/20/2015.
 */
public class UserVerification extends Activity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_verification);
        loginAccount();
        newAccount();

    }

    private void loginAccount(){
        FrameLayout login = (FrameLayout)findViewById(R.id.loginaccount);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserVerification.this, LoginActivity.class));
            }
        });
    }

    private void newAccount(){
        FrameLayout newaccount = (FrameLayout)findViewById(R.id.createaccount);
        newaccount.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(UserVerification.this, NewUserActivity.class));
            }
        });

    }
}

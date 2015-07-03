package com.example.pascalso.tester;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by owner on 7/3/15.
 */
public class PhotoInfoFragment extends FragmentActivity{

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        selectSubject();
        selectGrade();
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PhotoInfoFragment.this, MainActivity.class));
            }
        });
    }

    private void selectSubject(){
        Spinner dropdown = (Spinner)findViewById(R.id.spinner1);
        String[] items = new String[]{"--Select--","Math", "Physics", "English"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        dropdown.setAdapter(adapter);
    }

    private void selectGrade(){
        Spinner dropdown = (Spinner)findViewById(R.id.spinner2);
        String[] items = new String[]{"--Select--","1", "2", "3", "4", "5","6","7","8","9","10","11","12"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        dropdown.setAdapter(adapter);
    }
}

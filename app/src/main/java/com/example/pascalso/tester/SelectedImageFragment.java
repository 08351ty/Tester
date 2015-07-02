package com.example.pascalso.tester;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by owner on 7/2/15.
 */
public class SelectedImageFragment extends FragmentActivity {

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectedimage);
    }

    public SelectedImageFragment(){
        homeClick();
    }

    public void homeClick(){
        ImageButton home = (ImageButton)findViewById(R.id.returnhome);
        home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                setContentView(R.layout.activity_main);
            }
        });
    }

    public void commentClick(){
        ImageButton comment = (ImageButton)findViewById(R.id.addcomment);
        comment.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0){
                //Dialog dialog = new Dialog(context);
            }
        });
    }


}

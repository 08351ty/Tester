package com.example.pascalso.tester;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.parse.ParsePush;
import com.parse.ParseUser;

/**
 * Created by Pascal So on 7/23/2015.
 */
public class TutorActivity extends Activity {
    private static String [] subjects = {"Biology", "Chemistry", "Computer Science", "Economics", "Maths", "Physics"};
    private static String chosensubject;
    private static Bitmap selectedimage;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridlist);
        ParsePush.subscribeInBackground("Tutor");
        getActionBar().setTitle("Subject");
        getActionBar().setDisplayHomeAsUpEnabled(true);
        GridView gridview = (GridView)findViewById(R.id.gridview);
        gridview.setAdapter(new SubjectImageAdapter(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                setChosenSubject(subjects[position]);
                int callingActivity = getIntent().getIntExtra("calling-activity", 0);
                switch (callingActivity){
                    case ActivityConstants.SELECTED_IMAGE_FRAGMENT:
                        setImage(SelectedImageFragment.getImage());
                        break;
                    case ActivityConstants.MAIN_ACTIVITY:
                        break;
                }
                startActivity(new Intent(TutorActivity.this, QuestionPick.class));
                Toast.makeText(TutorActivity.this, subjects[position],
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static String[] getSubjects(){
        return subjects;
    }

    private void setChosenSubject(String subject){
        chosensubject = subject;
    }

    public static String getChosenSubject(){
        return chosensubject;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_settings:
                startActivity(new Intent(TutorActivity.this, PrefsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static Bitmap getImage(){
        return selectedimage;
    }

    public void setImage(Bitmap image){
        selectedimage = image;
    }
}

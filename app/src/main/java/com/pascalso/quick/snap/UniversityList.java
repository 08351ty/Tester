package com.pascalso.quick.snap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

/**
 * Created by owner on 8/25/15.
 */
public class UniversityList extends Activity {
    private static String selectedcollege;
    private static String[] colleges = {"brown", "chicago", "emory", "swarthmore"};
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_universitylist);
        getActionBar().setTitle("Tutors");
        getActionBar().setDisplayHomeAsUpEnabled(true);
        GridView gridview = (GridView)findViewById(R.id.gridview);
        gridview.setAdapter(new UniversityImageAdapter(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id){
                setCollege(colleges[position]);
                /**
                Toast.makeText(UniversityList.this, "" + position,
                        Toast.LENGTH_SHORT).show();
                 */
                startActivity(new Intent(UniversityList.this, TutorCardView.class));
                Toast.makeText(UniversityList.this, colleges[position],
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_profile:
                startActivity(new Intent(UniversityList.this, StudentActivity.class));
                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setCollege(String college){
        selectedcollege = college;
    }

    public static String getCollege(){
        return selectedcollege;
    }
}

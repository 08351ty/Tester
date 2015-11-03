package com.pascalso.quick.snap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Created by owner on 8/25/15.
 */
public class TutorCardView extends Activity{
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    private String college;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        college = UniversityList.getCollege();
        switch(college){
            case "brown":
                getActionBar().setTitle("Brown");
                break;
            case "chicago":
                getActionBar().setTitle("Chicago");
                break;
            case "emory":
                getActionBar().setTitle("Emory");
                break;
            case "swarthmore":
                getActionBar().setTitle("Swarthmore");
                break;
        }
        getActionBar().setDisplayHomeAsUpEnabled(true);
        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new TutorCardViewAdapter();
        mRecyclerView.setAdapter(mAdapter);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_profile:
                startActivity(new Intent(TutorCardView.this, UniversityList.class));
                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

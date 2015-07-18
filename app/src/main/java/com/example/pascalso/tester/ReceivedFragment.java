package com.example.pascalso.tester;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.ParseFile;

import java.util.ArrayList;

/**
 * Created by owner on 7/2/15.
 */
public class ReceivedFragment extends Activity {
    private ParseFile image;
    ListView listView;
    Integer[] imgid = { R.drawable.ic_action_waiting, R.drawable.ic_action_done };
    ArrayList<String> timecreated = new ArrayList<>();
    ArrayList<String> subjects = new ArrayList<>();
    ArrayList<ParseFile> photos = new ArrayList<>();
    static ParseFile selectedimage;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received);
        timecreated = SplashActivity.getTimeCreated();
        subjects = SplashActivity.getSubjects();
        photos = SplashActivity.getPhotos();
        CustomListAdapter adapter = new CustomListAdapter(this, subjects, timecreated, imgid);
        listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                selectedimage = photos.get(+position);
                startActivity(new Intent(ReceivedFragment.this, ViewPhoto.class));
            }
        });
    }

    public static ParseFile getImage(){
        return selectedimage;
    }
}


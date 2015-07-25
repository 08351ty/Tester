package com.example.pascalso.tester;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.CountCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by owner on 7/2/15.
 */
public class StudentActivity extends Activity {
    ListView listView;
    Integer[] imgid = { R.drawable.ic_action_waiting, R.drawable.ic_action_done };
    ArrayList<String> timecreated = new ArrayList<>();
    ArrayList<String> subjects = new ArrayList<>();
    ArrayList<ParseFile> photos = new ArrayList<>();
    ArrayList<ParseObject> parseObjects = new ArrayList<>();
    static ArrayList<Date> dates = new ArrayList<>();
    int minute;
    int hour;
    int day;
    int month;

    static ParseFile selectedimage;
    String username;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        ParseUser currentUser = ParseUser.getCurrentUser();
        username = currentUser.getUsername();
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
                startActivity(new Intent(StudentActivity.this, ViewPhoto.class));
            }
        });
        autoUpdate();
    }

    public static ParseFile getImage(){
        return selectedimage;
    }

    private void autoUpdate(){
        final ParseQuery<ParseObject> query = ParseQuery.getQuery(username);
        query.countInBackground(new CountCallback() {
            @Override
            public void done(int i, ParseException e) {
                if(i != photos.size()){
                    query.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e){
                            if(e == null){
                                parseObjects.addAll(objects);
                                timecreated.clear();
                                subjects.clear();
                                photos.clear();
                                dates.clear();
                                int x = 0;
                                while (x < parseObjects.size()){
                                    String subject = parseObjects.get(x).getString("subject");
                                    Date date = parseObjects.get(x).getCreatedAt();
                                    ParseFile photo = parseObjects.get(x).getParseFile("ImageFile");
                                    subjects.add(subject);
                                    dates.add(date);
                                    photos.add(photo);
                                    x++;
                                }
                                Collections.reverse(subjects);
                                Collections.reverse(dates);
                                Collections.reverse(photos);
                                compareDate();
                                CustomListAdapter adapter = new CustomListAdapter(StudentActivity.this, subjects, timecreated, imgid);
                                listView = (ListView)findViewById(R.id.list);
                                listView.setAdapter(adapter);
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view,
                                                            int position, long id) {
                                        // TODO Auto-generated method stub
                                        selectedimage = photos.get(+position);
                                        startActivity(new Intent(StudentActivity.this, ViewPhoto.class));
                                    }
                                });
                            }
                            else{
                                Log.e("Error", e.getMessage());
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(getApplicationContext(), "wtf?",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void compareDate(){
        Calendar calendar = Calendar.getInstance();
        minute = calendar.get(Calendar.MINUTE);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        int x = 0;
        while (x < dates.size()){
            calendar.setTime(dates.get(x));
            if(calendar.get(Calendar.HOUR_OF_DAY) == hour && calendar.get(Calendar.DAY_OF_MONTH) == day){
                int b = minute - calendar.get(Calendar.MINUTE);
                if(b < 2){
                    timecreated.add("Just now");
                }
                else{
                    timecreated.add(b + " minutes ago");
                }
            }
            else{
                if (calendar.get(Calendar.DAY_OF_MONTH) == day) {
                    int c = hour - calendar.get(Calendar.HOUR_OF_DAY);
                    if (c == 1) {
                        timecreated.add(c + " hour ago");
                    } else {
                        timecreated.add(c + " hours ago");
                    }
                } else {
                    int d = day - calendar.get(Calendar.DAY_OF_MONTH);
                    if (d == 1) {
                        timecreated.add(d + " day ago");
                    } else {
                        timecreated.add(d + " days ago");
                    }
                }
            }
            x++;
        }
    }

}


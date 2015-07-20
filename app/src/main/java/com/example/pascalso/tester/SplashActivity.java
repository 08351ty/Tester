package com.example.pascalso.tester;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

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
 * Created by Pascal So on 7/17/2015.
 */
public class SplashActivity extends Activity{

    ArrayList<ParseObject> parseObjects = new ArrayList<>();
    static ArrayList<Date> dates = new ArrayList<>();
    static ArrayList<String> timecreated = new ArrayList<>();
    static ArrayList<String> subjects = new ArrayList<>();
    static ArrayList<ParseFile> photos = new ArrayList<>();
    int day;
    int month;
    int hour;
    int minute;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        /**
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        if (settings.getBoolean("my_first_time", true)) {
            //the app is being launched for first time, do something
            // first time task
            // record the fact that the app has been started at least once
            settings.edit().putBoolean("my_first_time", false).commit();
            Log.d("Comments", "First tim"e);
            startActivity(new Intent(SplashActivity.this, UserVerification.class));
        }
         */
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                ParseUser user = ParseUser.getCurrentUser();
                if(user == null){
                    startActivity(new Intent(SplashActivity.this, UserVerification.class));
                }
                else{
                    ParseQuery<ParseObject> query = ParseQuery.getQuery(user.getUsername());
                    query.whereEqualTo("mediatype", "image");
                    try {
                        List<ParseObject> photos = query.find();
                        parseObjects.addAll(photos);
                    } catch (ParseException e) {
                    }
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
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            }
        }, 1000);
    }


    protected void onStart(){
        super.onStart();
    }

    protected void onPause(){
        super.onPause();
    }

    protected void onResume(){
        super.onResume();
    }

    protected void onStop() {
        super.onStop();
    }

    protected void onRestart(){
        super.onRestart();
    }

    protected void onDestroy(){
        super.onDestroy();
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
                if(b == 1){
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

    public static ArrayList<String> getTimeCreated(){
        return timecreated;
    }

    public static ArrayList<String> getSubjects(){
        return subjects;
    }

    public static ArrayList<ParseFile> getPhotos(){
        return photos;
    }
}

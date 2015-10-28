package com.example.pascalso.tester;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    private ListView listView;
    private Integer[] imgid = { R.drawable.ic_action_waiting, R.drawable.ic_action_done };
    private ArrayList<String> timecreated = new ArrayList<>();
    private ArrayList<String> subjects = new ArrayList<>();
    private ArrayList<ParseFile> photos = new ArrayList<>();
    private ArrayList<ParseObject> parseObjects = new ArrayList<>();
    private static ArrayList<Date> dates = new ArrayList<>();
    private static ArrayList<String> answered = new ArrayList<>();
    private static ArrayList<String> comments = new ArrayList<>();
    private static ArrayList<String> tutorIds = new ArrayList<>();
    private int minute;
    private int hour;
    private int day;
    private int month;
    private static ParseFile selectedimage;
    private String username;
    private static String comment;
    private static String answer;
    private Handler handler = new Handler();
    private static String tutorId;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getActionBar();
        actionBar.setTitle("Photos");
        setContentView(R.layout.activity_student);
        ParseUser currentUser = ParseUser.getCurrentUser();
        username = currentUser.getUsername();
        answered = SplashActivity.getAnswers();
        timecreated = SplashActivity.getTimeCreated();
        subjects = SplashActivity.getSubjects();
        photos = SplashActivity.getPhotos();
        comments = SplashActivity.getComments();
        tutorIds = SplashActivity.getTutorIds();
        CustomListStudentAdapter adapter = new CustomListStudentAdapter(this, subjects, timecreated, imgid, answered);
        listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                startActivity(new Intent(StudentActivity.this, StudentViewPhoto.class));
                selectedimage = photos.get(+position);
                comment = comments.get(+position);
                tutorId = tutorIds.get(+position);
                answer = answered.get(+position);
            }
        });
        //handler.postDelayed(runnable, 500);
    }

    public static ParseFile getImage(){
        return selectedimage;
    }

    public static String getComment(){
        return comment;
    }

    public static String getTutorId(){
        return tutorId;
    }

    public static String getAnswered() { return answer; }


    private void autoUpdate(){
        final ParseQuery<ParseObject> query = ParseQuery.getQuery("Questions");
        query.whereEqualTo("username", username);
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
                                comments.clear();
                                tutorIds.clear();
                                answered.clear();
                                int x = 0;
                                while (x < parseObjects.size()){
                                    String subject = parseObjects.get(x).getString("subject");
                                    Date date = parseObjects.get(x).getUpdatedAt();
                                    ParseFile photo = parseObjects.get(x).getParseFile("ImageFile");
                                    String tutorId = parseObjects.get(x).getString("tutorId");
                                    String ans = parseObjects.get(x).getString("answered");
                                    answered.add(ans);
                                    tutorIds.add(tutorId);
                                    subjects.add(subject);
                                    dates.add(date);
                                    photos.add(photo);
                                    x++;
                                }
                                Collections.reverse(answered);
                                Collections.reverse(tutorIds);
                                Collections.reverse(subjects);
                                Collections.reverse(dates);
                                Collections.reverse(photos);
                                compareDate();
                                CustomListStudentAdapter adapter = new CustomListStudentAdapter(StudentActivity.this, subjects, timecreated, imgid, answered);
                                listView = (ListView)findViewById(R.id.list);
                                listView.setAdapter(adapter);
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view,
                                                            int position, long id) {
                                        // TODO Auto-generated method stub
                                        selectedimage = photos.get(+position);
                                        comment = comments.get(+position);
                                        tutorId = tutorIds.get(+position);
                                        answer = answered.get(+position);
                                        startActivity(new Intent(StudentActivity.this, StudentViewPhoto.class));
                                    }
                                });
                            }
                            else{
                                Log.e("Error", e.getMessage());
                            }
                        }
                    });
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
            else {
                if(calendar.get(Calendar.MONTH) == month) {
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
                else {
                    int e = month - calendar.get(Calendar.MONTH);
                    if (e == 1) {
                        timecreated.add(e + " month ago");
                    }
                    else{
                        timecreated.add(e + " months ago");
                    }
                }
            }
            x++;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.student_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_comment:
                startActivity(new Intent(StudentActivity.this, StudentChat.class));
                return true;
            case R.id.action_profile:
                startActivity(new Intent(StudentActivity.this, UniversityList.class));
                return true;
            case R.id.action_settings:
                startActivity(new Intent(StudentActivity.this, PrefsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
    private Runnable runnable = new Runnable(){
        @Override
        public void run(){
            refreshQuestions();
            handler.postDelayed(this, 100);
        }
    };

    private void refreshQuestions(){
        autoUpdate();
    }

     */
    protected void onStart(){
        super.onStart();
        autoUpdate();
    }

    protected void onPause(){
        super.onPause();
    }

    protected void onResume(){
        super.onResume();
        autoUpdate();
    }

    protected void onDestroy(){
        super.onDestroy();
    }

    protected void onRestart(){
        super.onRestart();
        autoUpdate();
    }
}


package com.example.pascalso.tester;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

/**
 * Created by owner on 9/2/15.
 */
public class QuestionPick extends Activity {
    private ListView listView;
    private Integer[] imgid = { R.drawable.ic_action_waiting, R.drawable.ic_action_done };
    private ArrayList<String> timecreated = new ArrayList<>();
    private ArrayList<ParseFile> photos = new ArrayList<>();
    private ArrayList<ParseObject> parseObjects = new ArrayList<>();
    private static ArrayList<Date> dates = new ArrayList<>();
    private ArrayList<String> grades = new ArrayList<>();
    private ArrayList<String> usernames = new ArrayList<>();
    private ArrayList<String> curriculum = new ArrayList<>();
    private ArrayList<String> comments = new ArrayList<>();
    private ArrayList<String> objectIDs = new ArrayList<>();
    private static String selectedObject;
    private int minute;
    private int hour;
    private int day;
    private int month;
    private static ParseFile selectedimage;
    private String chosensubject;
    private static String username;
    private static String comment;


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionpick);
        chosensubject = TutorActivity.getChosenSubject();
        ActionBar actionBar = getActionBar();
        actionBar.setTitle(chosensubject);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        switch (chosensubject){
            case "Biology":
                parseObjects = SplashActivity.getBiology();
                break;
            case "Chemistry":
                parseObjects = SplashActivity.getChemistry();
                break;
            case "Computer Science":
                parseObjects = SplashActivity.getCompsci();
                break;
            case "Economics":
                parseObjects = SplashActivity.getEcon();
                break;
            case "Maths":
                parseObjects = SplashActivity.getMath();
                break;
            case "Physics":
                parseObjects = SplashActivity.getPhysics();
        }
        //ParseUser currentUser = ParseUser.getCurrentUser();
        int x = 0;
        while (x < parseObjects.size()){
            Date date = parseObjects.get(x).getCreatedAt();
            ParseFile photo = parseObjects.get(x).getParseFile("ImageFile");
            String curr = parseObjects.get(x).getString("curriculum");
            String grade = parseObjects.get(x).getString("grade");
            String comment = parseObjects.get(x).getString("comment");
            String username = parseObjects.get(x).getString("username");
            String objectID = parseObjects.get(x).getObjectId();
            objectIDs.add(objectID);
            usernames.add(username);
            comments.add(comment);
            curriculum.add(curr);
            grades.add(grade);
            dates.add(date);
            photos.add(photo);
            x++;
        }
        Collections.reverse(objectIDs);
        Collections.reverse(usernames);
        Collections.reverse(comments);
        Collections.reverse(grades);
        Collections.reverse(curriculum);
        Collections.reverse(dates);
        Collections.reverse(photos);
        compareDate();

        CustomListSubjectAdapter adapter = new CustomListSubjectAdapter(this, usernames, grades, timecreated, imgid);
        listView = (ListView)findViewById(R.id.list1);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                selectedimage = photos.get(+position);
                setObjectID(objectIDs.get(+position));
                setUsername(usernames.get(+position));
                setComment(comments.get(+position));
                startActivity(new Intent(QuestionPick.this, TutorViewPhoto.class));
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
    public static ParseFile getImage(){
        return selectedimage;
    }

    public static String getObjectID() { return selectedObject; }

    private void setObjectID(String id){
        selectedObject = id;
    }

    private void setUsername(String name) { username = name; }

    public static String getUsername(){return username; }

    public static String getComment(){ return comment; }

    private void setComment(String c){ comment = c;}

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_settings:
                ParseUser.logOut();
                startActivity(new Intent(QuestionPick.this, SplashActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

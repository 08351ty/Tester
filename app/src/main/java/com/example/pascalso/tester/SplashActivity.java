package com.example.pascalso.tester;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParsePush;
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

    private ArrayList<ParseObject> parseObjects = new ArrayList<ParseObject>();
    private static ArrayList<Date> dates = new ArrayList<Date>();
    private static ArrayList<String> timecreated = new ArrayList<String>();
    private static ArrayList<String> subjects = new ArrayList<String>();
    private static ArrayList<ParseFile> photos = new ArrayList<>();
    private static ParseQuery<ParseObject> query;
    private static ArrayList<String> comments = new ArrayList<>();
    private static ArrayList<String> curriculum = new ArrayList<>();
    private static ArrayList<String> grades = new ArrayList<>();
    private static ArrayList<String> usernames = new ArrayList<>();
    private static ArrayList<String> answers = new ArrayList<>();
    private static ArrayList<ParseObject> biology = new ArrayList<>();
    private static ArrayList<ParseObject> chemistry = new ArrayList<>();
    private static ArrayList<ParseObject> compsci = new ArrayList<>();
    private static ArrayList<ParseObject> econ = new ArrayList<>();
    private static ArrayList<ParseObject> math = new ArrayList<>();
    private static ArrayList<ParseObject> physics = new ArrayList<>();
    private int day;
    private int month;
    private int hour;
    private int minute;
    private String username;
    private ParseFile photo;
    private String answered;
    private String comment;
    private String answercomment;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //ParseUser.logOut();
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                ParseUser user = ParseUser.getCurrentUser();
                if(user == null){
                    startActivity(new Intent(SplashActivity.this, UserVerification.class));
                    finish();
                }
                else{
                    if(user.get("usertype").toString().equals("student")){
                        //Get data of student's own profile
                        query = ParseQuery.getQuery("Questions");
                        username = user.get("username").toString();
                        query.whereEqualTo("username", username);
                        try {
                            List<ParseObject> photos = query.find();
                            parseObjects.addAll(photos);
                        } catch (ParseException e){
                        }

                        int x = 0;
                        while (x < parseObjects.size()) {
                            String subject = parseObjects.get(x).getString("subject");
                            Date date = parseObjects.get(x).getUpdatedAt();
                            if(parseObjects.get(x).has("Answer")){
                                photo = parseObjects.get(x).getParseFile("Answer");
                                answered = "yes";
                            }
                            else {
                                photo = parseObjects.get(x).getParseFile("ImageFile");
                                answered = "no";
                            }
                            if(parseObjects.get(x).has("TutorComment")){

                                comment = parseObjects.get(x).getString("TutorComment");

                            }
                            else {
                                comment = parseObjects.get(x).getString("comment");
                            }
                            answers.add(answered);
                            comments.add(comment);
                            subjects.add(subject);
                            dates.add(date);
                            photos.add(photo);
                            x++;
                        }
                        Collections.reverse(answers);
                        Collections.reverse(comments);
                        Collections.reverse(grades);
                        Collections.reverse(curriculum);
                        Collections.reverse(subjects);
                        Collections.reverse(dates);
                        Collections.reverse(photos);
                        compareDate();
                        ParsePush.subscribeInBackground(username);
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    }
                    else{
                        //Get data of all work
                        query = ParseQuery.getQuery("Questions");
                        query.whereDoesNotExist("Answer");
                        try {
                            List<ParseObject> photos = query.find();
                            parseObjects.addAll(photos);
                        } catch (ParseException e){
                        }

                        int x = 0;
                        while (x < parseObjects.size()) {
                            String subject = parseObjects.get(x).getString("subject");
                            switch (subject){
                                case "Biology":
                                    biology.add(parseObjects.get(x));
                                    break;
                                case "Chemistry":
                                    chemistry.add(parseObjects.get(x));
                                    break;
                                case "Computer Science":
                                    compsci.add(parseObjects.get(x));
                                    break;
                                case "Economics":
                                    econ.add(parseObjects.get(x));
                                    break;
                                case "Maths":
                                    math.add(parseObjects.get(x));
                                    break;
                                case "Physics":
                                    physics.add(parseObjects.get(x));
                                    break;
                            }
                            x++;
                        }
                        ParsePush.subscribeInBackground("Tutor");
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();

                    }
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

    private void compileBiology(){
        /**
        Date date = biology.get(x).getCreatedAt();
        ParseFile photo = biology.get(x).getParseFile("ImageFile");
        String curr = biology.get(x).getString("curriculum");
        String grade = biology.get(x).getString("grade");
        String comment = biology.get(x).getString("comment");
        String username = biology.get(x).getString("username");
        usernames.add(username);
        comments.add(comment);
        curriculum.add(curr);
        grades.add(grade);
        subjects.add(subject);
        dates.add(date);
        photos.add(photo);
         */
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

    public static ArrayList<String> getGrades() {return grades; }

    public static ArrayList<String> getCurriculum() {return curriculum; }

    public static ArrayList<String> getComments() {return comments; }

    public static ArrayList<String> getUsernames() {return usernames; }

    public static ArrayList<ParseObject> getBiology() {
        return biology;
    }

    public static ArrayList<ParseObject> getChemistry() {
        return chemistry;
    }

    public static ArrayList<ParseObject> getCompsci() {
        return compsci;
    }

    public static ArrayList<ParseObject> getEcon(){
        return econ;
    }

    public static ArrayList<ParseObject> getMath(){
        return math;
    }

    public static ArrayList<ParseObject> getPhysics(){
        return physics;
    }

    public static ArrayList<String> getAnswers() {return answers;}

}

package com.example.pascalso.tester;

import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseInstallation;
import com.parse.PushService;

/**
 * Created by owner on 7/7/15.
 */
public class Application extends android.app.Application{
    public void onCreate(){
        super.onCreate();
        Parse.initialize(this, "OlKO7GclrmS2MLdwK2Av7puo7T2LcS67w7BiI2ye", "6PpmXqQlWrMlXHnwoJrhfZn7oRRebGTwzksyR4ej");
        ParseInstallation.getCurrentInstallation().saveInBackground();
        //PushService.setDefaultPushCallback(this, MainActivity.class);

        /**
        FontsOverride.setDefaultFont(this, "DEFAULT", "Lato-Thin.ttf");
        FontsOverride.setDefaultFont(this, "MONOSPACE", "Lato-Regular.ttf");
        FontsOverride.setDefaultFont(this, "SERIF", "Lato-Thin.ttf");
        FontsOverride.setDefaultFont(this, "SANS-SERIF", "Lato-Regular.ttf");
         */
    }
}
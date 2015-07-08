package com.example.pascalso.tester;

import com.parse.Parse;

/**
 * Created by owner on 7/7/15.
 */
public class Application extends android.app.Application {
    public void onCreate(){
        super.onCreate();
        Parse.initialize(this, "OlKO7GclrmS2MLdwK2Av7puo7T2LcS67w7BiI2ye", "6PpmXqQlWrMlXHnwoJrhfZn7oRRebGTwzksyR4ej");
    }
}
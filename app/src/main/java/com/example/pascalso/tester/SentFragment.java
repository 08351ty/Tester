package com.example.pascalso.tester;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Pascal So on 7/13/2015.
 */
public class SentFragment extends Fragment {
    ListView listView;
    ParseObject receivedPictures;
    ArrayList<String> values;
    ArrayList<ParseObject> parseObjects;
    static String x;




    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_sent, container, false);
        listView = (ListView) rootView.findViewById(R.id.list);
        ArrayList<String> values = new ArrayList<>();
        final ArrayList<ParseObject> parseObjects = new ArrayList<>();
        ParseQuery <ParseObject> query = ParseQuery.getQuery("ReceivedPictures");
        query.whereEqualTo("mediatype", "image");
        try {
            List<ParseObject> photos = query.find();
            parseObjects.addAll(photos);
        } catch (ParseException e) {
        }

        /**query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    Log.d("photos", "Retrieved " + list.size() + "photos");
                    //String subject = list.getString("subject");
                    //Date createdAt = receivedPictures.getCreatedAt();
                    //String date = createdAt.getMonth() + "/" + createdAt.getDay();
                    //values.addAll(list);
                    //parseObjects.addAll(list);
                    x = list.size();

                }
                else{
                    Log.d("photos", "Error" + e.getMessage());
                }
            }
        });*/

        int x = 0;
        while ( x < parseObjects.size() - 1) {
            String subject = parseObjects.get(x).getString("subject");
            Date createdAt = parseObjects.get(x).getCreatedAt();
            String date = createdAt.getMonth() + "/" + createdAt.getDay();
            values.add(subject + ", " + date);
            x++;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, values);
        listView.setAdapter(adapter);
        //TextView numberofpicturessent = (TextView)rootView.findViewById(R.id.numberofpicturessent);
        //numberofpicturessent.setText(x);
        return rootView;
    }

    public void onListItemClick(ListView l, View v, int position, long id){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

            }
        });
    }

    private void populateValues(){

        ParseQuery <ParseObject> query = new ParseQuery<>("ReceivedPictures");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if(e == null){
                    //Log.d("photos", "Retrieved " + list.size() + "photos");
                    //String subject = receivedPictures.getString("subject");
                    //Date createdAt = receivedPictures.getCreatedAt();
                    //String date = createdAt.getMonth() + "/" + createdAt.getDay();
                    String subject = "hi";
                    values.add(subject);
                }
                else{
                    Log.d("photos", "Error" + e.getMessage());
                }
            }
        });
    }
}

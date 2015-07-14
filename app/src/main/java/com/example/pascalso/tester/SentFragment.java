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
import java.util.List;

import static android.R.layout.simple_list_item_1;

/**
 * Created by Pascal So on 7/13/2015.
 */
public class SentFragment extends Fragment {
    ListView listView;
    ParseObject receivedPictures = new ParseObject("ReceivedPictures");
    ArrayList<String> values;
    String subject;



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_sent, container, false);
        //ListView listView = (ListView)rootView.findViewById(R.id.sentphotos);
        //listView.setAdapter(new SentPhotoAdapter(getActivity(),sentPhoto));

        //TabbedActivityFragment list = new TabbedActivityFragment();
        //constructListView();
        //ArrayList<String> values = new ArrayList<String>();
        //populateValues();
        listView = (ListView) rootView.findViewById(R.id.list);
        final ArrayList<String> values = new ArrayList<>();
        ParseQuery <ParseObject> query = new ParseQuery<>("ReceivedPictures");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if(e == null){
                    //Log.d("photos", "Retrieved " + list.size() + "photos");
                    //String subject = receivedPictures.getString("subject");
                    //Date createdAt = receivedPictures.getCreatedAt();
                    //String date = createdAt.getMonth() + "/" + createdAt.getDay();
                    subject = "hi";
                }
                else{
                    Log.d("photos", "Error" + e.getMessage());
                }
            }
        });
        values.add(subject);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), simple_list_item_1, values);
        listView.setAdapter(adapter);
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

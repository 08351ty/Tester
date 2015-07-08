package com.example.pascalso.tester;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.ParseException;

/**
 * Created by owner on 7/2/15.
 */
public class ReceivedFragment extends Activity {

    ListView listView;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received);
        listView = (ListView) findViewById(R.id.receivedphotos);
        String [] values = new String [] {"|Physics| Received: July 3","|Math| Received: July 2",
                "|Math| Received: July 2", "|English| Received July 1","|Physics| Received: June 30","|Math| Received: June 15",
                "|Math| Received: June 2", "|Economics| Received June 1", "|Physics| Received: May 30","|Math| Received: May 29",
                "|Math| Received: May 26", "|Math| Received May 15"};
        ArrayAdapter <String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        listView.setAdapter(adapter);
    }

    protected void onListItemClick(ListView l, View v, int position, long id){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

            }
        });
    }
/**
    private void retrieveImage(){
        ParseQuery query = new ParseQuery("ImageTester");
        query.getInBackground("AN_OBJECT_HERE", new GetCallback(){
            public void done(ParseObject object, ParseException e){
                if(object == null){
                    Log.d("test", "the object was not found");
                }
                else{
                    Log.d("test", "received object");
                    ParseFile fileObject = (ParseFile) object.get("images");
                    fileObject.getDataInBackground(new GetDataCallback() {
                        public void done(byte[] bytes, com.parse.ParseException e) {
                            if(e == null){
                                Log.d("test", "we've got data in data");
                            }
                            else{
                                Log.d("test", "There was a problem downloading the data");
                            }
                        }
                    });
                }
            }
        });
    }*/

}

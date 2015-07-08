package com.example.pascalso.tester;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
<<<<<<< HEAD
                "|Math| Received: July 2", "|English| Received July 1","|Physics| Received: June 30","|Math| Received: June 15",
                "|Math| Received: June 2", "|Economics| Received June 1", "|Physics| Received: May 30","|Math| Received: May 29",
                "|Math| Received: May 26", "|Math| Received May 15"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
=======
                "|Math| Received: July 2", "|Math| Received July 1"};
        ArrayAdapter <String> adapter = new ArrayAdapter<String>(this,
>>>>>>> origin/master
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        listView.setAdapter(adapter);
    }

    protected void onListItemClick(ListView l, View v, int position, long id){

    }
}

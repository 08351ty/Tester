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
                "|Math| Received: July 2", "|Math| Received July 1"};
        ArrayAdapter <String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        listView.setAdapter(adapter);

    }

    protected void onListItemClick(ListView l, View v, int position, long id){

    }
}

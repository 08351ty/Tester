package com.example.pascalso.tester;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by owner on 7/2/15.
 */
public class ReceivedFragment extends ListActivity {

    String [] items;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received);
        //items = new String [] {"|Physics| Received: July 3","|Math| Received: July 2",
                //"|Math| Received: July 2", "|Math| Received July 1"};
        //ListAdapter x = new ArrayAdapter<String>(this, R.layout.activity_received, items);
    }

    protected void onListItemClick(ListView l, View v, int position, long id){

    }
}

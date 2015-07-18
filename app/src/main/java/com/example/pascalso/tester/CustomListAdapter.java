package com.example.pascalso.tester;

/**
 * Created by Pascal So on 7/15/2015.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> subjects;
    private final ArrayList<String> timecreated;
    private final Integer[] imgid;

    public CustomListAdapter(Activity context, ArrayList<String> subjects, ArrayList<String> timecreated, Integer[] imgid) {
        super(context, R.layout.mylist, subjects);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.subjects = subjects;
        this.timecreated = timecreated;
        this.imgid = imgid;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.mylist, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);
        txtTitle.setText(subjects.get(position));
        imageView.setImageResource(imgid[0]);
        extratxt.setText("Sent: " + timecreated.get(position));
        return rowView;
    }
}
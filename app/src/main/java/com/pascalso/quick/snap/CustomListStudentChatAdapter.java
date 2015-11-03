package com.pascalso.quick.snap;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by owner on 9/23/15.
 */
public class CustomListStudentChatAdapter extends ArrayAdapter<String> {

    private final ArrayList<String> chatname;
    private final ArrayList<String> university;
    private final Integer[] imgid;
    private final Activity context;

    public CustomListStudentChatAdapter(Activity context, ArrayList<String> chatname, ArrayList<String> university, Integer[] imgid){
        super(context, R.layout.mylist, chatname);
        this.context = context;
        this.chatname = chatname;
        this.university = university;
        this.imgid = imgid;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.mylist, null, true);
        TextView txtTitle = (TextView)rowView.findViewById(R.id.item);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        imageView.setImageResource(R.drawable.ic_action_tutorprofile);
        TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);
        txtTitle.setText(chatname.get(position));
        extratxt.setText(university.get(position));
        return rowView;
    }
}

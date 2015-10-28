package com.example.pascalso.tester;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by pso on 10/9/15.
 */
public class StudentChat extends Activity {

    private ArrayList<String> chatname = new ArrayList<>();
    private ArrayList<String> university = new ArrayList<>();
    private Integer[] imgid = { R.drawable.ic_action_openchat, R.drawable.ic_action_hasmessage };
    private ListView listView;
    private int z = 0;
    private String name;
    private String uni;
    public static String chatId;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentchat);
        ActionBar actionBar = getActionBar();
        actionBar.setTitle("Chat");
        getActionBar().setDisplayHomeAsUpEnabled(true);
        fetchMessages();
        CustomListStudentChatAdapter adapter = new CustomListStudentChatAdapter(this, chatname, university, imgid);
        listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                chatId = chatname.get(+position);
                Intent startChatActivity = new Intent(StudentChat.this, ChatActivity.class);
                startChatActivity.putExtra("calling-activity", ActivityConstants.STUDENT_CHAT);
                startActivity(startChatActivity);
            }
        });
    }

    private void fetchMessages(){
        String user = ParseUser.getCurrentUser().getObjectId();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Message");
        query.whereContains("channel", user);
        try{
            List<ParseObject> messages = query.find();
            int x = 0;
            while (x < messages.size()) {
                if(z == 0) {
                    String id = messages.get(x).getString("userId");
                    findNameFromMessage(id);
                    chatname.add(name);
                    university.add(uni);
                    x++;
                }
                else {
                    x++;
                }
                int y = 0;
                if(x < messages.size()) {
                    z = 0;
                    while (y < chatname.size()) {
                        if (chatname.get(y).equals(name)) {
                            y++;
                            z++;
                        }
                        else {
                            y++;
                        }
                    }
                }
            }
            Collections.reverse(chatname);
            Collections.reverse(university);
        }
        catch (ParseException e){
        }
    }

    private void findNameFromMessage(String id) {
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("objectId", id);
        try {
            ParseUser user = query.getFirst();
            name = user.getString("firstname");
            uni = user.getString("college");
        }
        catch (ParseException e) {

        }
    }

    public static String getChatId(){
        return chatId;
    }
}

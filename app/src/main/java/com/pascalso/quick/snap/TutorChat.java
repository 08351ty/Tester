package com.pascalso.quick.snap;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by owner on 9/23/15.
 */
public class TutorChat extends Activity {

    private Integer[] imgid = { R.drawable.ic_action_openchat, R.drawable.ic_action_hasmessage };
    private ArrayList<String> chatname = new ArrayList<String>();
    private ArrayList<String> curriculum = new ArrayList<String>();
    private ArrayList<String> grade = new ArrayList<String>();
    private ListView listView;
    private String username;
    private String name;
    private String curr;
    private String gr;
    private int z = 0;
    private static String userId;
    private static String chatId;
    private static String studentId;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorchat);
        ActionBar actionBar = getActionBar();
        actionBar.setTitle("Chat");
        getActionBar().setDisplayHomeAsUpEnabled(true);
        fetchMessages();
        CustomListTutorChatAdapter adapter = new CustomListTutorChatAdapter(this, chatname, curriculum, grade, imgid);
        listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                chatId = chatname.get(+position);
                Intent startChatActivity = new Intent(TutorChat.this, ChatActivity.class);
                startChatActivity.putExtra("calling-activity", ActivityConstants.TUTOR_CHAT);
                startActivity(startChatActivity);
            }
        });
    }

    private void fetchMessages(){
        username = ParseUser.getCurrentUser().get("username").toString();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Questions");
        query.whereEqualTo("tutorname", username);
        try{
            List<ParseObject> question = query.find();
            int x = 0;
            Toast.makeText(getApplicationContext(), username,
                    Toast.LENGTH_SHORT).show();
            while (x < question.size()) {
                if(z == 0){
                    name = question.get(x).getString("username");
                    curr = question.get(x).getString("curriculum");
                    gr = question.get(x).getString("grade");
                    chatname.add(name);
                    curriculum.add(curr);
                    grade.add(gr);
                    x++;
                }
                else {
                    x++;
                }

                int y = 0;
                if(x < question.size()) {
                    z = 0;
                    while (y < chatname.size()) {
                        if (chatname.get(y).equals(question.get(x).getString("username"))) {
                            y++;
                            z++;
                        }
                        else{
                            y++;
                        }
                    }
                }
            }
            Collections.reverse(chatname);
            Collections.reverse(curriculum);
            Collections.reverse(grade);
        }
        catch(ParseException e){

        }
    }

    public static String getStudentId(){
        ParseQuery query = ParseUser.getQuery();
        query.whereEqualTo("username", chatId);
        try {
            List<ParseUser> user = query.find();
            studentId = user.get(0).getObjectId();

        }
        catch(ParseException e){
        }
        return studentId;
    }

    public static String getChatId(){
        return chatId;
    }

}

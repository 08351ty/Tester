package com.pascalso.quick.snap;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by owner on 9/21/15.
 */
public class ChatActivity extends Activity {
    private String userID;
    public static final String USER_ID_KEY = "userId";
    public static final String targetId = null;
    private EditText etMessage;
    private Button btSend;
    private ListView lvChat;
    private ArrayList<Message> messages;
    private ChatListAdapter mAdapter;
    private boolean mFirstLoad;
    private static final int MAX_CHAT_MESSAGES_TO_SHOW = 50;
    private Handler handler = new Handler();
    private String studentId;
    private String tutorId;
    private static Boolean isTutor;
    private String id;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getIds();
        getActionBar().setTitle(id);
        startWithCurrentUser();
        handler.postDelayed(runnable, 100);
    }


    // Get the userId from the cached currentUser object
    private void startWithCurrentUser() {
        userID = ParseUser.getCurrentUser().getObjectId();
        setupMessagePosting();
    }

    // Setup button event handler which posts the entered message to Parse
    private void setupMessagePosting() {
        // Find the text field and button
        etMessage = (EditText) findViewById(R.id.etMessage);
        btSend = (Button) findViewById(R.id.btSend);
        lvChat = (ListView) findViewById(R.id.lvChat);
        messages = new ArrayList<>();
        lvChat.setTranscriptMode(1);
        mFirstLoad = true;
        mAdapter = new ChatListAdapter(ChatActivity.this, userID, messages);
        lvChat.setAdapter(mAdapter);
        // When send button is clicked, create message object on Parse
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = etMessage.getText().toString();
                ParseObject message = ParseObject.create("Message");
                message.put(USER_ID_KEY, userID);
                message.put("channel", studentId + "/" + tutorId);
                message.put("body", data);
                message.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                    }
                });
                etMessage.setText("");
            }
        });
    }

    private void receiveMessage(){
        ParseQuery<Message> query = ParseQuery.getQuery(Message.class);
        query.whereEqualTo("channel", studentId + "/" + tutorId);
        query.setLimit(MAX_CHAT_MESSAGES_TO_SHOW);
        query.orderByAscending("createdAt");
        query.findInBackground(new FindCallback<Message>() {
            @Override
            public void done(List<Message> messagelist, ParseException e) {
                if(e == null){
                    messages.clear();
                    messages.addAll(messagelist);
                    mAdapter.notifyDataSetChanged();
                    if(mFirstLoad){
                        lvChat.setSelection(mAdapter.getCount() - 1);
                        mFirstLoad = false;
                    }
                }
                else{
                    Log.d("message", "Error: " + e.getMessage());
                }
            }
        });
    }

    private Runnable runnable = new Runnable(){
        @Override
        public void run(){
            refreshMessages();
            handler.postDelayed(this, 100);
        }
    };

    private void refreshMessages(){
        receiveMessage();
    }

    private void getIds(){
        int callingActivity = getIntent().getIntExtra("calling-activity", 0);
        switch(callingActivity){
            case ActivityConstants.STUDENT_CHAT:
                studentId = ParseUser.getCurrentUser().getObjectId();
                tutorId = StudentActivity.getTutorId();
                isTutor = false;
                id = StudentChat.getChatId();

                break;
            case ActivityConstants.TUTOR_CHAT:
                tutorId = ParseUser.getCurrentUser().getObjectId();
                studentId = TutorChat.getStudentId();
                isTutor = true;
                id = TutorChat.getChatId();
        }
    }

    public static Boolean getIdentity(){
        return isTutor;
    }
}

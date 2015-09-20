package com.example.pascalso.tester;

import android.content.Intent;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.preference.PreferenceActivity;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.parse.PushService;

import java.util.List;

/**
 * Created by owner on 9/13/15.
 */
public class PrefsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs);
        getActionBar().setTitle("Settings");
        ListView v = getListView();
        TextView tv = new TextView(this);
        tv.setText("Log Out");
        tv.setTextSize(24);
        tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        tv.setPadding(20, 20, 20, 20);
        v.addFooterView(tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                List<String> subscribedChannels = ParseInstallation.getCurrentInstallation().getList("channels");
                int x = subscribedChannels.size();
                while (x < 0){
                    ParsePush.unsubscribeInBackground(subscribedChannels.get(x-1));
                    x--;
                }
                startActivity(new Intent(PrefsActivity.this, SplashActivity.class));
                finish();
            }
        });
    }


}

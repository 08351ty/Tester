package com.pascalso.quick.snap;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseUser;

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
        getActionBar().setDisplayHomeAsUpEnabled(true);
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
                Intent i = new Intent(PrefsActivity.this, SplashActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) { //app icon in action bar clicked; go back
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}

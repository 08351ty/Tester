package com.pascalso.quick.snap;

import android.content.Context;
import android.content.Intent;

import com.parse.ParsePushBroadcastReceiver;

/**
 * Created by pso on 10/27/15.
 */
public class StudentPushReceiver extends ParsePushBroadcastReceiver {

    protected static String pushTitle = "";
    protected void onPushOpen(Context context, Intent intent) {
        super.onPushOpen(context, intent);
    }

}

package kiwihackathon.com.samplekiwiapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by afzal on 2014-09-29.
 */
public class ActionReceiver extends BroadcastReceiver {
    private static final String TAG = ActionReceiver.class.getSimpleName();
    SharedPreferences sharedPreferences;
    @Override
    public void onReceive(Context context, Intent intent) {
        String motionName = intent.getStringExtra("motionName");
        String score = intent.getStringExtra("score");
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//        sharedPreferences=context.getSharedPreferences("Log",0);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putString("motion",motionName);
        editor.putString("score",score);
        editor.apply();
        Log.d(TAG, "motion name: " + motionName);
    }
}

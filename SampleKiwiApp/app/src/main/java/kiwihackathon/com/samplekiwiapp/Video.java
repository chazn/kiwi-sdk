package kiwihackathon.com.samplekiwiapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.VideoView;

/**
 * Created by Ming on 15/11/2014.
 */
public class Video extends Activity implements MediaPlayer.OnCompletionListener {
    VideoView video;
    String fileName;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video);
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        fileName=sharedPreferences.getString("Video","");
        video();
    }
    public void video (){

        video=(VideoView)findViewById(R.id.videoView);
        try{
            video.setVideoPath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()+"/"+fileName);
        }catch(Exception e){
            e.printStackTrace();
        }
        // video.setMediaController(new MediaController(this));
        video.requestFocus();
        video.start();
        video.setOnCompletionListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Intent intent = new Intent(Video.this,ReviewTechnique.class);
        startActivity(intent);
    }
}

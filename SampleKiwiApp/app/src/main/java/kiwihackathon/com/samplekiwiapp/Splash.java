package kiwihackathon.com.samplekiwiapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * Created by Ming on 15/11/2014.
 */
public class Splash extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        Thread timer = new Thread(){
            public void run(){
                try{
                    sleep(1000);
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                }
                finally{
                    Intent openStartingPoint = new Intent(Splash.this,Menu.class);
                    startActivity(openStartingPoint);
                }
            }
        };
        timer.start();
    }
}

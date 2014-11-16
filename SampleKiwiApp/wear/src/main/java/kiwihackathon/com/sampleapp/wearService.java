package kiwihackathon.com.sampleapp;

import android.util.Log;

import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.WearableListenerService;

/**
 * Created by Ming on 15/11/2014.
 */
public class wearService extends WearableListenerService {
    public static final String TAG = wearService.class.getSimpleName();
    sensroListener a;
    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        super.onDataChanged(dataEvents);
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        super.onMessageReceived(messageEvent);
        Log.d(TAG,messageEvent.toString());
        if(messageEvent.getPath().equals("/message")){
            a = new sensroListener();
            a.setUpSensors(getApplicationContext());
            a.regiserSensors();
           /// a.unregisterSensors();
        }
    }

    @Override
    public void onPeerConnected(Node peer) {
        super.onPeerConnected(peer);
    }

    @Override
    public void onPeerDisconnected(Node peer) {
        super.onPeerDisconnected(peer);
        a.unregisterSensors();
    }
}

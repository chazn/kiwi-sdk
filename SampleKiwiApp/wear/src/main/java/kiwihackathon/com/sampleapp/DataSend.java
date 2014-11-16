package kiwihackathon.com.sampleapp;

import android.content.Context;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

/**
 * Created by Ming on 15/11/2014.
 */
public class DataSend implements GoogleApiClient.ConnectionCallbacks {
    private static final String DATA_PATH="/dataSend";
    private static final String TAG = DataSend.class.getSimpleName();

    private GoogleApiClient mApiClient;
    private Node mNode;

    public DataSend(){
    }

    public void initGoogleClient(Context context){
        mApiClient = new GoogleApiClient.Builder(context)
                .addApi( Wearable.API )
                .build();

        mApiClient.connect();
        Wearable.NodeApi.getConnectedNodes( mApiClient ).setResultCallback(new ResultCallback<NodeApi.GetConnectedNodesResult>() {

            @Override
            public void onResult(NodeApi.GetConnectedNodesResult getConnectedNodesResult) {
                if (getConnectedNodesResult.getNodes().size() > 0) {
                    mNode = getConnectedNodesResult.getNodes().get(0);
                }
            }
        });
    }
    public void disconnect(){
        mApiClient.disconnect();
    }

    public void sendMessage(final byte[] data){
                    Wearable.MessageApi.sendMessage(
                            mApiClient, mNode.getId(), DATA_PATH, data).setResultCallback(new ResultCallback<MessageApi.SendMessageResult>() {
                        @Override
                        public void onResult(MessageApi.SendMessageResult sendMessageResult) {
                            Log.d(TAG, sendMessageResult.getStatus().toString());
            }
        });

    }
    @Override
    public void onConnected(Bundle bundle) {
        sendMessage("".getBytes());
    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}

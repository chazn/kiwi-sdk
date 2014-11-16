package kiwihackathon.com.samplekiwiapp;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;


public class WearSender extends Activity implements GoogleApiClient.ConnectionCallbacks {

    private static final String START_ACTIVITY = "/start_activity";
    private static final String WEAR_MESSAGE_PATH = "/message";
    private static final String TAG=WearSender.class.getSimpleName();

    private GoogleApiClient mApiClient;

    private ArrayAdapter<String> mAdapter;

    private ListView mListView;
    private EditText mEditText;
    private Button mSendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

       // init();
        initGoogleApiClient();
    }

    private void initGoogleApiClient() {
        mApiClient = new GoogleApiClient.Builder( this )
                .addApi( Wearable.API )
                .build();

        mApiClient.connect();
        sendMessage(WEAR_MESSAGE_PATH, "");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
        mApiClient.disconnect();
    }

    private void init() {
       /* mListView = (ListView) findViewById( R.id.list_view );
        mEditText = (EditText) findViewById( R.id.input );
        mSendButton = (Button) findViewById( R.id.btn_send );
*/
        mAdapter = new ArrayAdapter<String>( this, android.R.layout.simple_list_item_1 );
        mListView.setAdapter( mAdapter );

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = mEditText.getText().toString();
                if (!TextUtils.isEmpty(text)) {
                    mAdapter.add(text);
                    mAdapter.notifyDataSetChanged();

                    sendMessage(WEAR_MESSAGE_PATH, text);
                }
            }
        });
    }

    private void sendMessage( final String path, final String text ) {
        new Thread( new Runnable() {
            @Override
            public void run() {
                NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes( mApiClient ).await();
                for(Node node : nodes.getNodes()) {
                    Wearable.MessageApi.sendMessage(
                            mApiClient, node.getId(), path, text.getBytes()).setResultCallback(new ResultCallback<MessageApi.SendMessageResult>() {
                        @Override
                        public void onResult(MessageApi.SendMessageResult sendMessageResult) {
                            Log.d(TAG,sendMessageResult.getStatus().toString());
                            Intent intent=new Intent(getApplicationContext(),MyActivity.class);
                            startActivity(intent);
                        }
                    });
                }

               /* runOnUiThread( new Runnable() {
                    @Override
                    public void run() {
                        mEditText.setText( "" );
                    }
                });*/
            }
        }).start();
    }

    @Override
    public void onConnected(Bundle bundle) {
        sendMessage(START_ACTIVITY, "");
    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
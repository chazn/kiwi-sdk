package kiwihackathon.com.samplekiwiapp;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.text.style.UpdateLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;
import com.ibm.mobile.services.core.IBMBluemix;
import com.ibm.mobile.services.core.service.IBMBluemixService;
import com.ibm.mobile.services.data.IBMData;
import com.ibm.mobile.services.data.IBMDataClient;
import com.ibm.mobile.services.data.IBMDataObject;
import com.ibm.mobile.services.push.IBMPush;
import com.kiwiwearables.app.models.SensorReading;
import com.kiwiwearables.app.services.IKiwiBinder;

import bolts.Continuation;
import bolts.Task;


public class MyActivity extends Activity {
    private static final String TAG = MyActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements MessageApi.MessageListener,SharedPreferences.OnSharedPreferenceChangeListener {

        private static final String TAG = PlaceholderFragment.class.getSimpleName();

        IKiwiBinder mKiwiService;

        private boolean mBound;

        float [] data = new float[6];

        GoogleApiClient googleApiClient;

        SharedPreferences sharedPreferences;
        SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;

        LineGraphView lgva,lgvg;
        LinearLayout linear;
        TextView textView;
        TextView tv;
        float[] points = new float [3];
        float[] point= new float [3];
        private ServiceConnection mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d(TAG, "service connected");
                mKiwiService = IKiwiBinder.Stub.asInterface(service);
                mBound = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d(TAG, "service disconnected");
                mBound = false;
            }
        };

        @Override
        public void onDestroy() {
            if (mBound) {
                getActivity().unbindService(mConnection);
                mBound = false;
            }
            super.onDestroy();
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_my, container, false);
           /* Button sendDataButton = (Button) rootView.findViewById(R.id.send_data_button);
            Button fetchMotionsButton = (Button) rootView.findViewById(R.id.fetch_motions_button);
            Button changeMotionButton = (Button) rootView.findViewById(R.id.change_motion_button);
*/
            linear = (LinearLayout)rootView.findViewById(R.id.linear);

            googleApiClient = new GoogleApiClient.Builder(getActivity()).addApi(Wearable.API).build();
            googleApiClient.connect();
            Wearable.MessageApi.addListener(googleApiClient, this);


           /* sendDataButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mBound) {
                        SensorReading reading = new SensorReading(new float[] {5.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f}, "test", "12afzaltest");
                        try {
                            mKiwiService.sendData(reading);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Not bound to service", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            final List<Motion> motions = new ArrayList<Motion>();

            fetchMotionsButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cursor c = getActivity().getContentResolver().query(Uri.parse("content://com.kiwiwearables.app.provider/motions"), null, null, null, null);
                    if (c != null) {
                        while (c.moveToNext()) {
                            Motion motion = Motion.fromCursor(c);
                            motions.add(motion);
                        }

                        c.close();
                    }

                    for (Motion motion : motions) {
                        Log.d(TAG, "" + motion.getAccWeight());
                    }
                }
            });

            changeMotionButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (motions.size() > 0) {
                        Motion motion = motions.get(0);
                        motion.setAccWeight(11.0f);
                        motion.sendMotion(getActivity());
                    }
                }
            });*/

            Intent intent = new Intent(IKiwiBinder.class.getName());
            getActivity().bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

//            sharedPreferences=rootView.getContext().getSharedPreferences("Log",0);
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
//            preferenceChangeListener= new SharedPreferences.OnSharedPreferenceChangeListener() {
//                @Override
//                public void onSharedPreferenceChanged(SharedPreferences sharedPreference, String key) {
//                    if(key.equals("motion")){
//                        textView.setText(sharedPreferences.getString("motion","") + " has been detected");
//                    }
//                }
//            };
            return rootView;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            lgva=new LineGraphView(getActivity(),100, Arrays.asList("x", "y", "z"));
            linear.addView(lgva);
            lgva.setVisibility(View.VISIBLE);
            lgvg=new LineGraphView(getActivity(),100, Arrays.asList("x", "y", "z"));
            linear.addView(lgvg);
            lgvg.setVisibility(View.VISIBLE);
            textView=new TextView(getActivity());
            linear.addView(textView);
            textView.setTextSize(20);
            tv= new TextView(getActivity());
            linear.addView(tv);
            tv.setTextSize(20);
            sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        }

        @Override
        public void onResume() {
            super.onResume();
            Wearable.MessageApi.addListener(googleApiClient, this);
        }

        @Override
        public void onPause() {
            super.onPause();
            Wearable.MessageApi.removeListener(googleApiClient,this);
            sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onMessageReceived(MessageEvent messageEvent) {

            if(messageEvent.getPath().equals("/dataSend")){
                ByteBuffer byteBuffer = ByteBuffer.wrap(messageEvent.getData());
                FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();
                floatBuffer.rewind();
                float[] values = new float[floatBuffer.limit()];
                floatBuffer.get(values);
                Log.d(TAG,"data:"+values[0]+"|"+values[1]+"|"+values[2]);
                SensorReading reading = new SensorReading(values, "test", "Hackathon");
                points[0]=values[0];
                points[1]=values[1];
                points[2]=values[2];

                lgva.addPoint(points);

                point[0]=values[3];
                point[1]=values[4];
                point[2]=values[5];

                lgvg.addPoint(point);


                    try {
                        mKiwiService.sendData(reading);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

            }
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                textView.setText(sharedPreferences.getString("motion","") + " has been detected");

                tv.setText("your score is "+sharedPreferences.getString("score",""));

        }
    }
}

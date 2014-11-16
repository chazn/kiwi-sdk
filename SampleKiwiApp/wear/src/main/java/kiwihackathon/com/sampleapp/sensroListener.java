package kiwihackathon.com.sampleapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import java.nio.ByteBuffer;

/**
 * Created by Ming on 15/11/2014.
 */
public class sensroListener implements SensorEventListener {
    private static final String TAG = sensroListener.class.getSimpleName();
    SensorManager sensorManager;
    Sensor aSensor;
    Sensor gSensor;
    float[] data;
    DataSend dataSend;
    int count;
    public sensroListener(){
        data=new float[6];
        count=0;
    }
    public void setUpSensors(Context context){
        sensorManager=(SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        aSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        dataSend=new DataSend();
        dataSend.initGoogleClient(context);
    }
    public void regiserSensors(){
        sensorManager.registerListener(this,aSensor,SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,gSensor,SensorManager.SENSOR_DELAY_NORMAL);
    }
    public void unregisterSensors(){
        sensorManager.unregisterListener(this);
        dataSend.disconnect();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            Log.d(TAG,"accelerometer:"+event.values[0]);
            if(count == 0){
            data[0]=event.values.clone()[0];
            data[1]=event.values.clone()[1];
            data[2]=event.values.clone()[2];
                count+=3;
            }
        }else if (event.sensor.getType()==Sensor.TYPE_GYROSCOPE){
            if(count == 3){
                data[3]=event.values.clone()[0];
                data[4]=event.values.clone()[1];
                data[5]=event.values.clone()[2];
                count+=3;
            }
        }
        if(count==6){
            count=0;

            ByteBuffer byteBuffer = ByteBuffer.allocate(data.length * 4);
            byteBuffer.asFloatBuffer().put(data);
            dataSend.sendMessage(byteBuffer.array());
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

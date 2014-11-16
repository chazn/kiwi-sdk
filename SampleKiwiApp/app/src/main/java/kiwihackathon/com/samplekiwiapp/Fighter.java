package kiwihackathon.com.samplekiwiapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.ibm.mobile.services.core.IBMBluemix;
import com.ibm.mobile.services.data.IBMData;
import com.ibm.mobile.services.data.IBMDataObject;
import com.ibm.mobile.services.push.IBMPush;

import bolts.Continuation;
import bolts.Task;

/**
 * Created by Ming on 15/11/2014.
 */
public class Fighter extends Activity {
    ImageButton imageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fighterprofile);
        final EditText et1=(EditText) findViewById(R.id.etAge);
        final EditText et2=(EditText) findViewById(R.id.etName);
        final EditText et3=(EditText) findViewById(R.id.etSex);
        final EditText et4 = (EditText) findViewById(R.id.etHeight);
        final EditText et5 = (EditText) findViewById(R.id.etWeight);
        imageButton = (ImageButton) findViewById(R.id.imageButton);
        Button b1 = (Button) findViewById(R.id.bSave);
        Button b2 = (Button) findViewById(R.id.bExit);
        IBMBluemix.initialize(Fighter.this, "a5178892-7502-486d-badc-189479eca149", "cb3e913c0528b7c4c9b0bccdd02b8c604c3580e3", "fctsystem.mybluemix.net");
        IBMData.initializeService();
        IBMPush.initializeService();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et2.getText().toString();
                String age = et1.getText().toString();
                String sex = et3.getText().toString();
                String height = et4.getText().toString();
                String weight = et5.getText().toString();
                if(name==""||age==""||sex==""||height==""||weight==""){
                    Toast toast=Toast.makeText(Fighter.this,"please fill in all fields",Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    DataObject dataObject = new DataObject();

                    dataObject.setName(name);
                    dataObject.setAge(age);
                    dataObject.setHeight(height);
                    dataObject.setSex(sex);
                    dataObject.setWEIGHT(weight);
                    dataObject.save().continueWith(new Continuation<IBMDataObject, Object>() {
                        @Override
                        public Object then(Task<IBMDataObject> ibmDataObjectTask) throws Exception {
                            return null;
                        }
                    });
                    et1.setText("");
                    et2.setText("");
                    et3.setText("");
                    et4.setText("");
                    et5.setText("");
                    Toast toast = Toast.makeText(Fighter.this,"successfully saved",Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

}

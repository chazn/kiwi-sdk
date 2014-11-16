package kiwihackathon.com.samplekiwiapp;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by Ming on 15/11/2014.
 */
public class ReviewTechnique extends ListActivity {
    private static final String TAG = ReviewTechnique.class.getSimpleName();
    String v1,v2,v3,v4;
    SharedPreferences sharedPreferences;
    String [] selections={"punch","jab","hook","kick"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v1="punch";
        setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,selections));
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Intent intent=new Intent(ReviewTechnique.this,Video.class);
        switch (position) {
            case 0:
                editor.putString("Video",v1+".mp4");
                editor.commit();
                break;
            case 1:
                editor.putString("Video",v2);
                break;
            case 2:
                editor.putString("Video",v3);
                break;
            case 3:
                editor.putString("Video",v4);
                break;
            default:
                break;
        }
        startActivity(intent);
    }
}

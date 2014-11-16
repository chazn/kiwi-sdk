package kiwihackathon.com.samplekiwiapp;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by Ming on 15/11/2014.
 */
public class Menu extends ListActivity {
    private static final String TAG = Menu.class.getSimpleName();

    String [] selections = {"Combinations","Review a Technique", "Record a Technique","Fighter Profile"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,selections));
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);
        switch(position){
            case 0:

                break;
            case 1:
                Intent intent2 = new Intent(Menu.this,ReviewTechnique.class);
                startActivity(intent2);
                break;
            case 2:
                Intent intent = new Intent(Menu.this,WearSender.class);
                startActivity(intent);
                break;
            case 3:
                Intent intent1 = new Intent(Menu.this,Fighter.class);
                startActivity(intent1);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}

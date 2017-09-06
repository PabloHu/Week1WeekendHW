package com.example.admin.week1weekendhw;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class SettingsSectionActivity extends AppCompatActivity {
    private static final String TAG ="SettingTAG" ;
    private ListView mListView;
    ArrayList<RadioStationsSerializable> rStation=null;
    String[] listItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_section);
        mListView = (ListView) findViewById(R.id.listView);

        Intent intent = this.getIntent();

        Bundle args = intent.getExtras();
        try {
            rStation = (ArrayList<RadioStationsSerializable>) args.getSerializable("ARRAYLIST");
            Log.d(TAG, "onCreate rstation good");
        }
        catch (Exception e){
            Log.d(TAG, "onCreate catch: "+ e.toString());
        }
        if(rStation != null) {
            Log.d(TAG, "onCreate length!!!: " + rStation.size());
            listItems = new String[rStation.size()];
            for(int i = 0; i < listItems.length; i++){
                listItems[i] = rStation.get(i).getStationName();//
            }
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Toast.makeText(getApplicationContext(),"New Favorite: "+rStation.get(position).getStationName(),Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor = getSharedPreferences("FavoriteStation", MODE_PRIVATE).edit();
                editor.putInt("StationID", rStation.get(position).getStationID());
                editor.apply();
                Log.d(TAG, "onItemClick store: "+rStation.get(position).getStationID());


            }
        });

    }


}

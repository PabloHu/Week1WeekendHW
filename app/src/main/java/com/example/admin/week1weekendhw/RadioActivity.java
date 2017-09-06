package com.example.admin.week1weekendhw;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class RadioActivity extends AppCompatActivity {

    private ListView mListView;
    ArrayList<RadioStationsSerializable> rStation = null;
    String[] listItems;
    private static final String TAG = "RadioTAG";
    String radioURL;
    MediaPlayer mediaPlayer;
    Boolean isRadioPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mListView = (ListView) findViewById(R.id.listView);

        Intent intent = this.getIntent();

        Bundle args = intent.getExtras();
        try {
            rStation = (ArrayList<RadioStationsSerializable>) args.getSerializable("ARRAYLIST");
            Log.d(TAG, "onCreate rstation good");
        } catch (Exception e) {
            Log.d(TAG, "onCreate catch: " + e.toString());
        }
        if (rStation != null) {
            Log.d(TAG, "onCreate !!!: " + rStation.toString());
            listItems = new String[rStation.size()];

            for (int i = 0; i < listItems.length; i++) {
                listItems[i] = rStation.get(i).getStationName();//
            }
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems);
        mListView.setAdapter(adapter);


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                view.setSelected(true);
                playRadio(rStation.get(position).getStationLink());


            }
        });
        //****************
        SharedPreferences prefs = getSharedPreferences("FavoriteStation", MODE_PRIVATE);
        if (prefs != null) {
            int radioID = prefs.getInt("StationID", 0); //0 is the default value.
            Log.d(TAG, "onItemClick restoreid: " + radioID);
            playRadio(rStation.get(radioID).getStationLink());
        }
//**********

    }

    public void playRadio(String radioLink) {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(radioLink);
            mediaPlayer.prepare(); // might take long! (for buffering, etc)
            mediaPlayer.start();
            isRadioPlaying = true;
        } catch (Exception e) {
            Log.d(TAG, "playRadioStationOnPlay: " + e.toString());
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        mediaPlayer.stop();
    }

}

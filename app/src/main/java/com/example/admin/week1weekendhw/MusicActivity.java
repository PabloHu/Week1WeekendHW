package com.example.admin.week1weekendhw;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.IOException;

public class MusicActivity extends AppCompatActivity {
    private static final String TAG = "tag";
    MediaPlayer mediaPlayer;
    Boolean songLoaded = false;
    private SeekBar songSeekbar = null;
    int windowWidth;
    int windoHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        Log.d(TAG, "onCreate: ");
  //      Toast.makeText(getApplicationContext(), "Music Player", Toast.LENGTH_SHORT).show();
        songSeekbar = (SeekBar)findViewById(R.id.musicSeekBar);
        songSeekbar.setEnabled(false);


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        windoHeight = displayMetrics.heightPixels;
        windowWidth = displayMetrics.widthPixels;
  //      Toast.makeText(getApplicationContext(), "width: " +windowWidth, Toast.LENGTH_SHORT).show();

    }
    private void initControls()
    {
        try
        {
            songSeekbar.setMax(mediaPlayer.getDuration());
            songSeekbar.setProgress(mediaPlayer.getCurrentPosition());
            songSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
            {

                @Override
                public void onStopTrackingTouch(SeekBar arg0)
                {
                    Toast.makeText(getApplicationContext(), "width: " +windowWidth, Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onStartTrackingTouch(SeekBar arg0)
                {
                }

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
                {
                    mediaPlayer.seekTo(progress);
                }

            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void playerButtons(View view) throws IOException {
        switch (view.getId()) {
            case R.id.PlayButton:

                if (songLoaded != true) {
                    mediaPlayer = MediaPlayer.create(this, R.raw.alltheboys);
                    songLoaded = true;
                    songSeekbar.setEnabled(true);
                    initControls();
                }

                if (mediaPlayer.isPlaying() == true)
                    mediaPlayer.pause();
                else if (mediaPlayer.isPlaying() == false) {
                    mediaPlayer.start();
                }


                break;

            case R.id.StopButton:

                if(mediaPlayer !=null)
                {
                    mediaPlayer.stop();
                    songSeekbar.setEnabled(false);
                }

                songLoaded = false;

                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mediaPlayer !=null)
        {
            mediaPlayer.stop();
            mediaPlayer.seekTo(0);

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}

package com.example.admin.week1weekendhw;

import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity

        implements NavigationView.OnNavigationItemSelectedListener {
    private static final int CAMERA_REQUEST = 1888;
    private static final String TAG = "KIWI";
    ArrayList<RadioStationsSerializable> rStationArray = new ArrayList<RadioStationsSerializable>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Camera Llama Power", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                try {
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    // Vibrate for 500 milliseconds
                    v.vibrate(50000);
                } catch (Exception e) {
                    Log.d(TAG, "vibrator: " + e);
                }

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_favoriteMusic) {
            SharedPreferences prefs = getSharedPreferences("FavoriteStation", MODE_PRIVATE);
            if (prefs != null) {
                goToRadio();
            } else {
                Toast.makeText(getApplicationContext(), "No favorite station chosen", Toast.LENGTH_SHORT).show();
                goToSettings();
            }
        }
        if (id == R.id.action_settings) {
            goToSettings();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void goToSettings() {
        instantiateRadioStations();
        Intent detailsIntent = new Intent(this, SettingsSectionActivity.class);
        Bundle args = new Bundle();
        args.putSerializable("ARRAYLIST", (Serializable) rStationArray);

        detailsIntent.putExtras(args);
        Log.d(TAG, "onNavigationItemSelected TAG!!: " + args.toString());
        PendingIntent pendingIntent =
                TaskStackBuilder.create(this)
                        .addNextIntentWithParentStack(detailsIntent)
                        .getPendingIntent(0, PendingIntent.FLAG_CANCEL_CURRENT);
        startActivity(detailsIntent);
    }

    private void instantiateRadioStations() {
        rStationArray.clear();
        RadioStationsSerializable rsSerializable = new RadioStationsSerializable();
        rsSerializable.setStationID(0);
        rsSerializable.setStationName("RPP");
        rsSerializable.setStationLink("http://str.radioplayerperu.com/rpp.mp3");
        rStationArray.add(rsSerializable);

        rsSerializable = new RadioStationsSerializable();
        rsSerializable.setStationID(1);
        rsSerializable.setStationName("Studio92");
        rsSerializable.setStationLink("http://str.radioplayerperu.com/st9.mp3");
        rStationArray.add(rsSerializable);

        rsSerializable = new RadioStationsSerializable();
        rsSerializable.setStationID(2);
        rsSerializable.setStationName("Kpopway Radio");
        rsSerializable.setStationLink("http://198.105.214.140:2684/Live");
        rStationArray.add(rsSerializable);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

            Intent detailsIntent = new Intent(this, CameraActivity.class);
            PendingIntent pendingIntent =
                    TaskStackBuilder.create(this)
                            .addNextIntentWithParentStack(detailsIntent)
                            .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            startActivity(detailsIntent);

           // Toast.makeText(getApplicationContext(), "Piru likes chicken", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_pdf) {
            Intent detailsIntent = new Intent(this, PDFActivity.class);
            PendingIntent pendingIntent =
                    TaskStackBuilder.create(this)
                            .addNextIntentWithParentStack(detailsIntent)
                            .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            startActivity(detailsIntent);

        } else if (id == R.id.nav_pdf1) {
            File pdfFile = new File("/storage/emulated/0/Download/nerd.pdf");

            try {
                if (pdfFile.exists()) {
                    Uri path = Uri.fromFile(pdfFile);
                    Intent objIntent = new Intent(Intent.ACTION_VIEW);
                    objIntent.setDataAndType(path, "application/pdf");
                    objIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(objIntent);
                } else {
                    Toast.makeText(getApplicationContext(), "Not Found", Toast.LENGTH_SHORT).show();
                }
            } catch (ActivityNotFoundException e) {
                Toast.makeText(getApplicationContext(), "no viewer ap foudn", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (id == R.id.nav_youtube) {
            Intent detailsIntent = new Intent(this, YoutubeActivity.class);
            PendingIntent pendingIntent =
                    TaskStackBuilder.create(this)
                            .addNextIntentWithParentStack(detailsIntent)
                            .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            startActivity(detailsIntent);
        } else if (id == R.id.nav_radio) {
            goToRadio();

        } else if (id == R.id.nav_Music) {
            Intent detailsIntent = new Intent(this, MusicActivity.class);
            PendingIntent pendingIntent =
                    TaskStackBuilder.create(this)
                            .addNextIntentWithParentStack(detailsIntent)
                            .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            startActivity(detailsIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void goToRadio() {
        instantiateRadioStations();
        Intent detailsIntent = new Intent(this, RadioActivity.class);
        Bundle args = new Bundle();
        args.putSerializable("ARRAYLIST", (Serializable) rStationArray);
        detailsIntent.putExtras(args);
        Log.d(TAG, "onNavigationItemSelected TAG!!: " + args.toString());
        PendingIntent pendingIntent =
                TaskStackBuilder.create(this)
                        .addNextIntentWithParentStack(detailsIntent)
                        .getPendingIntent(0, PendingIntent.FLAG_CANCEL_CURRENT);
        startActivity(detailsIntent);

    }


}

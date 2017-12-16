package com.example.eduardo.myapplication;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.preference.PreferenceActivity;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.startup.RegionBootstrap;
import org.altbeacon.beaconreference.ConfiguringActivity;

import java.util.ArrayList;

import static android.system.Os.remove;

public class MenuPrincipal extends AppCompatActivity {
    private static final String TAG = "BeaconReferenceApp";
    private static final int REQUEST_ENABLE_BT = 1;
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    private int timer;
    private ArrayList<String> beacons = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.menu_tb);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        //verifyRequirements();

            doFirstRun();


        BeaconManager beaconManager = org.altbeacon.beacon.BeaconManager.getInstanceForApplication(this);
        beaconManager.getBeaconParsers().clear();
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
    }


    private void doFirstRun() {
        Context context = this;
        boolean isFirstRun = getSharedPreferences("prefers", context.MODE_PRIVATE).getBoolean("isFirstRun", true);
        // Place your dialog code here to display the dialog
        if (isFirstRun) {
            getSharedPreferences("prefers", context.MODE_PRIVATE)
                    .edit()
                    .putBoolean("isFirstRun", false)
                    .apply();

            tutorial();
        }
    }

    private void tutorial() {
        ConstraintLayout main = (ConstraintLayout) findViewById(R.id.coordinatorLayout);

        LayoutInflater layoutInflater = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        View view = layoutInflater.inflate(R.layout.tutorial, main);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ConstraintLayout tut = (ConstraintLayout) findViewById(R.id.tuto);
                        ((ViewGroup) tut.getParent()).removeView(tut);
                    }
                });
            }
        });

        /*AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);
        // set title
        alertDialogBuilder.setTitle("Tutorial");

        // set dialog message
        alertDialogBuilder
                .setMessage("Click ok to exit!")
                .setCancelable(false)
                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();*/
    }

    @Override
    protected  void onResume() {
        super.onResume();
        //verifyRequirements();
    }

    public void play(View view){
        Intent it = new Intent(MenuPrincipal.this, org.altbeacon.beaconreference.RangingActivity.class);
        //PASSANDO O VALOR DO TIMER PARA A PROXIMA ATIVIDADE
        Intent jogo = it.putExtra("timer", timer);
        jogo = jogo.putExtra("beacons", beacons);
        //int tt = it.getIntExtra("timer", timer);
        //System.out.println(tt);
        startActivity(jogo);
        ///////////////////////////////////
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        /////////////////////////////////////
        if (id == R.id.cfg) {
            Intent it = new Intent(this, org.altbeacon.beaconreference.ConfiguringActivity.class);
            startActivityForResult(it, 0);
            return true;
        }
        ////////////////////////////////////
        if (id == R.id.tut) {
            tutorial();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            timer = data.getIntExtra("timer", 0);
            beacons = data.getStringArrayListExtra("beacons");
            if(beacons.size() > 0) {
                Log.d(TAG, "onActivityResult - timer: " + timer + " - 1o beacon: " + beacons.iterator().next());
            }
            else{
                Log.d(TAG, "onActivityResult - timer: " + timer + " - no beacon configured ");
            }
        }
        else {
            Log.d(TAG, "Error fetching results. resultCode = " + resultCode);
        }
    }

    private void verifyRequirements() {
        try {
            if (!BeaconManager.getInstanceForApplication(this).checkAvailability()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        } catch (RuntimeException e) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Bluetooth LE not available");
            builder.setMessage("Sorry, this device does not support Bluetooth LE.");
            builder.setPositiveButton(android.R.string.ok, null);
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface dialog) {
                    finish();
                    System.exit(0);
                }

            });
            builder.show();

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Android M Permission check
            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("This app needs location access");
                builder.setMessage("Please grant location access so this app can detect beacons.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                    @TargetApi(23)
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                PERMISSION_REQUEST_COARSE_LOCATION);
                    }

                });
                builder.show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "coarse location permission granted");
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Functionality limited");
                    builder.setMessage("Since location access has not been granted, this app will not be able to discover beacons.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }

                    });
                    builder.show();
                }
                return;
            }
        }
    }
}

package com.example.eduardo.myapplication;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.startup.RegionBootstrap;
import org.altbeacon.beaconreference.ConfiguringActivity;

public class MenuPrincipal extends AppCompatActivity {
    private static final String TAG = "BeaconReferenceApp";
    private static final int REQUEST_ENABLE_BT = 1;
    private int timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.menu_tb);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        //verifyBluetooth();

        BeaconManager beaconManager = org.altbeacon.beacon.BeaconManager.getInstanceForApplication(this);
        beaconManager.getBeaconParsers().clear();
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));

        //RECBENDO VALOR DO TIMER
        Intent extras = getIntent();
        timer = extras.getIntExtra("timer", 0);
        System.out.println(timer);
        //////////////////////////////
    }

    @Override
    protected  void onResume() {
        super.onResume();
        //verifyBluetooth();
    }

    public void play(View view){
        Intent it = new Intent(MenuPrincipal.this, org.altbeacon.beaconreference.RangingActivity.class);
        //PASSANDO O VALOR DO TIMER PARA A PROXIMA ATIVIDADE
        Intent jogo = it.putExtra("timer", timer);
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
        //Funçoes para setar o TIME
        if (id == R.id.cfg) {
            //MODO DE EXIBICAO  de MENSAGEM 1
            // Toast.makeText(getApplicationContext(), "Easy selected",
            //      Toast.LENGTH_SHORT).show();

            //MODO DE EXIBICAO  de MENSAGEM 2
            Intent it = new Intent(this, org.altbeacon.beaconreference.ConfiguringActivity.class);
            startActivity(it);
            return true;
        }
        ////////////////////////////////////

        return super.onOptionsItemSelected(item);
    }

    private void verifyBluetooth() {

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

    }
}

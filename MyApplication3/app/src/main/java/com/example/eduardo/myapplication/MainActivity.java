package com.example.eduardo.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;

public class MainActivity extends Activity {

    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IniciaSplash();
    }

    public void IniciaSplash(){

        setContentView(R.layout.activity_main);
        new Thread(new Runnable() {

            @Override
            public void run() {
                counter++;

                try{

                    while(counter == 1 || counter <= 3) {

                        Thread.sleep(1000);
                        counter++;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(counter == 4){

                    Intent it = new Intent(MainActivity.this, MenuPrincipal.class);
                    startActivity(it);

                    counter++;
                }
            }
        }).start();

    }
}

package org.altbeacon.beaconreference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ListActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.shapes.Shape;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.RemoteException;
import android.support.annotation.ColorInt;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eduardo.myapplication.MenuPrincipal;
import com.example.eduardo.myapplication.R;

import org.altbeacon.beacon.AltBeacon;
import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import cn.iwgang.countdownview.CountdownView;

import static android.media.MediaExtractor.MetricsConstants.FORMAT;

public class RangingActivity extends Activity implements BeaconConsumer {
    protected static final String TAG = "RangingActivity";
    private BeaconManager beaconManager = BeaconManager.getInstanceForApplication(this);
    private ArrayList<String> beaconsConf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animationball);
        //setContentView(R.layout.activity_ranging); CODIGO ANTERIOR

        //RECEBENDO O VALOR DO TIMER EM UM INT
        Intent extras = getIntent();
        int timer = extras.getIntExtra("timer", 0);
        beaconsConf = extras.getStringArrayListExtra("beacons");
        //System.out.println(timer);

        //TIMER - ALGORITMO CRIADO REPOSITORIO GITHUB
        CountdownView countDownTimer = findViewById(R.id.countdownview);
        countDownTimer.start(timer*1000*60);

        beaconManager.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(this);
    }

    @Override 
    protected void onPause() {
        super.onPause();
    }

    @Override 
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.addRangeNotifier(new RangeNotifier() {
           @Override
           public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
              if (beacons.size() > 0) {
                  for(Beacon i : beacons) {
                      if(beaconsConf.contains(i.getId1().toString())) {
                          logToDisplay(i.getDistance());
                          Log.d(TAG, "OK - Id: " + i.getId1().toString());
                      }
                      else{
                          Log.d(TAG, "Beacon not configured detected. Id: " + i.getId1().toString());
                      }
                  }
              }
           }

        });

        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {   }
    }

    private void logToDisplay(final double distance) {
        runOnUiThread(new Runnable() {
            @SuppressLint("DefaultLocale")
            public void run() {
                ImageView circleColor = findViewById(R.id.bounceBallImage);

                int bleue = 51*(int)Math.round(distance);
                if (bleue > 255){
                    bleue = 255;
                }
                if (bleue < 0){
                    bleue = 0;
                }
                int rouge = 255 - bleue;


                int couleur = 65536*rouge + bleue - 16777216;

                //troca a cor do objeto
                ((GradientDrawable)circleColor.getBackground()).setColor(couleur);
            }
        });
    }
}

package org.altbeacon.beaconreference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.shapes.Shape;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.RemoteException;
import android.support.annotation.ColorInt;
import android.support.constraint.ConstraintLayout;
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
    private int numBeacons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animationball);
        //setContentView(R.layout.activity_ranging); CODIGO ANTERIOR

        //RECEBENDO O VALOR DO TIMER EM UM INT
        Intent extras = getIntent();
        int timer = extras.getIntExtra("timer", 0);
        beaconsConf = extras.getStringArrayListExtra("beacons");
        numBeacons = beaconsConf.size();

        ConstraintLayout holdBalls = (ConstraintLayout) findViewById(R.id.holdBalls);

        LayoutInflater layoutInflater = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        View view;
        switch (numBeacons){
            case 1:
                view = layoutInflater.inflate(R.layout.ball1, holdBalls);
                break;
            case 2:
                view = layoutInflater.inflate(R.layout.ball2, holdBalls);
                break;
            case 3:
                view = layoutInflater.inflate(R.layout.ball3, holdBalls);
                break;
            case 4:
                view = layoutInflater.inflate(R.layout.ball4, holdBalls);
                break;
            case 5:
                view = layoutInflater.inflate(R.layout.ball5, holdBalls);
                break;
            default:
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Illegal number of Beacons selected");
                builder.setMessage("Sorry, the number of Beacons selected is zero or not supported. " +
                        "Please select a valid number of Beacons.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        finish();
                    }
                });
                builder.show();
                timer = 0;
                break;
        }

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
                      int ind = beaconsConf.indexOf(i.getId1().toString());
                      if(ind != -1) {
                          logToDisplay(i.getDistance(), ind+1);
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

    private void logToDisplay(final double distance, final int whi) {
        runOnUiThread(new Runnable() {
            @SuppressLint("DefaultLocale")
            public void run() {
                int bleue = 51*(int)Math.round(distance);
                if (bleue > 255){
                    bleue = 255;
                }
                if (bleue < 0){
                    bleue = 0;
                }
                int rouge = 255 - bleue;
                int couleur = 65536*rouge + bleue - 16777216;

                ImageView circleColor;
                switch (whi){
                    case 1:
                        circleColor = findViewById(R.id.bounceBallImage1);
                        //troca a cor do objeto
                        ((GradientDrawable)circleColor.getBackground()).setColor(couleur);
                        break;
                    case 2:
                        circleColor = findViewById(R.id.bounceBallImage2);
                        //troca a cor do objeto
                        ((GradientDrawable)circleColor.getBackground()).setColor(couleur);
                        break;
                    case 3:
                        circleColor = findViewById(R.id.bounceBallImage3);
                        //troca a cor do objeto
                        ((GradientDrawable)circleColor.getBackground()).setColor(couleur);
                        break;
                    case 4:
                        circleColor = findViewById(R.id.bounceBallImage4);
                        //troca a cor do objeto
                        ((GradientDrawable)circleColor.getBackground()).setColor(couleur);
                        break;
                    case 5:
                        circleColor = findViewById(R.id.bounceBallImage5);
                        //troca a cor do objeto
                        ((GradientDrawable)circleColor.getBackground()).setColor(couleur);
                        break;
                }
            }
        });
    }
}

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animationball);
        //setContentView(R.layout.activity_ranging); CODIGO ANTERIOR

        //RECEBENDO O VALOR DO TIMER EM UM INT
        Intent extras = getIntent();
        int timer = extras.getIntExtra("timer", 0);
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
        beaconManager.setRangeNotifier(new RangeNotifier() {
           @Override
           public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
              if (beacons.size() > 0) {
                 //EditText editText = (EditText)RangingActivity.this.findViewById(R.id.rangingText);
                 //Beacon firstBeacon = beacons.iterator().next();
                 //logToDisplay("The first beacon " + firstBeacon.toString() + " is about " + firstBeacon.getDistance() + " meters away.");
/*                  for(Iterator<Beacon> i = beacons.iterator(); i.hasNext();) {
                      Beacon firstBeacon = i.next();
                      switch(firstBeacon.getId1().toString()){
                          case "1cc68855-6883-4300-a884-411ecc6688cc":
                              logToDisplay1(firstBeacon.getId1().toString(), firstBeacon.getDistance());
                              break;

                          case "9a33d88a-a00a-488f-be99-deeaadff88dd":
                              logToDisplay2(firstBeacon.getId1().toString(), firstBeacon.getDistance());
                              break;

                          default:
                              break;
                      }
                  }*/
                  for(Beacon i : beacons) {
                      switch(i.getId1().toString()){
                          case "1cc68855-6883-4300-a884-411ecc6688cc":
                              logToDisplay1(i.getId1().toString(), i.getDistance());
                              break;

                          case "9a33d88a-a00a-488f-be99-deeaadff88dd":
                              logToDisplay2(i.getId1().toString(), i.getDistance());
                              break;

                          default:
                              break;
                      }
                  }
              }
           }

        });

        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {   }
    }

    private void logToDisplay2(final String uuid, final double distance) {
        runOnUiThread(new Runnable() {
            @SuppressLint("DefaultLocale")
            public void run() {
                //EditText editText = RangingActivity.this.findViewById(R.id.rangingText2);

//                String affiche;
//                if (distance >= 2) {
//                    affiche = uuid + " est à moins de " + String.format("%.2f", distance) + " mètres de distance." + "\n";
//                }
//                else{
//                    affiche = uuid + " est à moins de " + String.format("%.2f", distance) + " mètre de distance." + "\n";
//                }
//                editText.setText(affiche);

                //seta a variavel como animationball.xml
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

    private void logToDisplay1(final String uuid, final double distance) {
        runOnUiThread(new Runnable() {
            public void run() {
//                EditText editText = (EditText)RangingActivity.this.findViewById(R.id.rangingText1);
//
//                String affiche;
//                if (distance >= 2) {
//                    affiche = uuid + " est à moins de " + String.format("%.2f", distance) + " mètres de distance." + "\n";
//                }
//                else{
//                    affiche = uuid + " est à moins de " + String.format("%.2f", distance) + " mètre de distance." + "\n";
//                }
//                editText.setText(affiche);

                //seta a variavel como animationball.xml
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

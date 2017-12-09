package org.altbeacon.beaconreference;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.eduardo.myapplication.R;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.ArrayList;
import java.util.List;

public class ConfiguringActivity extends AppCompatActivity implements BeaconConsumer {
    protected static final String TAG = "ConfiguringActivity";
    private BeaconManager beaconManager = BeaconManager.getInstanceForApplication(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuring);
        Toolbar toolbar = (Toolbar) findViewById(R.id.conf_tb);
        setSupportActionBar(toolbar);

/*
        Button easy = (Button) findViewById(R.id.easy);
        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v.findViewById(android.R.id.content),"Easy selected",
                        Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        Button normal = (Button) findViewById(R.id.normal);
        normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v.findViewById(android.R.id.content),"Normal selected",
                        Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        Button hard = (Button) findViewById(R.id.hard);
        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v.findViewById(android.R.id.content),"Hard selected",
                        Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
*/

        beaconManager.bind(this);

        ListView lv = (ListView) findViewById(R.id.teste);

        List<String> your_array_list = new ArrayList<String>();
        your_array_list.add("foo");
        your_array_list.add("bar");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                your_array_list);

        lv.setAdapter(arrayAdapter);
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

    public void easy(View view){
        Snackbar.make(this.findViewById(android.R.id.content),"Easy selected",
                Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }
    public void normal(View view){
        Snackbar.make(this.findViewById(android.R.id.content),"Normal selected",
                Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }
    public void hard(View view){
        Snackbar.make(this.findViewById(android.R.id.content),"Hard selected",
                Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    @Override
    public void onBeaconServiceConnect() {
/*        beaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {

                if (beacons.size() > 0) {
                    for(Beacon i : beacons) {

                    }
                }
            }

        });

        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {   }*/
    }
}

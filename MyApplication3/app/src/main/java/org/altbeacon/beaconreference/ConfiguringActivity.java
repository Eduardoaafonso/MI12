package org.altbeacon.beaconreference;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.design.widget.Snackbar;
import android.view.Menu;
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
import java.util.Collection;
import java.util.List;

public class ConfiguringActivity extends AppCompatActivity implements BeaconConsumer {
    protected static final String TAG = "ConfiguringActivity";
    private BeaconManager beaconManager = BeaconManager.getInstanceForApplication(this);
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_configuring);
            Toolbar toolbar = (Toolbar) findViewById(R.id.conf_tb);
            setSupportActionBar(toolbar);
        }
        catch (Exception e){
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Error");
            builder.setMessage("Sorry, there seems to be an errror while loading the Content View.");
            builder.setPositiveButton(android.R.string.ok, null);
        }

        //beaconManager = BeaconManager.getInstanceForApplication(this);
        lv = (ListView) findViewById(R.id.teste);

        try {
            beaconManager.bind(this);
        }
        catch (RuntimeException e){
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Error");
            builder.setMessage("Sorry, there seems to be an errror while loading the Beacon Manager.");
            builder.setPositiveButton(android.R.string.ok, null);
        }
/*        List<String> your_array_list = new ArrayList<String>();
        your_array_list.add("foo");
        your_array_list.add("bar");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                your_array_list);

        lv.setAdapter(arrayAdapter);*/
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

package org.altbeacon.beaconreference;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.eduardo.myapplication.MenuPrincipal;
import com.example.eduardo.myapplication.R;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.simulator.StaticBeaconSimulator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ConfiguringActivity extends AppCompatActivity implements BeaconConsumer {
    protected static final String TAG = "ConfiguringActivity";
    private BeaconManager beaconManager;
    private ArrayList<Beacon> inrange = new ArrayList<Beacon>();
    private ArrayList<String> beacons = new ArrayList<String>();
    private int timer = 5;
    private UsersAdapter arrayAdapter;
    private Intent confs = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuring);
        Toolbar toolbar = (Toolbar) findViewById(R.id.conf_tb);
        setSupportActionBar(toolbar);

        beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.bind(this);

        arrayAdapter = new UsersAdapter(this, inrange);
        ListView lv = (ListView) findViewById(R.id.teste);
        lv.setAdapter(arrayAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // argument position gives the index of item which is clicked
            public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
                if(!beacons.contains(inrange.get(position).getId1().toString())) {
                    Snackbar.make(findViewById(android.R.id.content), inrange.get(position).getId1().toString() + " added",
                            Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    beacons.add(inrange.get(position).getId1().toString());
                }
                else{
                    Snackbar.make(findViewById(android.R.id.content), inrange.get(position).getId1().toString() + " removed",
                            Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    beacons.remove(inrange.get(position).getId1().toString());
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        confs.putExtra("timer", timer);
        confs.putExtra("beacons", beacons);
        setResult(RESULT_OK, confs);

        super.onBackPressed();
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
                //SETANDO TIMER
        timer = 5;
    }
    public void normal(View view){
        Snackbar.make(this.findViewById(android.R.id.content),"Normal selected",
                Snackbar.LENGTH_LONG).setAction("Action", null).show();
        //SETANDO TIMER
        timer = 3;
    }
    public void hard(View view){
        Snackbar.make(this.findViewById(android.R.id.content),"Hard selected",
                Snackbar.LENGTH_LONG).setAction("Action", null).show();
        //SETANDO TIMER
        timer = 1;
    }

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.addRangeNotifier(new RangeNotifier() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @SuppressLint("DefaultLocale")
            @Override
            public void didRangeBeaconsInRegion(final Collection<Beacon> beacons, Region region) {
                if(beacons.size() > 0) {
                    for(Beacon i : beacons) {
                        Log.d(TAG, "Beacons: " + i.getId1().toString());
                        int ind = inrange.indexOf(i);
                        if (ind != -1) {
                            inrange.set(ind, i);
                        }
                        else{
                            inrange.add(i);
                        }

                        Collections.sort(inrange, new Comparator<Beacon>() {
                            @Override
                            public int compare(Beacon b1, Beacon b2) {
                                return Double.compare(b1.getDistance(), b2.getDistance());
                            }
                        });
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            arrayAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });

        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {   }
    }

    private class UsersAdapter extends ArrayAdapter<Beacon> {
        // View lookup cache
        private class ViewHolder {
            TextView name;
            TextView home;
        }

        UsersAdapter(Context context, ArrayList<Beacon> users) {
            super(context, R.layout.item, users);
        }

        @SuppressLint("DefaultLocale")
        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            // Get the data item for this position
            Beacon b = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            ViewHolder viewHolder; // view lookup cache stored in tag
            if (convertView == null) {
                // If there's no view to re-use, inflate a brand new view for row
                viewHolder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.item, parent, false);
                viewHolder.name = convertView.findViewById(R.id.beaconName);
                viewHolder.home = convertView.findViewById(R.id.beaconDistance);
                // Cache the viewHolder object inside the fresh view
                convertView.setTag(viewHolder);
            } else {
                // View is being recycled, retrieve the viewHolder object from tag
                viewHolder = (ViewHolder) convertView.getTag();
            }
            // Populate the data from the data object via the viewHolder object
            // into the template view.
            assert b != null;
            viewHolder.name.setText(b.getId1().toString());
            viewHolder.home.setText(String.format("%.2f", b.getDistance()));
            // Return the completed view to render on screen
            return convertView;
        }
    }
}
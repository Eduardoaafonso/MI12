package org.altbeacon.beaconreference;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.eduardo.myapplication.MenuPrincipal;
import com.example.eduardo.myapplication.R;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.simulator.StaticBeaconSimulator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ConfiguringActivity extends AppCompatActivity implements BeaconConsumer {
    protected static final String TAG = "ConfiguringActivity";
    private BeaconManager beaconManager = BeaconManager.getInstanceForApplication(this);
    private ListView lv;
    public ArrayList<Beacon> inrange = new ArrayList<Beacon>();
    private UsersAdapter arrayAdapter;
    private Intent timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuring);
        Toolbar toolbar = (Toolbar) findViewById(R.id.conf_tb);
        setSupportActionBar(toolbar);

        beaconManager.bind(this);

        Beacon novo = new Beacon.Builder()
                .setId1("2F234454-CF6D-4A0F-ADF2-F4911BA9FFA6")
                .setId2("1")
                .setId3("2")
                .build();


        inrange.add(novo);
        inrange.add(novo);
        inrange.add(novo);
        inrange.add(novo);

        arrayAdapter = new UsersAdapter(this, inrange);
        lv = (ListView) findViewById(R.id.teste);
        lv.setAdapter(arrayAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // argument position gives the index of item which is clicked
            public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
                Snackbar.make(findViewById(android.R.id.content), inrange.get(position).getId1().toString(),
                        Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
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
                Intent i = new Intent(this, MenuPrincipal.class);
                timer = i.putExtra("timer",5);
                startActivity(timer);
    }
    public void normal(View view){
        Snackbar.make(this.findViewById(android.R.id.content),"Normal selected",
                Snackbar.LENGTH_LONG).setAction("Action", null).show();
        //SETANDO TIMER
        Intent i = new Intent(this, MenuPrincipal.class);
        timer = i.putExtra("timer",3);
        startActivity(timer);
    }
    public void hard(View view){
        Snackbar.make(this.findViewById(android.R.id.content),"Hard selected",
                Snackbar.LENGTH_LONG).setAction("Action", null).show();
        //SETANDO TIMER
        Intent i = new Intent(this, MenuPrincipal.class);
        timer = i.putExtra("timer",1);
        startActivity(timer);
    }



    @Override
    public void onBeaconServiceConnect() {
        beaconManager.setRangeNotifier(new RangeNotifier() {
            @SuppressLint("DefaultLocale")
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
/*                inrange.clear();
                inrange.addAll(beacons);

                arrayAdapter.clear();
                arrayAdapter.addAll(inrange);
                arrayAdapter.notifyDataSetChanged();*/
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

        public UsersAdapter(Context context, ArrayList<Beacon> users) {
            super(context, R.layout.item, users);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            Beacon b = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            ViewHolder viewHolder; // view lookup cache stored in tag
            if (convertView == null) {
                // If there's no view to re-use, inflate a brand new view for row
                viewHolder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.item, parent, false);
                viewHolder.name = (TextView) convertView.findViewById(R.id.beaconName);
                viewHolder.home = (TextView) convertView.findViewById(R.id.beaconDistance);
                // Cache the viewHolder object inside the fresh view
                convertView.setTag(viewHolder);
            } else {
                // View is being recycled, retrieve the viewHolder object from tag
                viewHolder = (ViewHolder) convertView.getTag();
            }
            // Populate the data from the data object via the viewHolder object
            // into the template view.
            viewHolder.name.setText(b.getId1().toString());
            viewHolder.home.setText(String.format("%.2f", b.getDistance()));
            // Return the completed view to render on screen
            return convertView;
        }
    }
}
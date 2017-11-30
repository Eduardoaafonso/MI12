package org.altbeacon.beaconreference;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.eduardo.myapplication.R;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class ConfiguringActivity extends ListActivity implements BeaconConsumer {
    protected static final String TAG = "ConfiguringActivity";
    private BeaconManager beaconManager = BeaconManager.getInstanceForApplication(this);
    private LeDeviceListAdapter mLeDeviceListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuring);
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
        mLeDeviceListAdapter.clear();
        if (beaconManager.isBound(this)) beaconManager.setBackgroundMode(false);
    }

    @Override 
    protected void onResume() {
        super.onResume();
        // Initializes list view adapter.
        mLeDeviceListAdapter = new LeDeviceListAdapter();
        setListAdapter(mLeDeviceListAdapter);
        if (beaconManager.isBound(this)) beaconManager.setBackgroundMode(false);
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
                  for(Iterator<Beacon> i = beacons.iterator(); i.hasNext();) {
                      Beacon firstBeacon = i.next();
                      mLeDeviceListAdapter.addDevice(firstBeacon.getId1().toString());
                  }
                  mLeDeviceListAdapter.notifyDataSetChanged();
              }
           }

        });
    }

    // Adapter for holding devices found through scanning.
    private class LeDeviceListAdapter extends BaseAdapter {
        private ArrayList<String> mLeDevices;
        private LayoutInflater mInflator;

        public LeDeviceListAdapter() {
            super();
            mLeDevices = new ArrayList<String>();
            mInflator = ConfiguringActivity.this.getLayoutInflater();
        }

        public void addDevice(String device) {
            if(!mLeDevices.contains(device)) {
                mLeDevices.add(device);
            }
        }

        public String getDevice(int position) {
            return mLeDevices.get(position);
        }

        public void clear() {
            mLeDevices.clear();
        }

        @Override
        public int getCount() {
            return mLeDevices.size();
        }

        @Override
        public Object getItem(int i) {
            return mLeDevices.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            // General ListView optimization code.
            if (view == null) {
                view = mInflator.inflate(R.layout.activity_ranging, null);
                viewHolder = new ViewHolder();
                viewHolder.device = (TextView) view.findViewById(R.id.device);
                viewHolder.checkBox = (CheckBox) view.findViewById(R.id.checkBox);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            String deviceName = mLeDevices.get(i);
            if (deviceName != null && deviceName.length() > 0)
                viewHolder.device.setText(deviceName);
            else
                viewHolder.device.setText("Dispositif unconnu");

            return view;
        }
    }

    static class ViewHolder {
        TextView device;
        CheckBox checkBox;
    }
}

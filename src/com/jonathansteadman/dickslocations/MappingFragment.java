
package com.jonathansteadman.dickslocations;

import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MappingFragment extends SupportMapFragment implements Constants {

    protected GoogleMap googleMap;

    private ArrayList<Dicks> dicksLocs;

    private LocationTrackerManager locationManager;

    private Location mLastLocation;

    private MarkerOptions myMarker;

    private boolean stopMoving = false;

    private Dicks dicks;

    int id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() == null) {
            id = getActivity().getIntent().getIntExtra(EXTRA_DICKS_ID, 0);
        } else {
            id = getArguments().getInt(EXTRA_DICKS_ID);
        }

        dicks = DicksSet.getInstance().getLocation(id);
        setRetainInstance(true);
        locationManager = LocationTrackerManager.get(getActivity());
        locationManager.startLocationUpdates();

        dicksLocs = DicksSet.getInstance().getLocations();
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().registerReceiver(mLocationReceiver,
                new IntentFilter(LocationTrackerManager.ACTION_LOCATION));
    }

    @Override
    public void onStop() {
        getActivity().unregisterReceiver(mLocationReceiver);
        super.onStop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);

        googleMap = getMap();
        googleMap.setMyLocationEnabled(true);
        addMarkers();
        updateUI();

        return view;
    }

    public void addMarkers() {
        for (Dicks dicks : dicksLocs) {
            MarkerOptions marker = new MarkerOptions().position(dicks.getLocation()).title(
                    dicks.getTitle());

            googleMap.addMarker(marker);
        }
    }

    public void updateUI() {

        if (mLastLocation == null)
            return;

        LatLng lastPostion = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        // On launch it centers on your location. However, it would constantly
        // center on your location since it was always updating. This is how I
        // worked around that so you can move around the map...
        if (stopMoving == false) {
            if (id >= 0 && id <= 5) {
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(dicks.getLocation(), 15));
            } else {
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastPostion, 13));
            }
            stopMoving = true;
        }

        if (myMarker == null) {
            myMarker = new MarkerOptions().position(lastPostion).title("ME");
            googleMap.addMarker(myMarker);
        }

    }

    private BroadcastReceiver mLocationReceiver = new LocationReceiver() {

        @Override
        protected void onLocationReceived(Context context, Location loc) {
            mLastLocation = loc;
            updateUI();
        }

        @Override
        protected void onProviderEnabledChanged(boolean enabled) {

        }

    };
}

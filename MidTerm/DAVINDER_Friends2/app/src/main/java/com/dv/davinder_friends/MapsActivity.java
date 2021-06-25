package com.dv.davinder_friends;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;

import com.dv.davinder_friends.databinding.ActivityMapsBinding;
import com.dv.davinder_friends.models.Friend;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private LatLng currentLocation;
    private LatLng friendLocation;
    private LocationHelper locationHelper;
    private LocationCallback locationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        this.friendLocation = new LatLng(this.getIntent().getDoubleExtra("EXTRA_FRIEND_LAT", 0.0),
                this.getIntent().getDoubleExtra("EXTRA_FRIEND_LNG", 0.0));

        this.currentLocation = new LatLng(this.getIntent().getDoubleExtra("EXTRA_LAT", 0.0),
                this.getIntent().getDoubleExtra("EXTRA_LNG", 0.0));

        this.locationHelper = LocationHelper.getInstance();
        this.locationHelper.checkPermissions(this);
        if (this.locationHelper.locationPermissionGranted){
            this.initiateLocationListener();
        }

    }

    private void initiateLocationListener(){
        this.locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                for (Location loc : locationResult.getLocations()){
                    currentLocation = new LatLng(loc.getLatitude(), loc.getLongitude());
                }
            }
        };

        this.locationHelper.requestLocationUpdates(this, locationCallback);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.locationHelper.stopLocationUpdates(this, this.locationCallback);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.locationHelper.requestLocationUpdates(this, this.locationCallback);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.addMarker(new MarkerOptions().position(this.currentLocation).title("You're here"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(this.currentLocation, 15.0f));

        mMap.addMarker(new MarkerOptions().position(this.friendLocation).title("Your Friend is here"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(this.currentLocation, 15.0f));

        mMap.setBuildingsEnabled(true);
        mMap.setTrafficEnabled(true);
        mMap.setIndoorEnabled(false);
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        UiSettings myUiSetting = googleMap.getUiSettings();
        myUiSetting.setZoomControlsEnabled(true);
        myUiSetting.setZoomGesturesEnabled(true);
        myUiSetting.setCompassEnabled(true);
    }

}


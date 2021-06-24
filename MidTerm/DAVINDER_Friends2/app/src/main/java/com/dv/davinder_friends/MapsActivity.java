package com.dv.davinder_friends;

import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.dv.davinder_friends.databinding.ActivityMapsBinding;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity  extends AppCompatActivity{//extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private LatLng currentLocation;
    private LocationHelper locationHelper;
    private LocationCallback locationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);//


        /*binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

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
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15.0f));
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
     *//*
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mMap.addMarker(new MarkerOptions().position(this.currentLocation).title("You're here"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(this.currentLocation));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(this.currentLocation, 15.0f));
        // 1: world, 5: Landmass/continent, 10: City, 15: Street, 20: Building

        mMap.setBuildingsEnabled(true);
        mMap.setTrafficEnabled(true);
        mMap.setIndoorEnabled(false);
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        UiSettings myUiSetting = googleMap.getUiSettings();
        myUiSetting.setZoomControlsEnabled(true);
        myUiSetting.setZoomGesturesEnabled(true);
        myUiSetting.setCompassEnabled(true);
    }
*/}
   }

package com.jk.locationservicesdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.model.LatLng;
import com.jk.locationservicesdemo.databinding.ActivityMainBinding;
import com.jk.locationservicesdemo.helpers.LocationHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private final String TAG = this.getClass().getCanonicalName();
    ActivityMainBinding binding;
    private LocationHelper locationHelper;
    private Location lastLocation;
    private LocationCallback locationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        this.binding.btnReverseGeocoding.setOnClickListener(this::onClick);
        this.binding.btnOpenMap.setOnClickListener(this::onClick);
        
        this.locationHelper = LocationHelper.getInstance();
        this.locationHelper.checkPermissions(this);
        
        if (this.locationHelper.locationPermissionGranted){
            Log.d(TAG, "onCreate: Location Permission Granted");

            //get the last location
            Log.d(TAG, "onCreate: Trying to get last location");
//            this.lastLocation = this.locationHelper.getLastLocation(this);
//            Log.d(TAG, "onCreate: Method call getLastLocation() completed");
//
//            if (this.lastLocation != null){
//                this.binding.tvLocationAddress.setText(this.lastLocation.toString());
//            }else{
//                Log.e(TAG, "onCreate: Last location not obtained");
//                this.binding.tvLocationAddress.setText("Last location not obtained");
//            }

            this.locationHelper.getLastLocation(this).observe(this, new Observer<Location>() {
                @Override
                public void onChanged(Location location) {
                    if (location != null){
                        lastLocation = location;
//                        binding.tvLocationAddress.setText(lastLocation.toString());

                        Address obtainedAddress = locationHelper.performForwardGeocoding(getApplicationContext(), lastLocation);
                        if (obtainedAddress != null){
                            binding.tvLocationAddress.setText(obtainedAddress.getAddressLine(0));
                        }else{
                            binding.tvLocationAddress.setText("Address for Last Location not obtained");
                        }
                    }else{
                        binding.tvLocationAddress.setText("Last Location not obtained");
                    }
                }
            });

            //keep listening to updated location - used to get changes in location usually fro navigation, walking, driving, etc
            //to respond to changing device location
            this.initiateLocationListener();

        }else{
            Log.d(TAG, "onCreate: Location Permission Denied");
        }
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

    @Override
    public void onClick(View v) {
        if (v != null){
            switch (v.getId()){
                case R.id.btn_open_map:{
                    this.openMap();
                    break;
                }
                case R.id.btn_reverse_geocoding:{
                    this.doReverseGeocoding();
                    break;
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == this.locationHelper.REQUEST_CODE_LOCATION){
            this.locationHelper.locationPermissionGranted = (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED);

            if (this.locationHelper.locationPermissionGranted){
                Log.d(TAG, "onRequestPermissionsResult: Result --- Location Permission Granted "
                        + this.locationHelper.locationPermissionGranted);
            }
        }
    }

    private void initiateLocationListener(){
        this.locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                for (Location loc : locationResult.getLocations()){
                    lastLocation = loc;
//                    binding.tvLocationAddress.setText(lastLocation.toString());

                    Address obtainedAddress = locationHelper.performForwardGeocoding(getApplicationContext(), lastLocation);
                    if (obtainedAddress != null){
                        binding.tvLocationAddress.setText(obtainedAddress.getAddressLine(0));
                        Log.d(TAG, "onLocationResult: Country code : " + obtainedAddress.getCountryCode());
                        Log.d(TAG, "onLocationResult: Country : " + obtainedAddress.getCountryName());
                        Log.d(TAG, "onLocationResult: City : " + obtainedAddress.getLocality());
                        Log.d(TAG, "onLocationResult: Postal Code : " + obtainedAddress.getPostalCode());
                        Log.d(TAG, "onLocationResult: Province : " + obtainedAddress.getAdminArea());
                    }else{
                        binding.tvLocationAddress.setText("Address for Last Location not obtained");
                    }
                }
            }
        };

        this.locationHelper.requestLocationUpdates(this, locationCallback);
    }

    private void openMap(){
        Log.d(TAG, "onClick: Open map to show location");
        Intent mapIntent = new Intent(this, MapsActivity.class);
        mapIntent.putExtra("EXTRA_LAT", this.lastLocation.getLatitude());
        mapIntent.putExtra("EXTRA_LNG", this.lastLocation.getLongitude());
        startActivity(mapIntent);
    }

    private void doReverseGeocoding(){
        Log.d(TAG, "onClick: Perform reverse geocoding to get coordinates from address.");
        if (this.locationHelper.locationPermissionGranted){
            String givenAddress = this.binding.editAddress.getText().toString();
            LatLng obtainedCoordinates = this.locationHelper.performReverseGeocoding(this, givenAddress);

            if (obtainedCoordinates != null){
                this.binding.tvLocationCoordinates.setText("Lat : " + obtainedCoordinates.latitude
                        + "\nLng : " + obtainedCoordinates.longitude);
            }else{
                this.binding.tvLocationCoordinates.setText("Coordinates cannot be obtained for given address");
            }
        }else{
            this.binding.tvLocationCoordinates.setText("Couldn't obtain the coordinates as not permission granted for location");
        }
    }
//
//    private void doGeocoding(){
//
//    }
}
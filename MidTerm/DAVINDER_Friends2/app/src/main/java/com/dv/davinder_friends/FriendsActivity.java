package com.dv.davinder_friends;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dv.davinder_friends.adapters.FriendsAdapter;
import com.dv.davinder_friends.databinding.ActivityFriendsBinding;
import com.dv.davinder_friends.models.Friend;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class FriendsActivity extends AppCompatActivity implements OnFriendsItemClickListener{

    ActivityFriendsBinding binding;
    private final String TAG = this.getClass().getCanonicalName();
    private FriendsAdapter adapter;

    private ArrayList<Friend> list;

    private LocationHelper locationHelper;
    private Location lastLocation;
    private LocationCallback locationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityFriendsBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        this.list = new ArrayList<>();

        ArrayList<Friend> extra = this.getIntent().getParcelableArrayListExtra("EXTRA_FRIEND_OBJ");

        this.list = extra;
        if (extra != null) Log.d(TAG, "onCreate: " + extra);


        this.adapter = new FriendsAdapter(this, this.list, this);
        this.binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.binding.recyclerView.setAdapter(adapter);


        this.locationHelper = LocationHelper.getInstance();
        this.locationHelper.checkPermissions(this);

        if (this.locationHelper.locationPermissionGranted) {


            this.locationHelper.getLastLocation(this).observe(this, new Observer<Location>() {
                @Override
                public void onChanged(Location location) {
                    if (location != null) {
                        lastLocation = location;
                    }
                }
            });

            this.initiateLocationListener();

        }
    }

    private void initiateLocationListener(){
        this.locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                for (Location loc : locationResult.getLocations()){
                    lastLocation = loc;

                    Address obtainedAddress = locationHelper.performForwardGeocoding(getApplicationContext(), lastLocation);
                    if (obtainedAddress != null){
                        Log.d(TAG, "onLocationResult: Country code : " + obtainedAddress.getCountryCode());
                        Log.d(TAG, "onLocationResult: Country : " + obtainedAddress.getCountryName());
                        Log.d(TAG, "onLocationResult: City : " + obtainedAddress.getLocality());
                        Log.d(TAG, "onLocationResult: Postal Code : " + obtainedAddress.getPostalCode());
                        Log.d(TAG, "onLocationResult: Province : " + obtainedAddress.getAdminArea());
                    }else{
                        Log.d(TAG, "onLocationResult: Address for Last Location not obtained");
                    }
                }
            }
        };

        this.locationHelper.requestLocationUpdates(this, locationCallback);
    }

    @Override
    public void onFriendsItemClicked(Friend f) {

        Intent detailIntent = new Intent(this, MapsActivity.class);
        detailIntent.putExtra("EXTRA_LAT", this.lastLocation.getLatitude());
        detailIntent.putExtra("EXTRA_LNG", this.lastLocation.getLongitude());

        Log.d(TAG, "onFriendsItemClicked: " + f.getAddress());

        String friendAddress  = f.getAddress();
        LatLng getFriendCoordinates = this.locationHelper.performReverseGeocoding(this, friendAddress);

        detailIntent.putExtra("EXTRA_FRIEND_LAT", getFriendCoordinates.latitude);
        detailIntent.putExtra("EXTRA_FRIEND_LNG", getFriendCoordinates.longitude);


        startActivity(detailIntent);
    }


}

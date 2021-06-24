package com.dv.davinder_friends;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

//import com.dv.davinder_friends.databinding.ActivityMainBinding;
import com.dv.davinder_friends.databinding.ActivityMainBinding;
import com.dv.davinder_friends.models.Friend;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private final String TAG = this.getClass().getCanonicalName();

    private EditText eName;
    private EditText eEmail;
    private EditText eNumber;
    private EditText eAddress;

    ActivityMainBinding binding;

    Friend newFriend;

    private LocationHelper locationHelper;
    private Location lastLocation;
    private LocationCallback locationCallback;

    ArrayList<Friend> list = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addbtn = findViewById(R.id.addFriend);
        addbtn.setOnClickListener(this);

        this.eName = findViewById(R.id.editName);
        this.eEmail = findViewById(R.id.editEmail);
        this.eNumber = findViewById(R.id.editNumber);
        this.eAddress = findViewById(R.id.editAddress);

        //this.binding.btnReverseGeocoding.setOnClickListener(this::onClick);
        //this.binding.btnOpenMap.setOnClickListener(this::onClick);

        this.locationHelper = LocationHelper.getInstance();
        this.locationHelper.checkPermissions(this);

        if (this.locationHelper.locationPermissionGranted) {
            Log.d(TAG, "onCreate: Location Permission Granted");

            this.locationHelper.getLastLocation(this).observe(this, new Observer<Location>() {

                @Override
                public void onChanged(Location location) {
                    if (location != null) {
                        lastLocation = location;
//                        binding.tvLocationAddress.setText(lastLocation.toString());

                        Address obtainedAddress = locationHelper.performForwardGeocoding(getApplicationContext(), lastLocation);
//                        if (obtainedAddress != null) {
//                            binding.tvLocationAddress.setText(obtainedAddress.getAddressLine(0));
//                        } else {
//                            binding.tvLocationAddress.setText("Address for Last Location not obtained");
//                        }
                    }
                }
            });

            this.initiateLocationListener();


        } else {
            Log.d(TAG, "onCreate: Location Permission Denied");
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_friends, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){

            case R.id.menu_list:{
                this.showFriends();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        if(v!=null)
        {
            if (v.getId() == R.id.addFriend) {
                if (checkEmpty()) {
                    this.newFriend = new Friend();

                    this.newFriend.setName(this.eName.getText().toString());
                    this.newFriend.setEmail(this.eEmail.getText().toString());
                    this.newFriend.setPhone(Integer.valueOf(this.eNumber.getText().toString()));
                    this.newFriend.setAddress(this.eAddress.getText().toString());

                    this.list.add(newFriend);

                    Log.d(TAG, "addFriend: " + newFriend);

                    AlertDialog.Builder alert = new AlertDialog.Builder(this);
                    alert.setTitle("Friend");
                    alert.setMessage("You have successfull added your Friend \nDo you want to add another one?");

                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //reset
                            resetHome();
                        }
                    });

                    alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //show list
                            showFriends();
                        }
                    });

                    alert.show();
                    resetHome();
                } else {
                    Log.d(TAG, "Empty");
                    //No input
                }
            }
        }

    }

    private void showFriends() {
        Intent friendListIntent = new Intent(this, FriendsActivity.class);
        friendListIntent.putExtra("EXTRA_FRIEND_OBJ", this.list);
        startActivity(friendListIntent);
    }

    private void resetHome() {
        this.eName.setText("");
        this.eAddress.setText("");
        this.eEmail.setText("");
        this.eNumber.setText("");
    }

    private Boolean checkEmpty(){

        boolean input = true;

        if(this.eName.getText().toString().isEmpty()){
            this.eName.setError("Name can't be empty");
            input = false;
        }
        else if(this.eNumber.getText().toString().isEmpty()){
            this.eNumber.setError("Phone Number can't be empty");
            input = false;
            //Should only be 10 numbers
        }else if(this.eAddress.getText().toString().isEmpty()){
            this.eAddress.setError("Address can't be empty");
            input = false;
        }

        return input;
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
                        Log.d(TAG, "onLocationResult: Country code : " + obtainedAddress.getCountryCode());
                        Log.d(TAG, "onLocationResult: Country : " + obtainedAddress.getCountryName());
                        Log.d(TAG, "onLocationResult: City : " + obtainedAddress.getLocality());
                        Log.d(TAG, "onLocationResult: Postal Code : " + obtainedAddress.getPostalCode());
                        Log.d(TAG, "onLocationResult: Province : " + obtainedAddress.getAdminArea());
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

            }else{
            }
        }else{
        }
    }
}
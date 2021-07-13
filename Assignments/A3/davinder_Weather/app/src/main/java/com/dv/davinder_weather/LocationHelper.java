package com.dv.davinder_weather;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;
import java.util.Locale;

public class LocationHelper {
    private final String TAG = this.getClass().getCanonicalName();

    public boolean locationPermissionGranted = false;

    public final int REQUEST_CODE_LOCATION = 101;
    private LocationRequest locationRequest;
    private FusedLocationProviderClient fusedLocationProviderClient = null;

    MutableLiveData<Location> mLocation = new MutableLiveData<>();

    private static final LocationHelper singletonInstance = new LocationHelper();

    public static LocationHelper getInstance(){
        return singletonInstance;
    }

    private LocationHelper(){
        this.locationRequest = new LocationRequest();
        this.locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        this.locationRequest.setInterval(1000); // 1 second
    }

    public void checkPermissions(Context context){
        this.locationPermissionGranted = (PackageManager.PERMISSION_GRANTED ==
                (ContextCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)));

        Log.d(TAG, "checkPermissions: locationPermissionGranted : " + this.locationPermissionGranted);

        if (!this.locationPermissionGranted){
            requestLocationPermission(context);
        }
    }

    public void requestLocationPermission(Context context){
        ActivityCompat.requestPermissions((Activity) context,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, this.REQUEST_CODE_LOCATION );
    }

    public  FusedLocationProviderClient getFusedLocationProviderClient(Context context){
        if (this.fusedLocationProviderClient == null){
            this.fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        }

        return this.fusedLocationProviderClient;
    }

    @SuppressLint("MissingPermission")
    public MutableLiveData<Location> getLastLocation(Context context){
        Log.d(TAG, "getLastLocation: location helper initiated");
        if (this.locationPermissionGranted){
            try{
                this.getFusedLocationProviderClient(context)
                        .getLastLocation()
                        .addOnSuccessListener(new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                if (location != null){

                                    mLocation.setValue(location);
                                    Log.d(TAG, "onSuccess: Last Location -- Latitude : " + mLocation.getValue().getLatitude() +
                                            " Longitude : " + mLocation.getValue().getLongitude());
                                }

                                Log.d(TAG, "onSuccess: onSuccess completed");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e(TAG, "onFailure: Exception while accessing last location " + e.getLocalizedMessage() );

                                Log.d(TAG, "onFailure: completed");
                            }
                        });
            }catch(Exception ex){
                Log.e(TAG, "getLastLocation: Exception while accessing last location " + ex.getLocalizedMessage() );
                return null;
            }

            return this.mLocation;
        }else {
            Log.e(TAG, "getLastLocation: Certain features won't be available as the app doesn't have access to location");
            return null;
        }
    }

    @SuppressLint("MissingPermission")
    public void requestLocationUpdates(Context context, LocationCallback locationCallback){
        if (this.locationPermissionGranted){
            try{
                this.getFusedLocationProviderClient(context)
                        .requestLocationUpdates(this.locationRequest, locationCallback, Looper.getMainLooper());
            }catch(Exception ex){
                Log.e(TAG, "requestLocationUpdates: Exception while getting location updates " + ex.getLocalizedMessage() );
            }
        }
    }

    public void stopLocationUpdates(Context context, LocationCallback locationCallback){
        try{
            this.getFusedLocationProviderClient(context).removeLocationUpdates(locationCallback);
        }catch(Exception ex){
            Log.e(TAG, "stopLocationUpdates: Exception while stopping location updates " + ex.getLocalizedMessage() );
        }
    }

    //convert location coordinates(lat & lng) to postal address
    public Address performForwardGeocoding(Context context, Location loc){
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addressList;

        try{

            addressList = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);

            if (addressList.size() > 0){
                Address addressObj = addressList.get(0);
                Log.d(TAG, "performForwardGeocoding: Address obtained from forward geocoding " + addressObj.getAddressLine(0));
                return addressObj;
            }

        }catch(Exception ex){
            Log.e(TAG, "performForwardGeocoding: Couldn't get address for the given location" + ex.getLocalizedMessage() );
        }

        return null;
    }

    //convert postal address to location coordinates (lat & lng)
    public LatLng performReverseGeocoding(Context context, String address){
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());

        try{
            List<Address> locationList = geocoder.getFromLocationName(address, 1);
            LatLng obtainedLocation = new LatLng(locationList.get(0).getLatitude(), locationList.get(0).getLongitude());
            Log.d(TAG, "performReverseGeocoding: ObtainedLocation : " + obtainedLocation.toString());
            return obtainedLocation;

        }catch (Exception ex){

            Log.e(TAG, "performReverseGeocoding: Couldn't get the coordinates for given address " + ex.getLocalizedMessage() );
        }

        return null;
    }

}

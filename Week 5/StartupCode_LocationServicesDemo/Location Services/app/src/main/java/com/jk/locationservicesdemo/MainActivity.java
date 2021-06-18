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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        this.binding.btnReverseGeocoding.setOnClickListener(this::onClick);
        this.binding.btnOpenMap.setOnClickListener(this::onClick);
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

    private void openMap(){
        Log.d(TAG, "onClick: Open map to show location");
    }

    private void doReverseGeocoding(){
        Log.d(TAG, "onClick: Perform reverse geocoding to get coordinates from address.");
    }

    private void doGeocoding(){

    }
}
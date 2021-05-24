package com.example.helloandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart(){
        super.onStart();

        Log.d(TAG, "onStart: Called");
    }

    @Override
    protected void onResume(){
        super.onResume();

        Log.d(TAG, "onResume: Called");
    }

    @Override
    protected void onPause(){
        super.onPause();

        Log.d(TAG, "onPause: Called");
    }

    @Override
    protected void onStop(){
        super.onStop();

        Log.d(TAG, "onStop: Called");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

        Log.d(TAG, "onDestroy: Called");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);

        if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Log.d(TAG, "onConfigurationChanged: Portrait Mode");
        }else{
            Log.d(TAG, "onConfigurationChanged: Landscape Mode");
        }
    }
}
package com.jk.sharedprefdemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.jk.sharedprefdemo.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getCanonicalName();
    private ActivityMainBinding binding;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.prefs = getApplicationContext().getSharedPreferences(getPackageName(), MODE_PRIVATE);
//        this.binding.tvGreet.setText("Hello " + this.prefs.getString("USER_EMAIL", "Not Found"));


    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String signature = sharedPreferences.getString("signature", "Not Found");
        this.binding.tvGreet.setText(signature);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_show_settings:{
                this.showSettings();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void showSettings(){
        Intent settingsIntent = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(settingsIntent);
    }
}
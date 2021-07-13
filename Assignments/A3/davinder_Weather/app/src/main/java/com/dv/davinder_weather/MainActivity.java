package com.dv.davinder_weather;

import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.dv.davinder_weather.models.LocationContainer;
import com.dv.davinder_weather.models.WeatherContainer;
import com.dv.davinder_weather.network.API;
import com.dv.davinder_weather.network.RetrofitClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getCanonicalName();

    TextView cityName, country, condition, temp, feelsLike, wind, windDirection, humidity, uv, visibility;

    private LocationHelper locationHelper;
    private Location lastLocation;
    private LocationCallback locationCallback;
    private double latitude, longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        temp = findViewById(R.id.temp);
        condition = findViewById(R.id.condition);
        cityName = findViewById(R.id.cityName);
        country = findViewById(R.id.country);
        feelsLike = findViewById(R.id.feelsLike);
        wind = findViewById(R.id.wind);
        windDirection = findViewById(R.id.windDirection);
        humidity = findViewById(R.id.humidity);
        uv = findViewById(R.id.uv);
        visibility = findViewById(R.id.visibility);

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
               for (Location loc : locationResult.getLocations())
                {
                    lastLocation = loc;

                    Address obtainedAddress = locationHelper.performForwardGeocoding(getApplicationContext(), lastLocation);
                    if (obtainedAddress != null){
                        Log.d(TAG, "onLocationResult: Country code : " + obtainedAddress.getCountryCode());
                        Log.d(TAG, "onLocationResult: Country : " + obtainedAddress.getCountryName());
                        Log.d(TAG, "onLocationResult: City : " + obtainedAddress.getLocality());
                        Log.d(TAG, "onLocationResult: Postal Code : " + obtainedAddress.getPostalCode());
                        Log.d(TAG, "onLocationResult: Province : " + obtainedAddress.getAdminArea());
                        latitude = obtainedAddress.getLatitude();
                        longitude = obtainedAddress.getLongitude();
                        Log.d(TAG, "onLocationResult: lat: " + latitude + " long: " + longitude);
                        String latlong = latitude + "," + longitude;
                        getWeather(latlong);
                    }else{
                        Log.d(TAG, "onLocationResult: Address for Last Location not obtained");
                    }
                }
            }
        };

        this.locationHelper.requestLocationUpdates(this, locationCallback);
    }

    public void getWeather(String name) {

        Log.d(TAG, "getWeather: button clicked, city: " + name);

        API apiInterface = RetrofitClient.getRetrofit().create(API.class);

        Call<LocationContainer> locationContainerCall = apiInterface.getLocData(name);
        locationContainerCall.enqueue(new Callback<LocationContainer>() {
            @Override
            public void onResponse(Call<LocationContainer> call, Response<LocationContainer> response) {
                Log.d(TAG, "onResponse: Entered location enqueue");
                try {
                    cityName.setText(response.body().getLocation().getCity());
                    country.setText(response.body().getLocation().getCountry());

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<LocationContainer> call, Throwable t) {
                t.printStackTrace();
                Log.d(TAG, "onResponse: failed for location enqueue");
            }
        });


        Call<WeatherContainer> call = apiInterface.getData(name);

        call.enqueue(new Callback<WeatherContainer>() {

            @Override
            public void onResponse(Call<WeatherContainer> call, Response<WeatherContainer> response) {
                Log.d(TAG, "onResponse: Entered on response");

                try {
                    temp.setText(response.body().getWeather().getTemp() + " °C");
                    condition.setText(response.body().getWeather().getCondition().getText());
                    feelsLike.setText(response.body().getWeather().getFeelsLike() + " °C");
                    wind.setText(response.body().getWeather().getWind() + " kph");
                    windDirection.setText(response.body().getWeather().getWindDirection());
                    humidity.setText(response.body().getWeather().getHumidity() + " %");
                    uv.setText(response.body().getWeather().getUv());
                    visibility.setText(response.body().getWeather().getVisibility() + " km");

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<WeatherContainer> call, Throwable t) {
                Log.d(TAG, "onFailure: failed " + t);
            }
        });

    }


}
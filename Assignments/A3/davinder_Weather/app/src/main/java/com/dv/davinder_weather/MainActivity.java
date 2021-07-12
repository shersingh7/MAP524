package com.dv.davinder_weather;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.dv.davinder_weather.models.WeatherContainer;
import com.dv.davinder_weather.models.LocationContainer;
import com.dv.davinder_weather.network.API;
import com.dv.davinder_weather.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getCanonicalName();

    TextView cityName, country, condition, temp, feelsLike, wind, windDirection, humidity, uv, visibility;
    EditText city;
    Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search = findViewById(R.id.button);
        city = findViewById(R.id.city);
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


        search.setOnClickListener(v -> getWeather(city.getText().toString().trim()));
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
                    temp.setText("Temperature" + " " + response.body().getWeather().getTemp() + " °C");
                    condition.setText(response.body().getWeather().getCondition().getText());
                    feelsLike.setText("Feels Like" + " " + response.body().getWeather().getFeelsLike() + " °C");
                    wind.setText("Wind" + " " + response.body().getWeather().getWind() + " kph");
                    windDirection.setText("Wind Direction" + " " + response.body().getWeather().getWindDirection());
                    humidity.setText("Humidity" + " " + response.body().getWeather().getHumidity() + " %");
                    uv.setText("UV" + " " + response.body().getWeather().getUv());
                    visibility.setText("Visibility" + " " + response.body().getWeather().getVisibility() + " km");

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
package com.weather.tonis.weatherapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.ScrollerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ViewConfiguration;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.weather.tonis.weatherapp.RecycleAdapters.CitiesListAdapter;
import com.weather.tonis.weatherapp.RecycleAdapters.ForecastListAdapter;
import com.weather.tonis.weatherapp.listObjects.CityData;
import com.weather.tonis.weatherapp.listObjects.PerDayWeatherInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.FOCUS_AFTER_DESCENDANTS;
import static com.weather.tonis.weatherapp.R.string.degree_symbol;

public class DetailedInfoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<PerDayWeatherInfo> forecastList = new ArrayList<>();
    private RecyclerView.Adapter adapter;
    private LinearLayoutManager linearLayoutManager;
    String city_id;
    String city_name;
    String city_temp;
    String city_weather_desc;
    String city_temp_max;
    String city_temp_min;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_info_acitivty);
        Intent i = getIntent();
        getPayload(i);
        setUpToolbar();
        setTemps();
        loadProducts();
        setUpRecycleAdapter();
    }

    @SuppressLint("SetTextI18n")
    private void setTemps() {
        TextView currentTemp = findViewById(R.id.current_temp);
        TextView minTemp = findViewById(R.id.low_temp);
        TextView maxTemp = findViewById(R.id.high_temp);
        TextView weatherDesc = findViewById(R.id.weather_desc);
        currentTemp.setText(city_temp + getString(R.string.degree_symbol));
        minTemp.setText(city_temp_min + getString(R.string.degree_symbol));
        maxTemp.setText(city_temp_max + getString(R.string.degree_symbol));
        weatherDesc.setText(city_weather_desc);
    }

    private void setUpRecycleAdapter() {
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.daily_forecast);
        /*recyclerView.setHasFixedSize(true);*/
        recyclerView.setLayoutManager(linearLayoutManager);
/*        recyclerView.setNestedScrollingEnabled(false);*/
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(city_name);
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void getPayload(Intent i) {
        this.city_id = (String) i.getSerializableExtra("CITY_ID");
        this.city_name = (String) i.getSerializableExtra("CITY_NAME");
        this.city_temp = (String) i.getSerializableExtra("CURRENT_TEMP");
        this.city_weather_desc = (String) i.getSerializableExtra("DESCRIPTION");
        this.city_temp_max = (String) i.getSerializableExtra("MAX_TEMP");
        this.city_temp_min = (String) i.getSerializableExtra("MIN_TEMP");


    }

    private void loadProducts() {
        String dataURL = "http://api.openweathermap.org/data/2.5/forecast?id=" + city_id + "&units=metric&APPID=c25e9063552783d77445456d863d5ea7";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, dataURL,
                response -> {
                    try {
                        if (forecastList.isEmpty()) {
                            JSONObject dates = new JSONObject(response);
                            JSONArray teams = dates.getJSONArray("list");
                            for (int i = 0; i < teams.length(); i++) {
                                JSONObject cityObject = teams.getJSONObject(i);
                                JSONObject weather = cityObject.getJSONArray("weather").getJSONObject(0);
                                JSONObject tempData = cityObject.getJSONObject("main");
                                long dateTime = cityObject.getLong("dt");
                                double maxTemp = tempData.getDouble("temp_max");
                                double minTemp = tempData.getDouble("temp_min");
                                String weatherDesc = weather.getString("description");
                                String weatherIcon = weather.getString("icon");
                                double windSpeed = cityObject.getJSONObject("wind").getDouble("speed");

                                PerDayWeatherInfo perDayWeatherInfo = new PerDayWeatherInfo(dateTime, maxTemp, minTemp, weatherDesc, weatherIcon, windSpeed);
                                forecastList.add(perDayWeatherInfo);
                            }
                        }
                        adapter = new ForecastListAdapter(forecastList, DetailedInfoActivity.this);
                        recyclerView.setAdapter(adapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
            if (error instanceof NoConnectionError){
                Log.e("Volley", error.toString());
            }
            Toast.makeText(DetailedInfoActivity.this, error.getMessage() + "PLEASE CONTACT SUPPORT", Toast.LENGTH_LONG).show();
        });
        Volley.newRequestQueue(this).add(stringRequest);
    }
}

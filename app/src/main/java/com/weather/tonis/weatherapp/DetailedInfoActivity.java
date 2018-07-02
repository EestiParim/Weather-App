package com.weather.tonis.weatherapp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

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

public class DetailedInfoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<PerDayWeatherInfo> forecastList = new ArrayList<>();
    private RecyclerView.Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_info_acitivty);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Tallinn");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> finish());
        loadProducts();
        recyclerView = findViewById(R.id.daily_forecast);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadProducts() {
        String dataURL = "http://www.mocky.io/v2/5b3a49a52e0000da1615819b";
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
                }, error -> Toast.makeText(DetailedInfoActivity.this, error.getMessage() + "PLEASE CONTACT SUPPORT", Toast.LENGTH_LONG).show());
        Volley.newRequestQueue(this).add(stringRequest);
    }
}

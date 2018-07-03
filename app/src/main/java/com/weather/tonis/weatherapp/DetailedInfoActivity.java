package com.weather.tonis.weatherapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.weather.tonis.weatherapp.RecycleAdapters.ForecastListAdapter;
import com.weather.tonis.weatherapp.listObjects.PerDayWeatherInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

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
                (String response) -> {
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
        }){
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    Cache.Entry cacheEntry = HttpHeaderParser.parseCacheHeaders(response);
                    if (cacheEntry == null) {
                        cacheEntry = new Cache.Entry();
                    }
                    final long cacheHitButRefreshed = 3 * 60 * 1000; // in 3 minutes cache will be hit, but also refreshed on background
                    final long cacheExpired = 24 * 60 * 60 * 1000; // in 24 hours this cache entry expires completely
                    long now = System.currentTimeMillis();
                    final long softExpire = now + cacheHitButRefreshed;
                    final long ttl = now + cacheExpired;
                    cacheEntry.data = response.data;
                    cacheEntry.softTtl = softExpire;
                    cacheEntry.ttl = ttl;
                    String headerValue;
                    headerValue = response.headers.get("Date");
                    if (headerValue != null) {
                        cacheEntry.serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
                    }
                    headerValue = response.headers.get("Last-Modified");
                    if (headerValue != null) {
                        cacheEntry.lastModified = HttpHeaderParser.parseDateAsEpoch(headerValue);
                    }
                    cacheEntry.responseHeaders = response.headers;
                    final String jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers));
                    return Response.success(jsonString, cacheEntry);
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                }
            }

            @Override
            protected void deliverResponse(String response) {
                super.deliverResponse(response);
            }

            @Override
            public void deliverError(VolleyError error) {
                super.deliverError(error);
            }

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                return super.parseNetworkError(volleyError);
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}

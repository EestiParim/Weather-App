package com.weather.tonis.weatherapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
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
import com.weather.tonis.weatherapp.network.JsonParser;

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
    private Handler handler = new Handler();
    JsonParser jsonParser = new JsonParser();
    String city_id;
    String city_name;
    String city_temp;
    String city_weather_desc;
    String city_temp_max;
    String city_temp_min;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_info_acitivty);
        Intent i = getIntent();
        getPayload(i);
        forecastList = jsonParser.loadProducts(forecastList, context, city_id);
        setUpToolbar();
        setTemps();
        handler.postDelayed(this::setUpRecycleAdapter, 100);
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
        adapter = new ForecastListAdapter(forecastList, DetailedInfoActivity.this);
        recyclerView.setAdapter(adapter);
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
}

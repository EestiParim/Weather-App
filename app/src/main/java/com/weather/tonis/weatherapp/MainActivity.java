package com.weather.tonis.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.weather.tonis.weatherapp.RecycleAdapters.CitiesListAdapter;
import com.weather.tonis.weatherapp.listObjects.CityData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private List<CityData> cityList = new ArrayList<>();
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadProducts();
        recyclerView = findViewById(R.id.cityList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
    private void loadProducts() {
        String dataURL = "http://api.openweathermap.org/data/2.5/box/city?bbox=22,58,28,60,10&APPID=c25e9063552783d77445456d863d5ea7";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, dataURL,
                (String response) -> {
                    try {
                        if (cityList.isEmpty()) {
                            JSONObject cities = new JSONObject(response);
                            JSONArray teams = cities.getJSONArray("list");
                            //Log.d("responsestring", response);
                            for (int i = 0; i < teams.length(); i++) {
                                JSONObject cityObject = teams.getJSONObject(i);
                                int id = (cityObject.getInt("id"));
                                int dt = cityObject.getInt("dt");
                                String name = cityObject.getString("name");
                                JSONObject coord = cityObject.getJSONObject("coord");
                                JSONObject tempData = cityObject.getJSONObject("main");
                                JSONObject windData = cityObject.getJSONObject("wind");
                                JSONObject clouds = cityObject.getJSONObject("clouds");
                                JSONArray weather = cityObject.getJSONArray("weather");
                                CityData cityListItem = new CityData(id, dt, name, coord, tempData, windData, clouds, weather);
                                cityList.add(cityListItem);
                            }
                        }
                        adapter = new CitiesListAdapter(cityList, MainActivity.this);
                        recyclerView.setAdapter(adapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> Log.e("Volley", error.toString())) {
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
//Volley.newRequestQueue(this).add(stringRequest);
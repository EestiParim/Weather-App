package com.weather.tonis.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.weather.tonis.weatherapp.RecycleAdapters.CitiesListAdapter;
import com.weather.tonis.weatherapp.listObjects.CityData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
/*        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).hide();*/
        loadProducts();
        recyclerView = findViewById(R.id.cityList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
    private void loadProducts() {
        String dataURL = "http://www.mocky.io/v2/5b2a21623000004e009cd1b9";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, dataURL,
                response -> {
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
                }, error -> Toast.makeText(MainActivity.this, error.getMessage() + "PLEASE CONTACT SUPPORT", Toast.LENGTH_LONG).show());
        Volley.newRequestQueue(this).add(stringRequest);
    }
}

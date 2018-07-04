package com.weather.tonis.weatherapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.weather.tonis.weatherapp.RecycleAdapters.CitiesListAdapter;
import com.weather.tonis.weatherapp.listObjects.CityData;
import com.weather.tonis.weatherapp.network.JsonParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private final JsonParser jsonParser = new JsonParser();
    private List<CityData> cityList = new ArrayList<>();
    private RecyclerView recyclerView;
    private String[] coordinates;
    private final Context context = this;
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getGpsLocation();
        cityList = jsonParser.getWeatherList(cityList, context, coordinates);
        handler.postDelayed(this::createRecycleAdapter, 100);
        //getWeatherList();


    }

    private void createRecycleAdapter() {
        recyclerView = findViewById(R.id.cityList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.Adapter adapter = new CitiesListAdapter(cityList, MainActivity.this);
        recyclerView.setAdapter(adapter);
    }

    private void getGpsLocation() {
        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

// Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                makeUseOfNewLocation(location);
            }

            private void makeUseOfNewLocation(Location location) {
                coordinates = new String[]{Location.convert(location.getLatitude(), Location.FORMAT_DEGREES), (Location.convert(location.getLongitude(), Location.FORMAT_DEGREES))};
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

// Register the listener with the Location Manager to receive location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        String locationProvider = LocationManager.NETWORK_PROVIDER;
        Objects.requireNonNull(locationManager).requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
        convertToGps(lastKnownLocation);
    }

    private void convertToGps(Location location) {
        coordinates = new String[]{(Location.convert(location.getLatitude(), Location.FORMAT_DEGREES)), (Location.convert(location.getLongitude(), Location.FORMAT_DEGREES))};
    }
}
//Volley.newRequestQueue(this).add(stringRequest);
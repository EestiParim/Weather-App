package com.weather.tonis.weatherapp.network;

import android.content.Context;
import android.util.Log;
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
import com.weather.tonis.weatherapp.DetailedInfoActivity;
import com.weather.tonis.weatherapp.RecycleAdapters.ForecastListAdapter;
import com.weather.tonis.weatherapp.listObjects.CityData;
import com.weather.tonis.weatherapp.listObjects.PerDayWeatherInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class JsonParser {


    public List<CityData> getWeatherList(List<CityData> cityList, Context context, String[] coordinates) {
        String dataURL = "http://api.openweathermap.org/data/2.5/find?lat=" + coordinates[0] + "&lon=" + coordinates[1] + "&units=metric&cnt=10&APPID=c25e9063552783d77445456d863d5ea7";
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
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
        return cityList;
    }

    public List<PerDayWeatherInfo> loadProducts(List<PerDayWeatherInfo> forecastList, Context context, String city_id) {
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
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
            if (error instanceof NoConnectionError){
                Log.e("Volley", error.toString());
            }
            Toast.makeText(context, error.getMessage() + "PLEASE CONTACT SUPPORT", Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
        return forecastList;
    }
}

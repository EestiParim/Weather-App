package com.weather.tonis.weatherapp.listObjects;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DecimalFormat;

import pl.allegro.finance.tradukisto.ValueConverters;

public class CityData implements Serializable {
    private final String cityName;
    private final JSONObject coord;
    private final JSONObject tempData;
    private final JSONObject windData;
    private final JSONObject clouds;
    private final JSONArray weather;
    private final int id;
    private final int dt;
    private double cityTemp;
    DecimalFormat tempDisplayFormat = new DecimalFormat("#.#");
    private String skyDesc;
    private String weatherDescription;
    private String weatherIcon;

    public CityData(int id, int dt, String cityName, JSONObject coord, JSONObject tempData, JSONObject windData, JSONObject clouds, JSONArray weather) throws JSONException {
        this.id = id;
        this.dt = dt;
        this.cityName = cityName;
        this.coord = coord;
        this.tempData = tempData;
        this.windData = windData;
        this.clouds = clouds;
        this.weather = weather;
        getWeatherDescription(weather);
        this.cityTemp = tempData.getDouble("temp");
    }

    public String getCityName() {
        return cityName;
    }

    public JSONObject getCoord() {
        return coord;
    }

    public JSONObject getTempData() {
        return tempData;
    }

    public JSONObject getWindData() {
        return windData;
    }

    public JSONObject getClouds() {
        return clouds;
    }

    public JSONArray getWeather() {
        return weather;
    }

    public String getId() {
        return Integer.toString(id);
    }

    public int getDt() {
        return dt;
    }

    public String getCityTempAsStringWord() {
        ValueConverters converter = ValueConverters.ENGLISH_INTEGER;
        return StringUtils.capitalize(converter.asWords((int) cityTemp));
    }
    public String getMaxTempAsString() throws JSONException {
        return String.valueOf((int) tempData.getDouble("temp_max"));
    }
    public String getMinTempAsString() throws JSONException {
        return String.valueOf((int) tempData.getDouble("temp_min"));
    }
    public String getCityTempAsString(){
        return String.valueOf((int) cityTemp);
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    private void getWeatherDescription(JSONArray weather) throws JSONException {
        JSONObject jsonObject = weather.getJSONObject(0);
        this.weatherDescription = jsonObject.getString("description");
        this.weatherIcon = jsonObject.getString("icon");
    }
}

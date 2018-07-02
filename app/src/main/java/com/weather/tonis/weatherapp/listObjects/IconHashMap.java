package com.weather.tonis.weatherapp.listObjects;

import java.util.HashMap;

public class IconHashMap {
    HashMap<String, Integer> weatherIconMap = new HashMap<>();

    public IconHashMap(){
        weatherIconMap.put("01d", 0);
        weatherIconMap.put("01n", 0);
        weatherIconMap.put("02d", 1);
        weatherIconMap.put("02n", 1);
        weatherIconMap.put("03d", 2);
        weatherIconMap.put("03n", 2);
        weatherIconMap.put("04d", 2);
        weatherIconMap.put("04n", 2);
        weatherIconMap.put("09d", 5);
        weatherIconMap.put("09n", 5);
        weatherIconMap.put("10d", 4);
        weatherIconMap.put("10n", 4);
        weatherIconMap.put("11d", 7);
        weatherIconMap.put("11n", 7);
        weatherIconMap.put("13d", 6);
        weatherIconMap.put("13n", 6);
        weatherIconMap.put("50d", 3);
        weatherIconMap.put("50n", 3);
    }

    public HashMap<String, Integer> getWeatherIconMap() {
        return weatherIconMap;
    }

}

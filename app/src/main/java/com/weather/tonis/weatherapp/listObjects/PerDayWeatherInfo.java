package com.weather.tonis.weatherapp.listObjects;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import pl.allegro.finance.tradukisto.ValueConverters;

public class PerDayWeatherInfo {


    private final long dateTime;
    private final double maxTemp;
    private final double minTemp;
    private final String weatherDesc;
    private final String weatherIcon;
    private final double windSpeed;

    public PerDayWeatherInfo(long dateTime, double maxTemp, double minTemp, String weatherDesc, String weatherIcon, double windSpeed) {
        this.dateTime = dateTime;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.weatherDesc = weatherDesc;
        this.weatherIcon = weatherIcon;
        this.windSpeed = windSpeed;

    }

    public String getDateTime() throws ParseException {
        String[] dayList;
        dayList = new String[]{"Monday", "Tuesday", "Wendsday", "Thursday", "Friday", "Saturday", "Sunday"};
        Date date = new Date(dateTime * 1000);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return dayList[((calendar.get(Calendar.DAY_OF_WEEK)) - 2)] + " " + calendar.get(Calendar.HOUR_OF_DAY);
    }

    public String getMaxTempAsString() {
       return String.valueOf((int) maxTemp);
    }

    public String getMinTempAsString() {
        return String.valueOf((int) minTemp);
    }

    public String getWeatherDesc() {
        return weatherDesc;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public String getWindSpeedAsString() {
        return String.valueOf((int) windSpeed);
    }
}

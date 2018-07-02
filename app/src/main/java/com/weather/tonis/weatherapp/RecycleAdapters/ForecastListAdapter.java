package com.weather.tonis.weatherapp.RecycleAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.weather.tonis.weatherapp.R;
import com.weather.tonis.weatherapp.listObjects.PerDayWeatherInfo;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

public class ForecastListAdapter extends RecyclerView.Adapter<ForecastListAdapter.ViewHolder> {
    private final List<PerDayWeatherInfo> perDayWeatherInfoList;
    private final Context context;
    HashMap<String, Integer> weatherIconMap = new HashMap<String, Integer>();

    public ForecastListAdapter(List<PerDayWeatherInfo> forecastList, Context context) {
        this.perDayWeatherInfoList = forecastList;
        this.context = context;
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

    @NonNull
    @Override
    public ForecastListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.forecast_item,parent,false);
        return new ForecastListAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ForecastListAdapter.ViewHolder holder, int position) {
        final PerDayWeatherInfo perDayWeatherInfo = perDayWeatherInfoList.get(position);
        String weatherIcon = perDayWeatherInfo.getWeatherIcon();
        holder.weatherDescTxt.setText(perDayWeatherInfo.getWeatherDesc());
        try {
            holder.date.setText((perDayWeatherInfo.getDateTime()) + ":00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.highTemp.setText(perDayWeatherInfo.getMaxTempAsString() + " °");
        holder.lowTemp.setText(perDayWeatherInfo.getMinTempAsString() + " °");
        holder.windSpeed.setText(perDayWeatherInfo.getWindSpeedAsString() + " m/s");
        if (weatherIconMap.containsKey(weatherIcon)){
            holder.weatherDescIcon.setImageLevel(weatherIconMap.get(weatherIcon));
        }
    }

    @Override
    public int getItemCount() {
        return perDayWeatherInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView date;
        final TextView weatherDescTxt;
        final TextView highTemp;
        final TextView lowTemp;
        final TextView windSpeed;
        final ImageView weatherDescIcon;

        ViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            weatherDescTxt = itemView.findViewById(R.id.cityClouds);
            highTemp = itemView.findViewById(R.id.high_temp);
            lowTemp = itemView.findViewById(R.id.low_temp);
            windSpeed = itemView.findViewById(R.id.wind_speed);
            weatherDescIcon = itemView.findViewById(R.id.weatherDescIcon);

        }
    }
}

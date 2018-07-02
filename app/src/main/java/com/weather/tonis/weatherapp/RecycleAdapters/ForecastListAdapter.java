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
import com.weather.tonis.weatherapp.listObjects.IconHashMap;
import com.weather.tonis.weatherapp.listObjects.PerDayWeatherInfo;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

public class ForecastListAdapter extends RecyclerView.Adapter<ForecastListAdapter.ViewHolder> {
    private final List<PerDayWeatherInfo> perDayWeatherInfoList;
    private final Context context;
    HashMap<String, Integer> weatherIconMap;

    public ForecastListAdapter(List<PerDayWeatherInfo> forecastList, Context context) {
        this.perDayWeatherInfoList = forecastList;
        this.context = context;
        IconHashMap iconHashMap = new IconHashMap();
        weatherIconMap = iconHashMap.getWeatherIconMap();
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
        holder.date.setText((perDayWeatherInfo.getDateTime()) + ":00");
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

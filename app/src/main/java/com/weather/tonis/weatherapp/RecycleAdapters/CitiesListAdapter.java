package com.weather.tonis.weatherapp.RecycleAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.weather.tonis.weatherapp.DetailedInfoActivity;
import com.weather.tonis.weatherapp.R;
import com.weather.tonis.weatherapp.listObjects.CityData;

import java.util.HashMap;
import java.util.List;

public class CitiesListAdapter extends RecyclerView.Adapter<CitiesListAdapter.ViewHolder> {
    private final List<CityData> cityList;
    private final Context context;
    HashMap<String, Integer> weatherIconMap = new HashMap<String, Integer>();

    public CitiesListAdapter(List<CityData> cityList, Context context) {
        this.cityList = cityList;
        this.context = context;
        weatherIconMap.put("02d", 1);
        weatherIconMap.put("04d", 2);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.city_item,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final CityData cityData = cityList.get(position);
        String weatherIcon = cityData.getWeatherIcon();
        holder.cityName.setText(cityData.getCityName());
        holder.cityTemp.setText(cityData.getCityTempAsString() + " " + context.getString(R.string.degrees));
        holder.cityClouds.setText(cityData.getWeatherDescription());
        if (weatherIconMap.containsKey(weatherIcon)){
            holder.weatherIcon.setImageLevel(weatherIconMap.get(cityData.getWeatherIcon()));
        }
        holder.cardView.setOnClickListener(v -> {
            Intent mIntent = new Intent(context, DetailedInfoActivity.class);
            context.startActivity(mIntent);
        });
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView cityName;
        final TextView cityTemp;
        final TextView cityClouds;
        final ImageView weatherIcon;
        final CardView cardView;

        ViewHolder(View itemView) {
            super(itemView);
            cityName = itemView.findViewById(R.id.date);
            cityTemp = itemView.findViewById(R.id.cityTemp);
            cityClouds = itemView.findViewById(R.id.cityClouds);
            weatherIcon = itemView.findViewById(R.id.weatherDescIcon);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}

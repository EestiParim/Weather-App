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
import com.weather.tonis.weatherapp.listObjects.IconHashMap;

import org.json.JSONException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class CitiesListAdapter extends RecyclerView.Adapter<CitiesListAdapter.ViewHolder> {
    private final List<CityData> cityList;
    private final Context context;
    private HashMap<String, Integer> weatherIconMap;

    public CitiesListAdapter(List<CityData> cityList, Context context) {
        this.cityList = cityList;
        this.context = context;
        IconHashMap iconHashMap = new IconHashMap();
        weatherIconMap = iconHashMap.getWeatherIconMap();
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
        holder.cityTemp.setText(cityData.getCityTempAsStringWord() + " " + context.getString(R.string.degrees));
        holder.cityClouds.setText(cityData.getWeatherDescription());
        if (weatherIconMap.containsKey(weatherIcon)){
            holder.weatherIcon.setImageLevel(weatherIconMap.get(cityData.getWeatherIcon()));
        }
        holder.cardView.setOnClickListener(v -> {
            Intent mIntent = new Intent(context, DetailedInfoActivity.class);
            try {
                putExtras(mIntent, cityData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            context.startActivity(mIntent);
        });
    }

    private void putExtras(Intent mIntent, CityData cityData) throws JSONException {
        mIntent.putExtra("CITY_NAME", cityData.getCityName());
        mIntent.putExtra("CURRENT_TEMP", cityData.getCityTempAsString());
        mIntent.putExtra("MAX_TEMP", cityData.getMaxTempAsString());
        mIntent.putExtra("MIN_TEMP", cityData.getMinTempAsString());
        mIntent.putExtra("DESCRIPTION", cityData.getWeatherDescription());
        mIntent.putExtra("CITY_ID", cityData.getId());

    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
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

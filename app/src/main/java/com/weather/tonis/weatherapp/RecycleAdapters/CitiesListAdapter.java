package com.weather.tonis.weatherapp.RecycleAdapters;

import android.app.LauncherActivity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weather.tonis.weatherapp.MainActivity;
import com.weather.tonis.weatherapp.R;
import com.weather.tonis.weatherapp.listItems.CityData;

import java.util.List;

public class CitiesListAdapter extends RecyclerView.Adapter<CitiesListAdapter.ViewHolder> {
    private final List<CityData> cityList;
    private final Context context;

    public CitiesListAdapter(List<CityData> cityList, Context context) {
        this.cityList = cityList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.city_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final CityData cityData = cityList.get(position);
        holder.cityName.setText(cityData.getCityName());
        holder.cityTemp.setText(cityData.getCityTemp() + " " + context.getString(R.string.degrees));
        holder.cityClouds.setText(cityData.getWeatherDescription());
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView cityName;
        final TextView cityTemp;
        final TextView cityClouds;

        ViewHolder(View itemView) {
            super(itemView);
            cityName = itemView.findViewById(R.id.cityName);
            cityTemp = itemView.findViewById(R.id.cityTemp);
            cityClouds = itemView.findViewById(R.id.cityClouds);
        }
    }
}

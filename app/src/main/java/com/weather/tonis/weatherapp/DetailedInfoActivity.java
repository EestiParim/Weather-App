package com.weather.tonis.weatherapp;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Objects;

public class DetailedInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_info_acitivty);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Tallinn");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_left_white_24dp);
        /*        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
                getSupportActionBar().setDisplayShowCustomEnabled(true);
                getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
                View view =getSupportActionBar().getCustomView();

                view.setBackgroundColor(Color.parseColor("#0f000000"));
                view.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0f000000")));
                ImageButton imageButton= view.findViewById(R.id.action_bar_back);

                ColorDrawable newColor = new ColorDrawable(getResources().getColor(R.color.transparent));//your color from res
                newColor.setAlpha(128);//from 0(0%) to 256(100%)
                view.setBackgroundDrawable(newColor);

                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0f000000")));
                getSupportActionBar().setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor("#0f000000")));

                imageButton.setOnClickListener(v -> finish());*/
        toolbar.setNavigationOnClickListener(v -> finish());
    }
}

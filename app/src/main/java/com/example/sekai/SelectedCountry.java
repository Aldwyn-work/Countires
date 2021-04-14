package com.example.sekai;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class SelectedCountry extends AppCompatActivity {
    private Country country;
    private String name;
    private String capital;
    private String region;
    private String callingCodes;
    private String abbv;
    private int population;
    private String currencies;
    private String longLat;
    private String languages;
    private String borders;
    private TextView tvName, tvCapital, tvRegion, tvAbbv, tvCallingCodes, tvPopulation, tvCurrencies, tvLongLat, tvLanguages, tvBorders;
    private Button btnback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        country = (Country) getIntent().getParcelableExtra("country");
        name = country.getName();
        capital = country.getCapital();
        region = country.getRegion();
        abbv = country.getAlpha2code();
        callingCodes = country.getCallingCodes();
        population = country.getPopulation();
        currencies = country.getCurrencies();
        longLat = country.getLnglat();
        languages = country.getLanguages();
        borders = country.getBorders();

        setContentView(R.layout.selectedcountry);
        setViews();
        changeTxt();
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainActivity = new Intent(SelectedCountry.this, MainActivity.class);
                startActivity(mainActivity);
            }
        });
    }

    private void setViews() {
        tvName = findViewById(R.id.tvCountryName);
        tvCapital = findViewById(R.id.tvCountryCapital);
        tvRegion = findViewById(R.id.tvCountryRegion);
        tvAbbv = findViewById(R.id.tvCountryAbbv);
        tvCallingCodes = findViewById(R.id.tvCountryCallingCodes);
        tvPopulation = findViewById(R.id.tvCountryPopulation);
        tvCurrencies = findViewById(R.id.tvCountryCurrencies);
        tvLongLat = findViewById(R.id.tvCountryLongLat);
        tvLanguages = findViewById(R.id.tvCountryLanguages);
        tvBorders = findViewById(R.id.tvCountryBorders);
        btnback = findViewById(R.id.bckbutton);


    }

    private void changeTxt() {
        tvName.setText("Name: " + name);
        tvCapital.setText("Capital: " + capital);
        tvRegion.setText("Region: " + region);
        tvAbbv.setText("Abbreviation: " + abbv);
        tvCallingCodes.setText("Calling Codes: " + callingCodes.replace("[","+").replace("]","").replace("\"",""));
        tvPopulation.setText("Population: " + population);
        tvLongLat.setText("Longitude, Latitude: " + longLat.replace("[","").replace("]","").replace(",",", "));
        tvBorders.setText("Borders: " + borders.replace("[","").replace("]","").replace("\"",""));
        tvLanguages.setText("Languages: " + languages);
        tvCurrencies.setText("Currencies: " + currencies);
    }
}
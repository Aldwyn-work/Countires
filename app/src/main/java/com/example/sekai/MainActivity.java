package com.example.sekai;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText txtSearchbar;
    RecyclerView rvrecyclerview;

    SekaiAdapter sekaiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtSearchbar = findViewById(R.id.Searchbar);
        rvrecyclerview = findViewById(R.id.recyclerView);

        APIservice apiservice = new APIservice(MainActivity.this);

        apiservice.getCountries(new APIservice.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(MainActivity.this, "Somethings Wrong", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(List<Country> countries) {
                sekaiAdapter = new SekaiAdapter(MainActivity.this, countries, new SekaiAdapter.CountryAdapterListener() {
                    @Override
                    public void onCountrySelected(Country country) {
                            Intent selectCountry = new Intent(MainActivity.this, SelectedCountry.class);
                            selectCountry.putExtra("country", country);
                        startActivity(selectCountry);
                    }
                });

                rvrecyclerview.setAdapter(sekaiAdapter);
                rvrecyclerview.setLayoutManager(new LinearLayoutManager(MainActivity.this));

                txtSearchbar.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        sekaiAdapter.getFilter().filter(s.toString());
                    }
                });
            }
        });
    }
}
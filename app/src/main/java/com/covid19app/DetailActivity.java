package com.covid19app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private int positionCountry;
    TextView txtCases,txtRecovered,txtCritical,txtActive,txtTodayCases,
            txtTodayDeath,txtTotalDeath,txtCountries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        positionCountry = intent.getIntExtra("position",0);

        getSupportActionBar().setTitle("Detail of " + AffectedCountries.countryModelList.get(positionCountry).getCountry());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        txtCases = findViewById(R.id.txtCases);
        txtRecovered = findViewById(R.id.txtRecovered);
        txtCritical = findViewById(R.id.txtCritical);
        txtActive = findViewById(R.id.txtActive);
        txtTodayCases = findViewById(R.id.txtTodayCases);
        txtTodayDeath = findViewById(R.id.txtTodayDeath);
        txtTotalDeath = findViewById(R.id.txtTotalDeath);
        txtCountries = findViewById(R.id.txtCountries);

        txtCases.setText(AffectedCountries.countryModelList.get(positionCountry).getCases());
        txtRecovered.setText(AffectedCountries.countryModelList.get(positionCountry).getRecovered());
        txtCritical.setText(AffectedCountries.countryModelList.get(positionCountry).getCritical());
        txtActive.setText(AffectedCountries.countryModelList.get(positionCountry).getActive());
        txtTodayCases.setText(AffectedCountries.countryModelList.get(positionCountry).getTodayCases());
        txtTodayDeath.setText(AffectedCountries.countryModelList.get(positionCountry).getTodayDeaths());
        txtTotalDeath.setText(AffectedCountries.countryModelList.get(positionCountry).getDeaths());
        txtCountries.setText(AffectedCountries.countryModelList.get(positionCountry).getCountry());


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }
}

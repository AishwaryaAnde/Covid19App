package com.covid19app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView txtCases,txtRecovered,txtCritical,txtActive,txtTodayCases,
            txtTodayDeath,txtTotalDeath,txtCountries;
    ScrollView scrollStates;
    PieChart pieChart;
    SimpleArcLoader loader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtCases = findViewById(R.id.txtCases);
        txtRecovered = findViewById(R.id.txtRecovered);
        txtCritical = findViewById(R.id.txtCritical);
        txtActive = findViewById(R.id.txtActive);
        txtTodayCases = findViewById(R.id.txtTodayCases);
        txtTodayDeath = findViewById(R.id.txtTodayDeath);
        txtTotalDeath = findViewById(R.id.txtTotalDeath);
        txtCountries = findViewById(R.id.txtCountries);

        scrollStates = findViewById(R.id.scrollStates);
        pieChart = findViewById(R.id.pieChart);
        loader = findViewById(R.id.loader);
        
        fetchData();
    }

    private void fetchData() {

        String url = "https://corona.lmao.ninja/v2/all/";

        loader.start();

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.toString());

                            txtCases.setText(jsonObject.getString("cases"));
                            txtRecovered.setText(jsonObject.getString("recovered"));
                            txtCritical.setText(jsonObject.getString("critical"));
                            txtActive.setText(jsonObject.getString("active"));
                            txtTodayCases.setText(jsonObject.getString("todayCases"));
                            txtTodayDeath.setText(jsonObject.getString("todayDeaths"));
                            txtTotalDeath.setText(jsonObject.getString("deaths"));
                            txtCountries.setText(jsonObject.getString("affectedCountries"));

                            pieChart.addPieSlice(new PieModel("Cases",
                                    Integer.parseInt(txtCases.getText().toString()), Color.parseColor("#FFA726")));

                            pieChart.addPieSlice(new PieModel("Recovered",
                                    Integer.parseInt(txtRecovered.getText().toString()), Color.parseColor("#66BB6A")));

                            pieChart.addPieSlice(new PieModel("Death",
                                    Integer.parseInt(txtTotalDeath.getText().toString()), Color.parseColor("#EF5350")));

                            pieChart.addPieSlice(new PieModel("Active",
                                    Integer.parseInt(txtActive.getText().toString()), Color.parseColor("#29B6F6")));

                            pieChart.startAnimation();

                            loader.stop();
                            loader.setVisibility(View.GONE);
                            scrollStates.setVisibility(View.VISIBLE);

                        } catch (JSONException e) {

                            e.printStackTrace();
                            loader.stop();
                            loader.setVisibility(View.GONE);
                            scrollStates.setVisibility(View.VISIBLE);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.stop();
                loader.setVisibility(View.GONE);
                scrollStates.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    public void goTrackCountries(View view) {

        startActivity(new Intent(getApplicationContext(),AffectedCountries.class));
    }
}

package risabhmishra.com.air;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.JsonReader;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WeatherForecast extends AppCompatActivity {
    TextView today;
    RecyclerView recyclerView;

    private String JSONUrl;
    private SharedPreferences sharedpreference;
    List<Weather> weatherList;
    RecyclerView.LayoutManager layoutManager;
    WeatherForecastAdapter weatherForecastAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        today = findViewById(R.id.tv_today);
        recyclerView = findViewById(R.id.rview);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        weatherList = new ArrayList<>();


        sharedpreference = getSharedPreferences("sf", Context.MODE_PRIVATE);
        String lat = sharedpreference.getString("lat", "");
        String lon = sharedpreference.getString("long", "");

        Toast.makeText(this, lat + " " + lon, Toast.LENGTH_LONG).show();

        JSONUrl = "https://api.openweathermap.org/data/2.5/forecast?lat=" + lat + "&lon=" + lon + "&cnt=10&appid=256c80a1ec83c04d09af98ecdad6340d&units=metric";

        new RequestData().execute();

        weatherForecastAdapter = new WeatherForecastAdapter(this, weatherList);

        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(weatherForecastAdapter);
    }

    private class RequestData extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {

            JsonObjectRequest jsr = new JsonObjectRequest(Request.Method.GET, JSONUrl, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {

                        JSONArray main_object = response.getJSONArray("list");

                        for (int i = 1; i < 10; i++) {
                            JSONObject main = main_object.getJSONObject(i).getJSONObject("main");
                            String min = String.valueOf(main.getDouble("temp_min"));
                            String max = String.valueOf(main.getDouble("temp_max"));
                            String day = main_object.getJSONObject(i).getString("dt_txt");

                            String iconcode = main_object.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("icon");

                            String iconurl = "http://openweathermap.org/img/w/" + iconcode + ".png";

                            weatherList.add(new Weather(day, min, max, iconurl));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }
            );

            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            queue.add(jsr);


            return true;
        }

    }
}



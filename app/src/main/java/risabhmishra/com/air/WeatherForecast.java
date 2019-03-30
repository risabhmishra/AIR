package risabhmishra.com.air;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

public class WeatherForecast extends AppCompatActivity {
  TextView today;
  RecyclerView recyclerView;
  private String JSONUrl = "https://api.openweathermap.org/data/2.5/forecast?lat="++"&lon="++"&appid=256c80a1ec83c04d09af98ecdad6340d&units=metric";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        today = findViewById(R.id.tv_today);
        recyclerView = findViewById(R.id.rview);


    }
}

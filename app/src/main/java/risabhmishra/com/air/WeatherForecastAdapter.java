package risabhmishra.com.air;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class WeatherForecastAdapter extends RecyclerView.Adapter<WeatherForecastAdapter.MyViewHolder> {

    public WeatherForecastAdapter(Context context, List<Weather> weatherForecasts) {
        this.context = context;
        this.weatherForecasts = weatherForecasts;
    }

    Context context;
    List<Weather> weatherForecasts;


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.weather_forecast_item,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.day.setText(weatherForecasts.get(position).getDay());
        holder.min.setText(weatherForecasts.get(position).getMin_temp());
        holder.max.setText(weatherForecasts.get(position).getMax_temp());

        Picasso.get().load(weatherForecasts.get(position).getImg_url()).into(holder.weather);



    }

    @Override
    public int getItemCount() {
        return weatherForecasts.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView day,min,max;
        ImageView weather;

        public MyViewHolder(View itemView) {
            super(itemView);

            day = itemView.findViewById(R.id.tv_day);
            min = itemView.findViewById(R.id.tv_min);
            max = itemView.findViewById(R.id.tv_max);
            weather = itemView.findViewById(R.id.iv_weather);


        }
    }
}
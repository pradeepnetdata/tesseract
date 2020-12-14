package com.rakuten.weather.provider;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rakuten.weather.R;
import com.rakuten.weather.model.WeatherInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

public class WeatherListAdapter extends RecyclerView.Adapter<WeatherListAdapter.ViewHolder> {

    private List<WeatherInfo> mWeatherList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context mContext;

    // data is passed into the constructor
    public WeatherListAdapter(Context context, List<WeatherInfo> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mWeatherList = data;
        mContext= context;
    }
    public void addItems(WeatherInfo weatherInfo) {
        mWeatherList.add(weatherInfo);
        notifyDataSetChanged();
    }
    public void refineDataList(WeatherInfo weatherInfo) {
        if(mWeatherList!=null && mWeatherList.size() >0){
            for(int i=0; i< mWeatherList.size();i++){

                if(mWeatherList.get(i).getCityName().equals(weatherInfo.getCityName())){
                    mWeatherList.remove(i);
                }
            }
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_list_item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WeatherInfo weatherInfo = mWeatherList.get(position);
        holder.cityName.setText(weatherInfo.getCityName() + ", " + weatherInfo.getCountryName());
        holder.details.setText(weatherInfo.getWeatherDescription() + ", Humidity:"+ weatherInfo.getHumidity()+ "%");
        String temp = weatherInfo.getTemperature()+ " ℃";
        holder.temp_info.setText(temp + ", " +weatherInfo.getCountryTime());

        Picasso.get().load(weatherInfo.getWeatherIconUrl())
                .fit().centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.weatherIcon);

    }

    @Override
    public int getItemCount() {
        if(mWeatherList!=null && mWeatherList.size() > 0){
            Log.i("WeatherListAdapter", "getItemCount ()");
            return mWeatherList.size();
        }
        return 0;
    }
    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView cityName;
        TextView details;
        ImageView weatherIcon;
        TextView temp_info;


        ViewHolder(View itemView) {
            super(itemView);
            weatherIcon =(ImageView) itemView.findViewById(R.id.weatherIcon);
            cityName = (TextView)itemView.findViewById(R.id.cityName);
            details = (TextView)itemView.findViewById(R.id.details);
            temp_info = (TextView)itemView.findViewById(R.id.temp_info);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
    // convenience method for getting data at click position
    public WeatherInfo getWeatherItem(int id) {
        return mWeatherList.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }


}

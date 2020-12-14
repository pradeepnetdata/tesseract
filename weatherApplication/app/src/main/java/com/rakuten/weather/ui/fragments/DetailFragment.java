package com.rakuten.weather.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.rakuten.weather.MainActivity;
import com.rakuten.weather.R;
import com.rakuten.weather.model.WeatherInfo;
import com.rakuten.weather.viewmodel.base.BaseViewModel;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Locale;

public class DetailFragment extends BaseFragment {
    private static final String TAG = DetailFragment.class.getName();
    public static final String EXTRA_TEXT ="text";
    private WeatherInfo weatherInfo;
    public static final String WEATHER_KEY = "weather_key";
    private TextView cityField ;
    private TextView weatherDetails;
    private TextView detailsField;
    private TextView currentTemperatureField;
    private ImageView weatherIcon;
    private TextView humidity;
    private TextView pressure;
    private TextView wind_speed;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            String text = bundle.getString(EXTRA_TEXT);

            weatherInfo = (WeatherInfo) getArguments().getSerializable(
                    WEATHER_KEY);
            setUpDetails();
        }
    }

    public void setUpDetails() {
        Log.i(TAG, "setUpDetails :: called :: " + weatherInfo.getCityName());
         cityField = (TextView) getView().findViewById(R.id.city_field);
         weatherDetails = (TextView) getView().findViewById(R.id.weather_details);
         detailsField = (TextView) getView().findViewById(R.id.details_field);
         currentTemperatureField = (TextView) getView().findViewById(R.id.current_temperature_field);
         humidity = (TextView) getView().findViewById(R.id.humidity);
         pressure = (TextView) getView().findViewById(R.id.pressure);
         wind_speed = (TextView) getView().findViewById(R.id.wind_speed);
         weatherIcon = (ImageView) getView().findViewById(R.id.weather_icon);

        cityField.setText(weatherInfo.getCityName() +
                ", " +weatherInfo.getCountryName());

        weatherDetails.setText(weatherInfo.getWeatherDescription().toUpperCase(Locale.US) +" , ");
        currentTemperatureField.setText(weatherInfo.getTemperature()+ " ℃");
        humidity.setText(weatherInfo.getHumidity() + "%");
        pressure.setText(weatherInfo.getPressure() + " hPa");
        wind_speed.setText(weatherInfo.getWindSpeed()+"km/h");

        detailsField.setText( weatherInfo.getCountryTime() );


        Picasso.get().load(weatherInfo.getWeatherIconUrl())
                .fit().centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(weatherIcon);


    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_weather_detail;
    }

    @Override
    public BaseViewModel getViewModel() {
        return null;
    }
}
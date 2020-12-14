package com.rakuten.weather.data.remote;


import com.rakuten.weather.model.WeatherInfo;

import io.reactivex.Single;

public interface RemoteServiceHelper {

    Single<WeatherInfo> getWeatherInfoApiCall(String cityName);
}

package com.rakuten.weather.data.remote;

import com.rakuten.weather.BuildConfig;

public final class ApiEndPoint {

    public static final String OPEN_WEATHER_MAP_API = BuildConfig.BASE_URL + "?access_key="+BuildConfig.API_KEY;
    //public static final String OPEN_WEATHER_MAP_API ="http://api.weatherstack.com/current?access_key=2feed809d43842f8474d00213f0fa706&query=New York";

    private ApiEndPoint() {
        // This class is not publicly instantiable
    }
}

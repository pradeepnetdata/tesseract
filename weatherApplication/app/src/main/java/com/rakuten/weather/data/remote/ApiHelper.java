package com.rakuten.weather.data.remote;

import android.util.Log;
import com.rakuten.weather.BuildConfig;
import com.rakuten.weather.model.WeatherInfo;
import com.rx2androidnetworking.Rx2AndroidNetworking;
import io.reactivex.Single;


public class ApiHelper implements RemoteServiceHelper {

    private static final String TAG = ApiHelper.class.getName();

    @Override
    public Single<WeatherInfo> getWeatherInfoApiCall(String city) {
        Log.i(TAG, "getWeatherInfoApiCall :: called ");
        return Rx2AndroidNetworking.get(ApiEndPoint.OPEN_WEATHER_MAP_API)
                .addQueryParameter("query", city)
                .build()
                .getObjectSingle(WeatherInfo.class);

    }

}

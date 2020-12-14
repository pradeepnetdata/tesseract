package com.rakuten.weather.data;

import android.content.Context;

import com.rakuten.weather.data.remote.RemoteServiceHelper;
import com.rakuten.weather.model.WeatherInfo;
import io.reactivex.Single;


public class AppDataManager implements DataManager {

    private static final String TAG = "AppDataManager";
    private final RemoteServiceHelper mApiHelper;
    private final Context mContext;


    public AppDataManager(Context context, RemoteServiceHelper apiHelper) {
        mContext = context;
        mApiHelper = apiHelper;
    }

    @Override
    public Single<WeatherInfo> getWeatherInfoApiCall(String cityName) {
        return mApiHelper.getWeatherInfoApiCall(cityName);
    }
}

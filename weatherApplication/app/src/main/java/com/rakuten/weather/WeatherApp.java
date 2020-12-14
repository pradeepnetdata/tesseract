package com.rakuten.weather;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.interceptors.HttpLoggingInterceptor;
import com.rakuten.weather.data.AppDataManager;
import com.rakuten.weather.data.DataManager;
import com.rakuten.weather.data.remote.ApiHelper;
import com.rakuten.weather.dbhelper.AppDatabase;
import com.rakuten.weather.provider.AppSchedulerProvider;
import com.rakuten.weather.provider.SchedulerProvider;
import com.rakuten.weather.utils.AppUtils;

public class WeatherApp extends Application {

    private static WeatherApp singleton;

    public static WeatherApp getInstance() {
        return singleton;
    }

    private AppDataManager appDataManager;
    private ApiHelper apiHelper;
    private SchedulerProvider schedulerProvider;
    private AppDatabase appDatabase;
    @Override
    public void onCreate() {

        super.onCreate();
        singleton =this;
       AndroidNetworking.initialize(getApplicationContext());
        if (BuildConfig.DEBUG) {
            AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.BODY);
        }
        apiHelper = new ApiHelper();
        appDataManager = new AppDataManager(getApplicationContext(),apiHelper);
        schedulerProvider = new AppSchedulerProvider();
        createAppDatabase();
    }

    public AppDataManager getDataManager() {
        return appDataManager;
    }
    public SchedulerProvider getSchedulerProvider(){
        return schedulerProvider;
    }
    public AppDatabase getAppDatabase() {
        return appDatabase;
    }
    private void createAppDatabase() {
        appDatabase= Room.databaseBuilder(getApplicationContext(), AppDatabase.class, AppUtils.DB_NAME).build();
    }
}

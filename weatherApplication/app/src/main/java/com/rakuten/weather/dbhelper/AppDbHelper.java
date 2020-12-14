package com.rakuten.weather.dbhelper;

import android.util.Log;

import com.rakuten.weather.model.db.Weather;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;


public class AppDbHelper implements DbHelper {

    private final AppDatabase mAppDatabase;

    public AppDbHelper(AppDatabase appDatabase) {
        this.mAppDatabase = appDatabase;
    }


    @Override
    public Observable<List<Weather>> getAllCitiesWeatherInfo() {
        Log.i("TAG", "getAllCitiesWeatherInfo >> ");
        return mAppDatabase.weatherDao().loadAll().toObservable();

    }

    @Override
    public Observable<Boolean> insertWeatherInfo(Weather weather) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call()  {
                Log.i("TAG", "insertWeatherInfo :: start::");
                mAppDatabase.weatherDao().insert(weather);
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> updateWeatherInfo(Weather weather) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call()  {
                Log.i("TAG", "updateWeatherInfo :: start::");
                mAppDatabase.weatherDao().update(weather);
                return true;
            }
        });
    }

    @Override
    public Observable<List<Weather>> getWeatherInfoByName(String city_name) {
        Log.i("TAG", "getAllCitiesWeatherInfo :: weather >> ");
        return mAppDatabase.weatherDao().findByName(city_name).toObservable();
    }
}

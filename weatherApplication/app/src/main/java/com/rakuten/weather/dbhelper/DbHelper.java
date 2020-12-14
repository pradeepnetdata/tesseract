package com.rakuten.weather.dbhelper;

import com.rakuten.weather.model.db.Weather;
import java.util.List;
import io.reactivex.Observable;



public interface DbHelper {

    Observable<List<Weather>> getAllCitiesWeatherInfo();
    Observable<Boolean> insertWeatherInfo(final Weather weather);
    Observable<Boolean> updateWeatherInfo(final Weather weather);
    Observable<List<Weather>> getWeatherInfoByName(String city_name);
}

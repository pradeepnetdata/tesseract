package com.rakuten.weather.dbhelper.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.rakuten.weather.model.WeatherInfo;
import com.rakuten.weather.model.db.Weather;

import java.util.List;

import io.reactivex.Single;


@Dao
public interface WeatherDao {

    @Delete
    void update(Weather weather);

    @Delete
    void delete(Weather weather);

    @Query("SELECT * FROM cities WHERE cityName LIKE :name")
    Single<List<Weather>> findByName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Weather weather);


    @Query("SELECT * FROM cities")
    Single<List<Weather>> loadAll();

}

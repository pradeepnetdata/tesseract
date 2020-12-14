package com.rakuten.weather.dbhelper;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.rakuten.weather.dbhelper.dao.WeatherDao;
import com.rakuten.weather.model.db.Weather;

@Database(entities = { Weather.class}, exportSchema = true,version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract WeatherDao weatherDao();
}
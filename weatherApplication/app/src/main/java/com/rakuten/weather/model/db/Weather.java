package com.rakuten.weather.model.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cities")
public class Weather {

    @ColumnInfo(name = "created_at")
    public String createdAt;

    @PrimaryKey(autoGenerate = true)
    public Long id;

    @ColumnInfo(name = "cityName")
    public String cityName;

    @ColumnInfo(name = "countryName")
    public String countryName;

    @ColumnInfo(name = "countryTimeZone")
    public String countryTimeZone;

    @ColumnInfo(name = "countryTime")
    public String countryTime;

    @ColumnInfo(name = "observation_time")
    public String observation_time;

    @ColumnInfo(name = "temperature")
    public String temperature;

    @ColumnInfo(name = "weather_code")
    public String weather_code;

    @ColumnInfo(name = "weatherIcon_url")
    public String weatherIcon_url;

    @ColumnInfo(name = "weatherDesc")
    public String weatherDesc;

    @ColumnInfo(name = "wind_speed")
    public String wind_speed;

    @ColumnInfo(name = "wind_degree")
    public String wind_degree;

    @ColumnInfo(name = "wind_dir")
    public String wind_dir;

    @ColumnInfo(name = "pressure")
    public String pressure;

    @ColumnInfo(name = "humidity")
    public String humidity;



    @ColumnInfo(name = "visibility")
    public String visibility;
}

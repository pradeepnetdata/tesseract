package com.rakuten.weather.model;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WeatherInfo implements Serializable {

    @ColumnInfo(name = "created_at")
    public String createdAt;

    @PrimaryKey(autoGenerate = true)
    public Long id;

    @Expose
    @SerializedName("location")
    private Location location ;

    public Location getLocation() {

        if(location==null){
            location= new WeatherInfo.Location();
        }
        return location;
    }

    public String getCityName() {
        return location.cityName;
    }

    public void setCityName(String city_name) {
        getLocation().cityName = city_name;
    }


    public String getCountryName() {
        return location.countryName;
    }

    public void setCountryName(String countryName) {
        getLocation().countryName = countryName;
    }

    public String getCountryTimeZone() {
        return location.countryTimeZone;
    }

    public void setCountryTimeZone(String countryTimeZone) {
        getLocation().countryTimeZone = countryTimeZone;
    }

    public String getCountryTime() {
        return location.countryTime;
    }

    public void setCountryTime(String countryTime) {
        getLocation().countryTime = countryTime;
    }

    public static class Location {

        @Expose
        @SerializedName("name")
        private String cityName;

        @Expose
        @SerializedName("country")
        private String countryName;

        @Expose
        @SerializedName("timezone_id")
        private String countryTimeZone;
        @Expose
        @SerializedName("localtime")
        private String countryTime;
    }

    public Current getCurrentWeatherInfo() {
        if(currentWeatherInfo==null){
            currentWeatherInfo= new WeatherInfo.Current();
        }
        return currentWeatherInfo;
    }

    @Expose
    @SerializedName("current")
    private Current currentWeatherInfo  ;


    public static class Current {
        @Expose
        @SerializedName("observation_time")
        private String observation_time;

        @Expose
        @SerializedName("temperature")
        private String temperature;

        @Expose
        @SerializedName("weather_code")
        private String weather_code;

        @Expose
        @SerializedName("weather_icons")
        private List<String> weatherIconList;

        @Expose
        @SerializedName("weather_descriptions")
        private List<String> weatherDescList;

        @Expose
        @SerializedName("wind_speed")
        private String wind_speed;

        @Expose
        @SerializedName("wind_degree")
        private String wind_degree;

        @Expose
        @SerializedName("wind_dir")
        private String wind_dir;

        @Expose
        @SerializedName("pressure")
        private String pressure;

        @Expose
        @SerializedName("humidity")
        private String humidity;

        @Expose
        @SerializedName("visibility")
        private String visibility;
    }
    public String getObservationTime() {
        return getCurrentWeatherInfo().observation_time;
    }

    public void setObservationTime(String observation_time) {
        getCurrentWeatherInfo().observation_time = observation_time;
    }

    public String getTemperature() {
        return getCurrentWeatherInfo().temperature;
    }

    public void setTemperature(String temperature) {
        getCurrentWeatherInfo().temperature = temperature;
    }

    public String getWeatherCode() {
        return getCurrentWeatherInfo().weather_code;
    }

    public void setWeatherCode(String weather_code) {
        getCurrentWeatherInfo().weather_code = weather_code;
    }

    public String getWeatherIconUrl() {
        if(getCurrentWeatherInfo().weatherIconList!=null && getCurrentWeatherInfo().weatherIconList.size()>0) {
            return getCurrentWeatherInfo().weatherIconList.get(0);
        }

        return null;
    }

    public void setWeatherIconUrl(String weather_url) {
        if(getCurrentWeatherInfo().weatherIconList==null) {
            getCurrentWeatherInfo().weatherIconList = new ArrayList<>();
        }
        getCurrentWeatherInfo().weatherIconList.add(weather_url);
    }
    public String getWeatherDescription() {

        if(getCurrentWeatherInfo().weatherDescList!=null && getCurrentWeatherInfo().weatherDescList.size()>0) {
            return getCurrentWeatherInfo().weatherDescList.get(0);
        }
        return null;
    }

    public void setWeatherDescription(String weather_desc) {
        if(getCurrentWeatherInfo().weatherDescList==null) {
            getCurrentWeatherInfo().weatherDescList = new ArrayList<>();
        }
        getCurrentWeatherInfo().weatherDescList.add(weather_desc);
    }

    public String getWindSpeed() {
        return getCurrentWeatherInfo().wind_speed;
    }

    public void setWindSpeed(String wind_speed) {
        getCurrentWeatherInfo().wind_speed= wind_speed;
    }

    public String getWindDegree() {
        return getCurrentWeatherInfo().wind_degree;
    }
    public void setWindDegree(String wind_degree) {
        getCurrentWeatherInfo().wind_degree= wind_degree;
    }
    public String getWindDirection() {
        return getCurrentWeatherInfo().wind_dir;
    }
    public void setWindDirection(String wind_dir) {
        getCurrentWeatherInfo().wind_dir= wind_dir;
    }

    public String getPressure() {
        return getCurrentWeatherInfo().pressure;
    }
    public void setPressure(String pressure) {
        getCurrentWeatherInfo().pressure= pressure;
    }
    public String getHumidity() {
        return getCurrentWeatherInfo().humidity;
    }
    public void setHumidity(String humidity) {
        getCurrentWeatherInfo().humidity= humidity;
    }
    public String getVisibility() {
        return getCurrentWeatherInfo().visibility;
    }
    public void setVisibility(String visibility) {
        getCurrentWeatherInfo().visibility= visibility;
    }
}

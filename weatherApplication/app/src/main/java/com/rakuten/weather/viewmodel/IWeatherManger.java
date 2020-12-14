package com.rakuten.weather.viewmodel;



public interface IWeatherManger {

    void handleError(Throwable throwable);

    void getCityWeatherInfo();

    void IsShowingProgress();
}

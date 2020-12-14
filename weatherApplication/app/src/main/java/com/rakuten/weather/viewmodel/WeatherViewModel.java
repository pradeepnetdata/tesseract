package com.rakuten.weather.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rakuten.weather.WeatherApp;
import com.rakuten.weather.data.DataManager;
import com.rakuten.weather.dbhelper.AppDbHelper;
import com.rakuten.weather.model.WeatherInfo;
import com.rakuten.weather.model.db.Weather;
import com.rakuten.weather.provider.SchedulerProvider;
import com.rakuten.weather.viewmodel.base.BaseViewModel;

import java.util.List;


public class WeatherViewModel extends BaseViewModel implements IWeatherHelper {

    private static final String TAG = WeatherViewModel.class.getName();
    private final MutableLiveData<WeatherInfo> weatherLiveData;
    private final MutableLiveData<List<Weather>> resposLiveData;

    private AppDbHelper appDbHelper;
    public WeatherViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        weatherLiveData = new MutableLiveData<>();
        resposLiveData = new MutableLiveData<>();
        appDbHelper = new AppDbHelper(WeatherApp.getInstance().getAppDatabase());

    }

    @Override
    public void getWeatherResultInfo(String city) {
        //   .doOnSuccess(response-> setData(response))
        Log.d(TAG, "getWeatherResultInfo :: start");
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getWeatherInfoApiCall(city)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .doOnSuccess(response-> setData(response))
                .subscribe(response -> {
                     setIsLoading(false);
                    //getNavigator().openMainActivity();
                }, throwable -> {
                    setIsLoading(false);
                    //getNavigator().handleError(throwable);
                }));
    }
    public void setData(WeatherInfo weatherInfoSingle){
        weatherLiveData.setValue(weatherInfoSingle);
    }
    public LiveData<WeatherInfo> getWeatherLiveData() {
        return weatherLiveData;
    }
    public void getWeatherRepositoryInfo() {
        getCompositeDisposable().add(appDbHelper.getAllCitiesWeatherInfo()

                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    Log.d(TAG, "getWeatherRepositoryInfo :: size-- " +response.size());
                    resposLiveData.setValue(response);
                }, throwable -> {
                    //decideNextActivity();

                    if(throwable!=null){
                        Log.d(TAG, "getWeatherRepositoryInfo :: error " + throwable.toString());
                    }

                }));
    }

    public void saveWeatherRepositoryInfo(WeatherInfo weatherInfo) {
        Weather weather = new Weather();
        weather.cityName =weatherInfo.getCityName();
        weather.countryName = weatherInfo.getCountryName();
        weather.countryTimeZone = weatherInfo.getCountryTimeZone();
        weather.countryTime = weatherInfo.getCountryTime();
        weather.observation_time = weatherInfo.getObservationTime();
        weather.temperature = weatherInfo.getTemperature();
        weather.weather_code = weatherInfo.getWeatherCode();
        weather.weatherIcon_url = weatherInfo.getWeatherIconUrl();
        weather.weatherDesc = weatherInfo.getWeatherDescription();
        weather.wind_speed = weatherInfo.getWindSpeed();
        weather.wind_degree = weatherInfo.getWindDegree();
        weather.wind_dir = weatherInfo.getWindDirection();
        weather.pressure = weatherInfo.getPressure();
        weather.humidity = weatherInfo.getHumidity();
        Log.d(TAG, "saveWeatherRepositoryInfo :: city name ::" + weather.cityName);
        getAllCitiesByName(weather);
    }
    public void saveWeatherRepository(Weather weather) {
        Log.d(TAG, "saveWeatherRepositoryInfo :: city name ::" +weather.cityName);
        getCompositeDisposable().add(appDbHelper.insertWeatherInfo(weather)

                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    Log.d(TAG, "saveWeatherRepositoryInfo :: size-- " +response);
                    //getAllCitiesWeatherInfo();
                }, throwable -> {
                    //decideNextActivity();
                    Log.d(TAG, "saveWeatherRepositoryInfo :: error " );
                }));
    }

    public void getAllCitiesByName(Weather weather){
        Log.d(TAG, "getAllCitiesInfo :: city name ::");
        getCompositeDisposable().add(appDbHelper.getWeatherInfoByName(weather.cityName)

                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    Log.d(TAG, "getAllCitiesInfo :: response " +response);

                    if(response!=null && response.size() >0){
                        updateWeatherRepositoryInfo(weather);
                    } else {
                        saveWeatherRepository(weather);
                    }

                }, throwable -> {
                    saveWeatherRepository(weather);
                    Log.d(TAG, "getAllCitiesWeatherInfo :: error " );
                }));
    }

    public void updateWeatherRepositoryInfo(Weather weather){
        Log.d(TAG, "getAllCitiesInfo :: city name ::");
        getCompositeDisposable().add(appDbHelper.updateWeatherInfo(weather)

                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    Log.d(TAG, "getAllCitiesInfo :: response " +response);

                }, throwable -> {

                    Log.d(TAG, "getAllCitiesWeatherInfo :: error " +throwable.toString());
                }));
    }

    public LiveData<List<Weather>> getWeatherDBLiveData() {
        return resposLiveData;
    }
}

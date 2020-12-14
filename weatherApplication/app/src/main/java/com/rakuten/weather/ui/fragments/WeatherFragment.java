package com.rakuten.weather.ui.fragments;

import android.content.Context;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rakuten.weather.MainActivity;
import com.rakuten.weather.R;
import com.rakuten.weather.WeatherApp;
import com.rakuten.weather.dbhelper.AppDatabase;
import com.rakuten.weather.dbhelper.AppDbHelper;
import com.rakuten.weather.model.WeatherInfo;
import com.rakuten.weather.model.db.Weather;
import com.rakuten.weather.provider.WeatherListAdapter;
import com.rakuten.weather.viewmodel.ViewModelProviderFactory;
import com.rakuten.weather.viewmodel.WeatherViewModel;
import com.rakuten.weather.viewmodel.base.BaseViewModel;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;


/** Displays a list of cities weather. */
public class WeatherFragment extends BaseFragment implements WeatherListAdapter.ItemClickListener{

  private static final String TAG= WeatherFragment.class.getName();
  private WeatherListAdapter mWeatherAdapter;
  private RecyclerView recyclerView;
  private TextView textViewMessage;
  private List<WeatherInfo> weatherInfoList;
  private ViewModelProviderFactory factory;
  private WeatherViewModel mWeatherViewModel;
  private OnItemSelectedListener listener;
  public interface OnItemSelectedListener{

    void onItemSelected(WeatherInfo weatherInfo);
  }
  @Override
  public int getLayoutId() {
    return R.layout.fragment_weather;
  }

  @Override
  public BaseViewModel getViewModel() {
    Log.d(TAG, "getViewModel :: " + ((WeatherApp)getActivity().getApplicationContext()).getDataManager());
    factory = new ViewModelProviderFactory(WeatherApp.getInstance().getDataManager(), WeatherApp.getInstance().getSchedulerProvider());
    mWeatherViewModel = ViewModelProviders.of(this,factory).get(WeatherViewModel.class);
    return mWeatherViewModel;
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof OnItemSelectedListener) {
      listener = (OnItemSelectedListener) context;
    }
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    weatherInfoList = new ArrayList<>();
    subscribeToLiveData();
    subscribeRepositoryLiveData();
    getWeatherRepositoryInfo();
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    Log.i(TAG, "WeatherListFragment :: onViewCreated() :: called");
    recyclerView = view.findViewById(R.id.weather_list);
    textViewMessage = view.findViewById(R.id.message);
    ((MainActivity)getActivity()).showSearchMenu();
    setUp();

  }
  private void setUp() {
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    recyclerView.setItemAnimator(new DefaultItemAnimator());
    mWeatherAdapter = new WeatherListAdapter(getContext(),weatherInfoList);
    recyclerView.setAdapter(mWeatherAdapter);
    mWeatherAdapter.setClickListener(this::onItemClick);

    if(weatherInfoList.size() >0){
      hideTextMessage();
    }
  }


  public void hideTextMessage() {
    if (textViewMessage.getVisibility() == View.VISIBLE) {
      // Its visible
      textViewMessage.setVisibility(View.GONE);
    }
  }

  public void updateWeatherData(WeatherInfo weatherInfo) {
    Log.i(TAG, "updateWeatherData () :: called");
    hideTextMessage();
    mWeatherAdapter.addItems(weatherInfo);
  }


  @Override
  public void onDestroyView() {
    super.onDestroyView();
  }


  @Override
  public void onItemClick(View view, int position) {
    Log.i(TAG, "onItemClick ::  start " );
    WeatherInfo weatherInfo = mWeatherAdapter.getWeatherItem(position);
    Log.i(TAG, "onItemClick ::  start " +weatherInfo.getCityName());
    listener.onItemSelected(weatherInfo);
  }
  private void subscribeToLiveData() {

    mWeatherViewModel.getWeatherLiveData().observe(this, new Observer<WeatherInfo>() {
      @Override
      public void onChanged(@androidx.annotation.Nullable WeatherInfo weatherInfo) {
        Log.i(TAG, "subscribeToLiveData ::  start " );
        getBaseActivity().hideLoading();
        mWeatherAdapter.refineDataList(weatherInfo);
        updateWeatherData(weatherInfo);
        saveWeatherRepositoryInfo(weatherInfo);
      }
    });
  }
  public void getWeatherData(final String city){
     hideKeyboard();
     mWeatherViewModel.getWeatherResultInfo(city);
  }

  private void saveWeatherRepositoryInfo( WeatherInfo weatherInfo){
    mWeatherViewModel.saveWeatherRepositoryInfo(weatherInfo);
  }


  private void getWeatherRepositoryInfo(){
    Log.i(TAG, "getWeatherRepositoryInfo ::  start " );
    mWeatherViewModel.getWeatherRepositoryInfo();
  }

  private void subscribeRepositoryLiveData() {

    mWeatherViewModel.getWeatherDBLiveData().observe(this, new Observer<List<Weather>>() {
      @Override
      public void onChanged(List<Weather> weathers) {
        Log.i(TAG, "RepositoryLiveData ::  start " +weathers.size());

        for(Weather weather : weathers){
          WeatherInfo weatherInfo = new WeatherInfo();
           weatherInfo.setCityName(weather.cityName);
          Log.i(TAG, "RepositoryLiveData ::  start " +weatherInfo.getCityName());
          weatherInfo.setCountryName(weather.countryName);
          weatherInfo.setCountryTimeZone(weather.countryTimeZone);
          weatherInfo.setCountryTime(weather.countryTime);
          weatherInfo.setObservationTime(weather.observation_time);
          weatherInfo.setTemperature(weather.temperature);
          weatherInfo.setWeatherCode(weather.weather_code);
          weatherInfo.setWeatherIconUrl(weather.weatherIcon_url);
          weatherInfo.setWeatherDescription(weather.weatherDesc);
          weatherInfo.setWindSpeed(weather.wind_speed);
          weatherInfo.setWindDegree(weather.wind_degree);
          weatherInfo.setWindDirection(weather.wind_dir);
          weatherInfo.setPressure(weather.pressure);
          weatherInfo.setHumidity(weather.humidity);
          updateWeatherData(weatherInfo);
        }

      }

    });
  }
}

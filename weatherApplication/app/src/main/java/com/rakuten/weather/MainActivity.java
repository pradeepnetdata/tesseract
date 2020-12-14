package com.rakuten.weather;

import android.app.SearchManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.rakuten.weather.model.WeatherInfo;
import com.rakuten.weather.ui.BaseActivity;
import com.rakuten.weather.ui.fragments.DetailFragment;
import com.rakuten.weather.ui.fragments.WeatherFragment;

import java.util.Locale;

public class MainActivity extends BaseActivity implements WeatherFragment.OnItemSelectedListener{

    private static final String TAG = MainActivity.class.getName();
    private MenuItem searchViewItem;
    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Weather");
        setSupportActionBar(toolbar);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragment_container, new WeatherFragment(),"weather_fragment");
        ft.commit();
    }



    @Override
    protected void onStart() {
        super.onStart();

    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        searchViewItem = menu.findItem(R.id.menuSearch);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) searchViewItem.getActionView();

        //you can put a hint for the search input field
        searchView.setQueryHint("Search...");
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setIconifiedByDefault(true);

        //here we will get the search query
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "onQueryTextSubmit :: query result :: "+query);
                //do the search here
                String city_name = query.toUpperCase(Locale.US);
                updateWeatherData(city_name);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
               // mAdapter.getFilter().filter(newText);
                Log.d(TAG, "query result :: "+newText);
                return true;
            }
        });

        return true;
    }

    public void showSearchMenu(){
        if (searchViewItem!=null){
            searchViewItem.setVisible(true);
            searchView.setVisibility(View.VISIBLE);
        }
    }
    private void hideSearchMenu(){
        searchViewItem.setVisible(false);
        searchView.setVisibility(View.GONE);
    }
    private void updateWeatherData(final String city){
        Log.d(TAG, "subscribeToLiveData ::  start " );

        boolean isNumberExist = city.matches(".*\\d.*");

        if(isNumberExist){
            Toast.makeText(this,"City is not valid. Please try again", Toast.LENGTH_LONG).show();
            if (searchView!=null){
                searchView.setQuery("", false);
            }
            return;
        }
        showLoading();
        FragmentManager fm = getSupportFragmentManager();
        WeatherFragment fragment = (WeatherFragment)fm.findFragmentById(R.id.fragment_container);

        if(isNetworkConnected()) {
            fragment.getWeatherData(city);
        } else {
            Log.i(TAG, "No internet connection found");
            hideLoading();
            hideKeyboard();
            Toast.makeText(this,"No internet connection found!", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onItemSelected(WeatherInfo weatherInfo) {
        Log.i(TAG, "onItemSelected :: called");
        DetailFragment fragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(DetailFragment.WEATHER_KEY, weatherInfo);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();
        hideSearchMenu();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            //Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getSupportFragmentManager().popBackStack();
        }

    }
}
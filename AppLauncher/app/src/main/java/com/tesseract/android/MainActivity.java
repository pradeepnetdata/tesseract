package com.tesseract.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.tesseract.android.model.AppInfo;
import com.tesseract.android.provider.AppInfoAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private ArrayList appsList = new ArrayList<AppInfo>();
    private RecyclerView recyclerView;
    private AppInfoAdapter mAdapter;
    private final String TAG =  MainActivity.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("AppLauncher");
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new AppInfoAdapter(context,appsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        prepareAppList();
        mAdapter.notifyDataSetChanged();
    }

    private void prepareAppList(){

        PackageManager pm = context.getPackageManager();
        //appsList = new ArrayList<AppInfo>();

        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> allApps = pm.queryIntentActivities(i, 0);
        for(ResolveInfo ri:allApps) {
            AppInfo app = new AppInfo();
            app.setLabel( ri.loadLabel(pm));
            app.setPackageName(ri.activityInfo.packageName);
            app.setIcon(ri.activityInfo.loadIcon(pm));

            appsList.add(app);
        }
        Collections.sort(appsList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        //getting the search view from the menu
        MenuItem searchViewItem = menu.findItem(R.id.menuSearch);

        //getting search manager from systemservice
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        //getting the search view
        final SearchView searchView = (SearchView) searchViewItem.getActionView();

        //you can put a hint for the search input field
        searchView.setQueryHint("Search...");
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setIconifiedByDefault(true);

        //here we will get the search query
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                //do the search here
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                Log.d(TAG, "query result :: "+newText);
                return true;
            }
        });

        return true;
    }
}
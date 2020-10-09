package com.tesseract.android.provider;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.tesseract.android.R;
import com.tesseract.android.model.AppInfo;

import java.util.ArrayList;
import java.util.List;

public class AppInfoAdapter extends RecyclerView.Adapter<AppInfoAdapter.ViewHolder> implements Filterable {
    private List<AppInfo> appsList;
    private List<AppInfo> filteredList;
    private Context context;
    private Filter appFilter;

    public AppInfoAdapter(Context context,List<AppInfo> appsList) {
     this.context = context;
     this.appsList =appsList;
     this.filteredList =appsList;
     getFilter();
    }

    /**
     * Get custom filter
     * @return filter
     */
    @Override
    public Filter getFilter() {
        if (appFilter == null) {
            appFilter = new AppInfoFilter();
        }

        return appFilter;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView appName;
        public ImageView appImg;
        public TextView packageName;

        //This is the subclass ViewHolder which simply
        //'holds the views' for us to show on each row
        public ViewHolder(View itemView) {
            super(itemView);

            //Finds the views from our row.xml
            appName = (TextView) itemView.findViewById(R.id.appName);
            packageName = (TextView) itemView.findViewById(R.id.packageName);
            appImg = (ImageView) itemView.findViewById(R.id.appImg);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick (View v) {
            int pos = getAdapterPosition();
            Context context = v.getContext();

            Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(filteredList.get(pos).getPackageName().toString());
            context.startActivity(launchIntent);
            Toast.makeText(v.getContext(), filteredList.get(pos).getLabel().toString(), Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onBindViewHolder(AppInfoAdapter.ViewHolder viewHolder, int i) {

        //Here we use the information in the list we created to define the views

        String appLabel = filteredList.get(i).getLabel().toString();
        String appPackage = filteredList.get(i).getPackageName().toString();
        Drawable appIcon = filteredList.get(i).getIcon();

        TextView textView = viewHolder.appName;
        textView.setText(appLabel);
        viewHolder.packageName.setText(appPackage);
        ImageView imageView = viewHolder.appImg;
        imageView.setImageDrawable(appIcon);
    }


    @Override
    public int getItemCount() {
        //This method needs to be overridden so that Androids knows how many items
        //will be making it into the list

        return filteredList.size();
    }


    @Override
    public AppInfoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //This is what adds the code we've written in here to our target view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.app_list_row, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    /**
     * Custom filter for app list
     * Filter content in app list according to the search text
     */
    private class AppInfoFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint!=null && constraint.length()>0) {
                ArrayList<AppInfo> tempList = new ArrayList<AppInfo>();

                // search content in friend list
                for (AppInfo appInfo : appsList) {
                    if (appInfo.getLabel().toString().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(appInfo);
                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = appsList.size();
                filterResults.values = appsList;
            }

            return filterResults;
        }

        /**
         * Notify about filtered list to ui
         * @param constraint text
         * @param results filtered result
         */
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredList = (ArrayList<AppInfo>) results.values;
            notifyDataSetChanged();
        }
    }
}
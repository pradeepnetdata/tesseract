package com.tesseract.android.model;

import android.graphics.drawable.Drawable;

public class AppInfo implements Comparable<AppInfo>{
    CharSequence label;

    public CharSequence getLabel() {
        return label;
    }

    public void setLabel(CharSequence label) {
        this.label = label;
    }

    public CharSequence getPackageName() {
        return packageName;
    }

    public void setPackageName(CharSequence packageName) {
        this.packageName = packageName;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    CharSequence packageName;
    Drawable icon;

    @Override
    public int compareTo(AppInfo appInfo) {
        if (getLabel().toString() == null || appInfo.getLabel().toString() == null) {
            return 0;
        }
        return getLabel().toString().compareTo(appInfo.getLabel().toString());
    }

}

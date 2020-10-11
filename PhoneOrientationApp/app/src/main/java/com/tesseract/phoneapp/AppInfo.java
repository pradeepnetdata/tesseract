package com.tesseract.phoneapp;

public class AppInfo {

    private static final AppInfo appInfo=null;

    private AppInfo(){}

    public static AppInfo getInstance(){
        if(appInfo ==null){
            return new AppInfo();
        }
        return appInfo;
    }
}

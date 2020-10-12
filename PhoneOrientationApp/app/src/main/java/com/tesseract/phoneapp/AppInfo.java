package com.tesseract.phoneapp;

public class AppInfo {

    private static final AppInfo appInfo=null;
    private float[] value;
    private AppInfo(){}

    public static AppInfo getInstance(){
        if(appInfo ==null){
            return new AppInfo();
        }
        return appInfo;
    }


    public float [] getRotationVectorValue(){
        return value;
    }
}

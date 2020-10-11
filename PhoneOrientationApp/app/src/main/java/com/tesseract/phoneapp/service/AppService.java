package com.tesseract.phoneapp.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.tesseract.phoneapp.IOrientationService;

public class AppService extends Service implements SensorEventListener{

    //private RemoteCallbackList<ICallback> mCallbacks = new RemoteCallbackList<ICallback>();
    private SensorManager mSensorManager;

    // Accelerometer and magnetometer sensors, as retrieved from the
    // sensor manager.
    private Sensor mSensorRotationvector;
    private Display mDisplay;
    // Current data from accelerometer & magnetometer.  The arrays hold values
    // for X, Y, and Z.
    private float[] mRotationData = new float[3];
    private IBinder binder;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate()");
        mSensorManager = (SensorManager) getSystemService(
                Context.SENSOR_SERVICE);
        mSensorRotationvector = mSensorManager.getDefaultSensor(
                Sensor.TYPE_ROTATION_VECTOR);


        // Get the display from the window manager (for rotation).
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        mDisplay = wm.getDefaultDisplay();
        registerSensorReceiver();
    }

    private void registerSensorReceiver(){
        if (mSensorRotationvector != null) {
            mSensorManager.registerListener((SensorEventListener) this, mSensorRotationvector,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
       // return new SimpServiceImp();

        return new IOrientationService.Stub() {

            public float [] getRotationVectorInfo()  {
               // Log.d(TAG, String.format("AdditionService.add(%d, %d)",mRotationData[0], mRotationData[1]));
                return mRotationData;
            }

        };
        //return  null;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int sensorType = sensorEvent.sensor.getType();

        // The sensorEvent object is reused across calls to onSensorChanged().
        // clone() gets a copy so the data doesn't change out from under us
        switch (sensorType) {
            case Sensor.TYPE_ROTATION_VECTOR:
                mRotationData = sensorEvent.values.clone();
                //float str =  mRotationData[0];
                 Log.d(TAG, String.format("AdditionService.add(%f, %f)",mRotationData[0], mRotationData[1]));

                //mTextSensorAzimuth.setText((int) mAccelerometerData[0]);
                break;

            default:
                return;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}

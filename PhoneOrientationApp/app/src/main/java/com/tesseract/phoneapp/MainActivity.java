/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tesseract.phoneapp;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tesseract.phoneapp.service.AppService;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "AIDLDemo";
   // IOrientationService service;
    AppServiceConnection connection;
    private float[] mAccelerometerData = new float[3];
    // System sensor manager instance.
 /*   private SensorManager mSensorManager;

    // Accelerometer and magnetometer sensors, as retrieved from the
    // sensor manager.
    private Sensor mSensorAccelerometer;
    private Sensor mSensorMagnetometer;

    // Current data from accelerometer & magnetometer.  The arrays hold values
    // for X, Y, and Z.
    private float[] mAccelerometerData = new float[3];
    private float[] mMagnetometerData = new float[3];*/

    // TextViews to display current sensor values.
    private TextView mTextSensorAzimuth;
    private TextView mTextSensorPitch;
    private TextView mTextSensorRoll;


    // System display. Need this for determining rotation.
    private Display mDisplay;
    private static final float VALUE_DRIFT = 0.05f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextSensorAzimuth = (TextView) findViewById(R.id.value_azimuth);
        mTextSensorPitch = (TextView) findViewById(R.id.value_pitch);
        mTextSensorRoll = (TextView) findViewById(R.id.value_roll);


        initService();
    }

    /**
     * Listeners for the sensors are registered in this callback so that
     * they can be unregistered in onStop().
     */
    @Override
    protected void onStart() {
        super.onStart();

    }


    @Override
    protected void onStop() {
        super.onStop();

    }

    /**
     * This class represents the actual service connection. It casts the bound
     * stub implementation of the service to the AIDL interface.
     */
    class AppServiceConnection implements ServiceConnection {

        public void onServiceConnected(ComponentName name, IBinder boundService) {
           // service = IOrientationService.Stub.asInterface((IBinder) boundService);
            Log.d(MainActivity.TAG, "onServiceConnected() connected");
            Toast.makeText(MainActivity.this, "Service connected", Toast.LENGTH_LONG)
                    .show();
            setRotationalInfo();
        }

        public void onServiceDisconnected(ComponentName name) {
           // service = null;
            Log.d(MainActivity.TAG, "onServiceDisconnected() disconnected");
            Toast.makeText(MainActivity.this, "Service connected", Toast.LENGTH_LONG)
                    .show();
        }
    }
    /** Binds this activity to the service. */
    private void initService() {
        connection = new AppServiceConnection();
        Intent i = new Intent(this, AppService.class);
        //i.setClassName("com.marakana", com.marakana.AdditionService.class.getName());
        boolean ret = bindService(i, connection, Context.BIND_AUTO_CREATE);
        Log.d(TAG, "initService() bound with " + ret);
    }

    /** Unbinds this activity from the service. */
    private void releaseService() {
        unbindService(connection);
        connection = null;
        Log.d(TAG, "releaseService() unbound.");
    }
    private void setRotationalInfo(){


        /*try {
            mAccelerometerData = service.getRotationVectorInfo();
        } catch (RemoteException e) {
            Log.d(MainActivity.TAG, "RemoteException :: "+e.toString());
        }*/
        float azimuth = mAccelerometerData[0];
        float pitch = mAccelerometerData[1];
        float roll = mAccelerometerData[2];

        // Fill in the string placeholders and set the textview text.
        mTextSensorAzimuth.setText(getResources().getString(
                R.string.value_format, azimuth));
        mTextSensorPitch.setText(getResources().getString(
                R.string.value_format, pitch));
        mTextSensorRoll.setText(getResources().getString(
                R.string.value_format, roll));
    }
    /** Called when the activity is about to be destroyed. */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseService();
    }
}
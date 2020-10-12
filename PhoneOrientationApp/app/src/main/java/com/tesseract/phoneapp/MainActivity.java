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

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.Display;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tesseract.phoneapp.service.AppService;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "AIDLDemo";
    private IOrientationService service;
    private AppServiceConnection connection;
    private float[] mAccelerometerData = new float[3];
    private TextView mTextSensorAzimuth;
    private TextView mTextSensorPitch;
    private TextView mTextSensorRoll;
    private Display mDisplay;
    private static final float VALUE_DRIFT = 0.05f;
    private IAidlCbListener mCallback;
    private MyCbListener myCbListener = new MyCbListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextSensorAzimuth = (TextView) findViewById(R.id.value_azimuth);
        mTextSensorPitch = (TextView) findViewById(R.id.value_pitch);
        mTextSensorRoll = (TextView) findViewById(R.id.value_roll);
        initService();
    }

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
            service = IOrientationService.Stub.asInterface((IBinder) boundService);
            Log.d(MainActivity.TAG, "onServiceConnected() connected");

            Toast.makeText(MainActivity.this, "Service connected", Toast.LENGTH_LONG)
                    .show();

            if(myCbListener == null){
                myCbListener = new MyCbListener();
            }
            try {
                service.registerCallback(myCbListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
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
    //Implement callback listener
    class MyCbListener extends IAidlCbListener.Stub{

        @Override
        public void valueChanged(float[] value) {
            Log.d(TAG, "showServerMsg: ");
            mAccelerometerData = value;
            setRotationalInfo();
        }
    }
}
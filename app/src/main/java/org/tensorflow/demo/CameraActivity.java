/*
 * Copyright 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*  This file has been modified by Nataniel Ruiz affiliated with Wall Lab
 *  at the Georgia Institute of Technology School of Interactive Computing
 */

package org.tensorflow.demo;

import android.Manifest;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

public class CameraActivity extends AppCompatActivity implements SensorEventListener {
  private static final int PERMISSIONS_REQUEST = 1;

  private static final String PERMISSION_CAMERA = Manifest.permission.CAMERA;
  private static final String PERMISSION_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;

  SensorManager mSensorManager;
  private Sensor accSensor,magnetSensor;
  float[] geoMagnetic, gravity;
  float pitch,azimut,roll;

  @Override
  protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    TTS.init(getApplicationContext());

    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    setContentView(R.layout.activity_camera);

    mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    accSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    magnetSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

    if (hasPermission()) {
      if (null == savedInstanceState) {
        setFragment();
      }
    } else {
      requestPermission();
    }

  }

  @Override
  public void onSensorChanged(SensorEvent event) {
    if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
      gravity = event.values.clone();
    Log.e("hance","onsensor");
    Log.e("gravity",""+gravity[0]);
    if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
      geoMagnetic = event.values.clone();
    Log.e("geomagnetic",""+gravity[0]);
    if (gravity != null && geoMagnetic != null) {
      float R[] = new float[9];
      float I[] = new float[9];
      boolean success = SensorManager.getRotationMatrix(R, I, gravity,geoMagnetic);
      if (success) {
                /* Orientation has azimuth, pitch and roll */
        float orientation[] = new float[3];
        //SensorManager.remapCoordinateSystem(R, 1, 3, orientation);
        SensorManager.getOrientation(R, orientation);
        azimut = 57.29578F * orientation[0];
        pitch = 57.29578F * orientation[1];
        roll = 57.29578F * orientation[2];
        Log.e("Angle",""+ (pitch));
        Log.d("a", "orientation values: " + azimut + " / " + pitch + " / " + roll);

        SharedPreferences.Editor editor = getSharedPreferences("hance", MODE_PRIVATE).edit();
        editor.putString("pitch",""+pitch);
        Log.e("camera",""+pitch);
        editor.apply();

      }
    }
  }

  protected void onResume() {
    super.onResume();
    mSensorManager.registerListener(this, accSensor,
            SensorManager.SENSOR_DELAY_NORMAL);
    mSensorManager.registerListener(this, magnetSensor,
            SensorManager.SENSOR_DELAY_NORMAL);
  }

  @Override
  public void onAccuracyChanged(Sensor sensor, int accuracy) {

  }

  @Override
  public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
    switch (requestCode) {
      case PERMISSIONS_REQUEST: {
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
          setFragment();
        } else {
          requestPermission();
        }
      }
    }
  }

  private boolean hasPermission() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      return checkSelfPermission(PERMISSION_CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(PERMISSION_STORAGE) == PackageManager.PERMISSION_GRANTED;
    } else {
      return true;
    }
  }

  private void requestPermission() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      if (shouldShowRequestPermissionRationale(PERMISSION_CAMERA) || shouldShowRequestPermissionRationale(PERMISSION_STORAGE)) {
        Toast.makeText(CameraActivity.this, "Camera AND storage permission are required", Toast.LENGTH_LONG).show();
      }
      requestPermissions(new String[] {PERMISSION_CAMERA, PERMISSION_STORAGE}, PERMISSIONS_REQUEST);
    }
  }

  private void setFragment() {
    getFragmentManager()
            .beginTransaction()
            .replace(R.id.container, CameraConnectionFragment.newInstance())
            .commit();
  }


}

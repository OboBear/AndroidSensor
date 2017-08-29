package com.example.obo.androidsensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    SensorManager sensorManager;

    RoundImageSurfaceView roundImageSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);
        roundImageSurfaceView = (RoundImageSurfaceView) findViewById(R.id.round_surfaceview);
        // 陀螺仪传感器，负责水平旋转
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
//        sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME);
        // 重力传感器，负责上下旋转
        sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_UI);
    }

    SensorEventListener sensorEventListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){  //
                Log.i(TAG,"onSensorChanged");
                float X_lateral = sensorEvent.values[0];
                float Y_longitudinal = sensorEvent.values[1];
                float Z_vertical = sensorEvent.values[2];
                Log.i(TAG,"\n X "+X_lateral);
                Log.i(TAG,"\n Y "+Y_longitudinal);
                Log.i(TAG,"\n Z "+Z_vertical);
            } else if (sensorEvent.sensor.getType() == Sensor.TYPE_ORIENTATION) { // compass
                float X_lateral = sensorEvent.values[0];
                float Y_longitudinal = sensorEvent.values[1];
                float Z_vertical = sensorEvent.values[2];
                Log.i(TAG,"\n send heading "+X_lateral);
                Log.i(TAG,"\n send pitch "+Y_longitudinal);
                Log.i(TAG,"\n send roll "+Z_vertical);
            } else if (sensorEvent.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
                float X_lateral = sensorEvent.values[0];
                float Y_longitudinal = sensorEvent.values[1];
                float Z_vertical = sensorEvent.values[2];
                Log.i(TAG,"scop X "+X_lateral);
                Log.i(TAG,"scop Y "+Y_longitudinal);
                Log.i(TAG,"scop Z "+Z_vertical);
                Log.i(TAG, " ");
//                if (++a == 5) {
//                    a = 0;
                    roundImageSurfaceView.setSensorValue(X_lateral, Y_longitudinal, Z_vertical);
//                }
            }
        }

        int a = 0;

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
            Log.i(TAG, "onAccuracyChanged");
        }
    };
}

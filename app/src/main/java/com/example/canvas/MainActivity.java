package com.example.canvas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import petrov.kristiyan.colorpicker.ColorPicker;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    ConstraintLayout mLayout;
    Canvas canvasFragment;
    Palette paletteFragment;
    static private int backgroundColor;
    static private int strokeColor;
    private SensorManager sensorManager;
    private Sensor lightSensor;
    private Sensor accSensor;
    private static final int THRESHOLD = 800;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLayout = findViewById(R.id.mLayout);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        //CANVAS
        if(savedInstanceState != null){
            canvasFragment = (Canvas) getSupportFragmentManager().getFragment(savedInstanceState,"canvasFragment" );
            Log.d("Nu","Nao esta null");
        }else {
            Log.d("Nu","Esta null");
            canvasFragment = (Canvas) getSupportFragmentManager().findFragmentById(R.id.canvas);
        }
        //PALETTE
        paletteFragment = (Palette) getSupportFragmentManager().findFragmentById(R.id.palette);

        if(paletteFragment != null){
            if(backgroundColor != 0){
                canvasFragment.changeBackgroundColor(backgroundColor);

            }

            Button paletteColorPicker = findViewById(R.id.strokeColor_palette);
            if (paletteColorPicker != null) {
                paletteColorPicker.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final ColorPicker colorPicker = new ColorPicker(MainActivity.this);
                        colorPicker.setOnFastChooseColorListener(new ColorPicker.OnFastChooseColorListener() {
                            @Override
                            public void setOnFastChooseColorListener(int position, int color) {
                                canvasFragment.changeStrokeColor(color);
                                strokeColor = color;
                            }

                            @Override
                            public void onCancel() {
                                colorPicker.dismissDialog();
                            }
                        });
                        colorPicker.setDefaultColorButton(strokeColor);
                        colorPicker.show();

                    }
                });
            }

        }




    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.about){
            Intent aboutIntent = new Intent(MainActivity.this,
                    AboutActivity.class);
            startActivity(aboutIntent);
            return false;
        }else if(id == R.id.settings){
            Intent settingsIntent = new Intent(MainActivity.this,
                    SettingsActivity.class);
            settingsIntent.putExtra("currentColor",backgroundColor);
            startActivity(settingsIntent);
            return false;
        }else if(id == R.id.palette){
            openPalette();
        }

        return super.onOptionsItemSelected(item);
    }

    public void openPalette(){
        ColorPicker colorPicker = new ColorPicker(this);
        colorPicker.show();
        colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
            @Override
            public void onChooseColor(int position,int color) {
                canvasFragment.changeBackgroundColor(color);

            }

            @Override
            public void onCancel(){
                colorPicker.dismissDialog();
                //Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                //startActivity(intent);
            }
        });

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "canvasFragment", canvasFragment);
    }


    private long lastUpdate;
    private float lastX = -1.0f;
    private float lastY = -1.0f;
    private float lastZ = -1.0f;

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            long now = System.currentTimeMillis();
            if ((now- lastUpdate) > 100) {
                Log.d("shake", "onSensorChanged: shakinggg");
                long diffTime = (now - lastUpdate);
                lastUpdate = now;
                float x = sensorEvent.values[0];
                float y = sensorEvent.values[1];
                float z = sensorEvent.values[2];

                //alignment on axis
                float absX = Math.abs(x);
                float absY = Math.abs(y);
                float absZ = Math.abs(z);

                if ((absX > 0) && (absX < 1) && (absY > 0) && (absY < 1) && (absZ > 9) && (absZ < 11)) {
                    backgroundColor = Color.parseColor("#ff7f7f");
                    canvasFragment.changeBackgroundColor(backgroundColor);
                } else if ((absX > 9) && (absX < 11) && (absY > 0) && (absY < 1) && (absZ > 0) && (absZ < 1)) {
                    backgroundColor = Color.parseColor("#add8e6");
                    canvasFragment.changeBackgroundColor(backgroundColor);
                } else if ((absX > 0) && (absX < 1) && (absY > 9) && (absY < 11) && (absZ > 0) && (absZ < 1)) {
                    backgroundColor = Color.parseColor("#90ee90");
                    canvasFragment.changeBackgroundColor(backgroundColor);
                }


                float speed = Math.abs(x + y + z - lastX - lastY - lastZ) / diffTime * 10000;
                if(speed > THRESHOLD){
                    canvasFragment.clear();
                    canvasFragment.changeBackgroundColor(0);
                }
                Log.d("speed", String.valueOf(speed));
                lastX = x;
                lastY = y;
                lastZ = z;
            }
        }else if(sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT){
            float brightValue = sensorEvent.values[0];
            Log.d("bright", String.valueOf(brightValue));
            Log.d("BR", "onSensorChanged: "+brightValue);
            if(brightValue <= 7){
                canvasFragment.changeCanvasBrightness(5);
                //Log.d("HLO", "onSensorChanged: HLO");
            }else if (brightValue > 7 ){
                canvasFragment.changeCanvasBrightness(60);
            }

        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener((SensorEventListener) this,lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener((SensorEventListener) this, accSensor, sensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}
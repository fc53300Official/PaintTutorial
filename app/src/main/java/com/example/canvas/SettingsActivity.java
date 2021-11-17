package com.example.canvas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import petrov.kristiyan.colorpicker.ColorPicker;

public class SettingsActivity extends AppCompatActivity {

    private Object AboutActivity;
    private int currentColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle e = getIntent().getExtras();
        currentColor = (int) e.get("currentColor");



        ColorPicker colorPicker = new ColorPicker(SettingsActivity.this);
        colorPicker.show();
        colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
            @Override
            public void onChooseColor(int position,int color) {
                Intent c = new Intent();
                c.putExtra("currentColor", color);
                //Log.d("color", String.valueOf(color));
                setResult(RESULT_OK,c);
                finish();

            }

            @Override
            public void onCancel(){
                colorPicker.dismissDialog();
            }

        });



        setContentView(R.layout.activity_settings);
    }
}
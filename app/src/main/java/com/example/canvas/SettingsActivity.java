package com.example.canvas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import petrov.kristiyan.colorpicker.ColorPicker;

public class SettingsActivity extends AppCompatActivity {

    private Object AboutActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        ColorPicker colorPicker = new ColorPicker(this);
        colorPicker.show();
        colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
            @Override
            public void onChooseColor(int position,int color) {
                /*View main = findViewById(R.id.main).getRootView();
                main.setBackgroundColor(color);
                finish();*/

                Intent c = new Intent();
                c.putExtra("color", color);
                finish();

            }

            @Override
            public void onCancel(){
                //Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                //startActivity(intent);
            }
        });



        setContentView(R.layout.activity_settings);
    }
}
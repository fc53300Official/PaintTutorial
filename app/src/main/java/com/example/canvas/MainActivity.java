package com.example.canvas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import petrov.kristiyan.colorpicker.ColorPicker;

public class MainActivity extends AppCompatActivity {
    ConstraintLayout mLayout;
    Canvas canvasFragment;
    Palette paletteFragment;
    static private int backgroundColor;
    static private int strokeColor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLayout = findViewById(R.id.mLayout);

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
                                Log.d("canvas", canvasFragment.toString());
                                canvasFragment.changeStrokeColor(color);
                                strokeColor = color;
                            }

                            @Override
                            public void onCancel() {
                                colorPicker.dismissDialog();
                            }
                        });
                        colorPicker.setDefaultColorButton(strokeColor);
                        colorPicker.setColumns(5);
                        colorPicker.setRoundColorButton(true);
                        colorPicker.show();

                    }
                });
            }

        }




    }
}
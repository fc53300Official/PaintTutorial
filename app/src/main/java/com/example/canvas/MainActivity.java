package com.example.canvas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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


    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "canvasFragment", canvasFragment);
    }
}
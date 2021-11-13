package com.example.canvas;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import petrov.kristiyan.colorpicker.ColorPicker;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Palette#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Palette extends Fragment {

    private Button StrokeColorBut;


    public Palette() {
        // Required empty public constructor
    }

    public static Palette newInstance(String param1, String param2) {
        Palette fragment = new Palette();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrokeColorBut = (Button) getActivity().findViewById(R.id.strokeColor_palette);
        /**StrokeColorBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorPicker colorPicker = new ColorPicker(getActivity());
                colorPicker.show();
                colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                    @Override
                    public void onChooseColor(int position,int color) {



                    }

                    @Override
                    public void onCancel(){

                    }
                });
            }
        });
        /**ColorPicker colorPicker = new ColorPicker(getActivity());
        colorPicker.show();
        colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
            @Override
            public void onChooseColor(int position,int color) {



            }

            @Override
            public void onCancel(){

            }
        });**/
    }


    public Button strokePaletteButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_palette, container, false);
        strokePaletteButton = view.findViewById(R.id.strokeColor_palette);
        return inflater.inflate(R.layout.fragment_palette, container, false);
    }

    public Button getStrokePaletteButton(){
        return strokePaletteButton;
    }
}
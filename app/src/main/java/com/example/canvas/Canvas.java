package com.example.canvas;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import java.util.ArrayList;

public class Canvas extends Fragment {
    public Canvas() {
        // Required empty public constructor
    }

    public static Canvas newInstance(String param1, String param2) {
        Canvas fragment = new Canvas();
        return fragment;
    }
    PaintCanvas paintCanvas;
    private int canvasBackgroundColor;
    private int currentStrokeColor;
    private ArrayList<Stroke> strokeArrayList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Intent intent = getActivity().getIntent();

        GestureListener mGestureListener = new GestureListener();
        mGestureListener.setFragment(this);
        GestureDetector mGestureDetector = new GestureDetector(getActivity().getApplicationContext(), mGestureListener);
        mGestureDetector.setIsLongpressEnabled(true);
        mGestureDetector.setOnDoubleTapListener(mGestureListener);


        if (savedInstanceState != null){
            currentStrokeColor = savedInstanceState.getInt("currentStrokeColor");
            canvasBackgroundColor = savedInstanceState.getInt("canvasBackgroundColor");
            strokeArrayList = (ArrayList<Stroke>) savedInstanceState.getSerializable("strokeArrayList");
        } else {
            currentStrokeColor = Color.BLACK;
            canvasBackgroundColor = Color.WHITE;
            strokeArrayList = new ArrayList<>();
        }

        paintCanvas = new PaintCanvas(getActivity().getApplicationContext(), null, mGestureDetector,
                currentStrokeColor, canvasBackgroundColor, strokeArrayList);


        mGestureListener.setCanvas(paintCanvas);
        getActivity().setContentView(paintCanvas);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_canvas, container, false);
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("canvasBackgroundColor", canvasBackgroundColor);
        outState.putInt("currentStrokeColor", currentStrokeColor);
        outState.putSerializable("strokeArrayList", strokeArrayList);

    }

    public void changeStrokeColor(int color){
        currentStrokeColor = paintCanvas.changeStroke(color);
        strokeArrayList = paintCanvas.getStrokeArray();
    }

    public void changeBackgroundColor(int color){
        canvasBackgroundColor = paintCanvas.changeBackground(color);
    }

    public void clear(){paintCanvas.erase();}

    public void changeCanvasBrightness(int brightness ){
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.screenBrightness = brightness == 0? -1/100.0f : brightness/100.0f;
        getActivity().getWindow().setAttributes(lp);
    }


}
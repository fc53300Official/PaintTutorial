package com.example.canvas;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Canvas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Canvas extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Canvas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Canvas.
     */
    // TODO: Rename and change types and number of parameters
    public static Canvas newInstance(String param1, String param2) {
        Canvas fragment = new Canvas();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    PaintCanvas paintCanvas;
    private int canvasBackgroundColor;
    private int currentStrokeColor;
    private ArrayList<Stroke> strokeArrayList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        //Intent intent = getActivity().getIntent();

        GestureListener mGestureListener = new GestureListener();
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
}
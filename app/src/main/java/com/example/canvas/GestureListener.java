package com.example.canvas;

import android.graphics.Color;
import android.view.GestureDetector;
import android.view.MotionEvent;

import java.util.Random;

public class GestureListener extends GestureDetector.SimpleOnGestureListener implements GestureDetector.OnDoubleTapListener {

    private PaintCanvas canvas;
    private Canvas canvasFragment;

    void setCanvas(PaintCanvas canvas) {
        this.canvas = canvas;
    }
    void setFragment(Canvas canvasF){this.canvasFragment = canvasF;}
    ////////SimpleOnGestureListener
    @Override
    public void onLongPress(MotionEvent motionEvent) {
        Random rnd = new Random();
        int color = Color.argb(255,rnd.nextInt(256),rnd.nextInt(256),rnd.nextInt(256));
        canvasFragment.changeBackgroundColor(color);
    }

    /////////OnDoubleTapListener
    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        canvas.erase();
        return false;
    }

}
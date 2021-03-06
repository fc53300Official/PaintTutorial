package com.example.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class PaintCanvas extends View implements View.OnTouchListener{

    private Paint paint = new Paint();
    private Path path = new Path();
    private int backGroundColor;
    private GestureDetector mGestureDetector;

    private ArrayList<Stroke> paths;
    private int currentStrokeColor;
    private int currentStrokeWidth = 10;
    private int backgroundColor;

    public PaintCanvas(Context context, AttributeSet attrs){
        super(context, attrs);
        setOnTouchListener(this);
        setBackgroundColor(backgroundColor);
        initPaint();
    }

    public PaintCanvas(Context context, AttributeSet attrs, GestureDetector mGestureDetector,
                       int currentStrokeColor, int backgroundColor, ArrayList<Stroke> paths){
        super(context, attrs);
        setOnTouchListener(this);
        setBackgroundColor(backgroundColor);
        this.mGestureDetector = mGestureDetector;
        this.currentStrokeColor = currentStrokeColor;
        this.backgroundColor = backgroundColor;
        this.paths = paths;
        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(path, paint);// draws the path with the paint
        for(Stroke s:paths){
            paint.setColor(s.color);
            paint.setStrokeWidth(s.strokeWidth);
            canvas.drawPath(s.path,paint);
        }
    }

    @Override
    public boolean performClick(){
        return super.performClick();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d("TO", "onTouch: h");
        mGestureDetector.onTouchEvent(event);
        return false; // let the event go to the rest of the listeners
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path = new Path();
                Stroke stroke = new Stroke(currentStrokeColor,currentStrokeWidth,path);
                paths.add(stroke);
                path.moveTo(eventX, eventY);// updates the path initial point
                return true;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(eventX, eventY);// makes a line to the point each time this event is fired
                break;
            case MotionEvent.ACTION_UP:// when you lift your finger
                performClick();
                break;
            default:
                return false;
        }

        // Schedules a repaint.
        invalidate();
        return true;
    }

    public void changeBackground(){
        Random r = new Random();
        backGroundColor = Color.rgb(r.nextInt(256), r.nextInt(256), r.nextInt(256));
        setBackgroundColor(backGroundColor);
        
    }

    public int changeBackground(int color){
        setBackgroundColor(color);
        return color;
    }
    public int changeStroke(int color){
        currentStrokeColor = color;
        paint.setColor(currentStrokeColor);
        return currentStrokeColor;
    }

    //public void erase(){
        //paint.setColor(backGroundColor);
    //}


    public void erase(){
        paths.clear();

    }

    private void initPaint(){
        paint.setAntiAlias(true);
        paint.setStrokeWidth(20f);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        changeStroke(currentStrokeColor);
    }

    public void changeColor(int color){
        this.paint.setColor(color);
    }

    public ArrayList<Stroke> getStrokeArray(){
        return paths;
    }
}

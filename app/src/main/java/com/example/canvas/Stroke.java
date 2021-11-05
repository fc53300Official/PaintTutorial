package com.example.canvas;

import android.graphics.Path;

import java.io.Serializable;

public class Stroke implements Serializable {
    public int color;
    public int strokeWidth;
    public Path path;

    public Stroke ( int color, int strokeWidth, Path path){
        this.color = color;
        this.strokeWidth = strokeWidth;
        this.path = path;
    }

}

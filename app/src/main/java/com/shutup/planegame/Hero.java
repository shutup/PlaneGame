package com.shutup.planegame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import java.util.Random;

/**
 * Created by shutup on 16/6/24.
 */
public class Hero implements CommonAction {
    private static final String TAG = "Hero";

    private int x = 0;
    private int y = 0;
    private int radius = 0;
    private Paint mPaint = null;
    private int mColor = Color.WHITE;
    private int speed = 0;
    private double degree = 0;

    private int screenW = 0;
    private int screenH = 0;

    private int currentType = Constants.STAY;

    public Hero(int color, int radius, int x, int y, int screenW, int screenH) {
        mColor = color;
        mPaint = new Paint();
        mPaint.setColor(mColor);
        mPaint.setAntiAlias(true);
        this.radius = radius;
        this.y = y;
        this.x = x;
        Random random = new Random(System.currentTimeMillis());
        this.speed = random.nextInt(10) + 10;
        this.screenW = screenW;
        this.screenH = screenH;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public Paint getPaint() {
        return mPaint;
    }

    public void setPaint(Paint paint) {
        mPaint = paint;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        mColor = color;
    }

    @Override
    public void logic() {
        if (currentType == Constants.STAY) {

        } else if (currentType == Constants.UP_LEFT) {
            up_left();
        } else if (currentType == Constants.UP_RIGHT) {
            up_right();
        } else if (currentType == Constants.DOWN_LEFT) {
            down_left();
        } else if (currentType == Constants.DOWN_RIGHT) {
            down_right();
        }
        checkBorder();
    }

    private void checkBorder(){
        if (x > screenW - radius ){
            x= screenW - radius;
        }else if (x <radius){
            x = radius;
        }

        if (y > screenH- radius ){
            y = screenH - radius;
        }else if (y <radius) {
            y = radius;
        }
    }

    private void up_left() {
        x -= speed * Math.cos(degree);
        y -= speed * Math.sin(degree);
    }

    private void up_right() {
        x += speed * Math.cos(degree);
        y -= speed * Math.sin(degree);
    }

    private void down_left() {
        x -= speed * Math.cos(degree);
        y += speed * Math.sin(degree);
    }

    private void down_right() {
        x += speed * Math.cos(degree);
        y += speed * Math.sin(degree);
    }


    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(x, y, radius, mPaint);
    }

    public void handleTouchEvent(MotionEvent motionEvent) {
        int type = motionEvent.getAction();
        if (type == MotionEvent.ACTION_UP) {
            currentType = Constants.STAY;
        } else if (type == MotionEvent.ACTION_DOWN) {
            calcDegree(motionEvent);
        } else if (type == MotionEvent.ACTION_MOVE) {
            calcDegree(motionEvent);
        }
    }

    public void calcDegree(MotionEvent motionEvent) {
        double x = motionEvent.getRawX();
        double y = motionEvent.getRawY();
        double distanceX = Math.abs(x - this.x);
        double distanceY = Math.abs(y - this.y);
        degree = Math.atan(distanceY / distanceX);

        if (this.x < x && this.y > y) {
            currentType = Constants.UP_RIGHT;
        } else if (this.x > x && this.y > y) {
            currentType = Constants.UP_LEFT;
        } else if (this.x < x && this.y < y) {
            currentType = Constants.DOWN_RIGHT;
        } else if (this.x > x && this.y < y) {
            currentType = Constants.DOWN_LEFT;
        }
    }

}

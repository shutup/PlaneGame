package com.shutup.planegame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.Random;

/**
 * Created by shutup on 16/6/23.
 */
public class Bubble implements CommonAction, Runnable {
    private static final String TAG = "Bubble";
    private int x = 0;
    private int y = 0;
    private int radius = 0;
    private int speed = 0;
    private double degree = 0;
    private Paint mPaint = null;
    private int mColor = Color.WHITE;
    private int screenW = 0;
    private int screenH = 0;


    private String name;

    public void setDirection(int direction) {
        this.direction = direction;
    }

    private int direction = Constants.RUN;

    private Thread mThread = null;
    private boolean isRun = false;

    public Bubble(int color, int radius, int x, int y, int screenW, int screenH) {
        mColor = color;
        mPaint = new Paint();
        mPaint.setColor(mColor);
        mPaint.setAntiAlias(true);
        this.radius = radius;
        this.y = y;
        this.x = x;
        Random random = new Random(System.currentTimeMillis());
        this.speed = random.nextInt(1) + 3;
        this.degree = (Math.random() * Math.PI * 2);
        this.screenW = screenW;
        this.screenH = screenH;

        mThread = new Thread(this);
    }

    public void setName(String name) {
        this.name = name;
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
        if (direction == Constants.RUN) {
            normalRun();
//            Log.d(TAG, name+"-logic: run");
        } else if (direction == Constants.UP_LEFT) {
            up_left();
//            Log.d(TAG, name+"-logic: up_left");
        } else if (direction == Constants.UP_RIGHT) {
            up_right();
//            Log.d(TAG, name+"-logic: up_right");
        } else if (direction == Constants.DOWN_LEFT) {
            down_left();
//            Log.d(TAG, name+"-logic: down_left");
        } else if (direction == Constants.DOWN_RIGHT) {
            down_right();
//            Log.d(TAG, name+"-logic: down_right");
        }
    }

    public void normalRun() {
        x += speed * Math.cos(degree);
        y += speed * Math.sin(degree);
        if (x > screenW - radius) {
            x = screenW - radius;
            degree = Math.PI - degree;
        } else if (x < radius) {
            x = radius;
            degree = Math.PI - degree;
        }

        if (y > screenH - radius) {
            y = screenH - radius;
            degree = -degree;
        } else if (y < radius) {
            y = radius;
            degree = -degree;
        }
    }

    public void reverseRun() {
        x -= speed * Math.cos(degree);
        y -= speed * Math.sin(degree);
        if (x > screenW - radius) {
            x = screenW - radius;
            degree = Math.PI - degree;
        } else if (x < radius) {
            x = radius;
            degree = Math.PI - degree;
        }

        if (y > screenH - radius) {
            y = screenH - radius;
            degree = -degree;
        } else if (y < radius) {
            y = radius;
            degree = -degree;
        }
    }


    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(x, y, radius, mPaint);
//        canvas.drawText(name,x,y,new Paint(Color.BLACK));
    }

    @Override
    public void run() {
        while (isRun) {
            logic();

            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void start() {
        isRun = true;
        mThread.start();
    }

    private void check() {
        if (x > screenW - radius) {
            x = screenW - radius;
            degree = Math.PI - degree;
            direction = Constants.RUN;
        } else if (x < radius) {
            x = radius;
            degree = Math.PI - degree;
            direction = Constants.RUN;
        }

        if (y > screenH - radius) {
            y = screenH - radius;
            degree = -degree;
            direction = Constants.RUN;
        } else if (y < radius) {
            y = radius;
            degree = -degree;
            direction = Constants.RUN;
        }
    }

    private void up_left() {
        x -= speed * Math.cos(degree);
        y -= speed * Math.sin(degree);
        check();
    }

    private void up_right() {
        x += speed * Math.cos(degree);
        y -= speed * Math.sin(degree);
        check();
    }

    private void down_left() {
        x -= speed * Math.cos(degree);
        y += speed * Math.sin(degree);
        check();
    }

    private void down_right() {
        x += speed * Math.cos(degree);
        y += speed * Math.sin(degree);
        check();
    }

    @Override
    public String toString() {
        return "Bubble is At {" + x + "," + y + "},radius is" + radius;
    }
}

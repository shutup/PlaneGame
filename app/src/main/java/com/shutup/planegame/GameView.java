package com.shutup.planegame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by shutup on 16/6/23.
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback , Runnable{
    private Thread mThread = null;
    private SurfaceHolder mSurfaceHolder = null;
    private int ScreenW = 0;
    private int ScreenH = 0;
    private boolean isRun = false;
    private Canvas mCanvas = null;
    private Paint mPaint = null;

    private List<CommonAction> items = null;
    private Hero mHero = null;

    private Context mContext = null;
    private MyApplication mMyApplication = null;
    private List<Bubble> mBubbles = null;
    private List<Hero> mHeros = null;

    public GameView(Context context) {
        super(context);
        this.mContext = context;
        mThread = new Thread(this);
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        this.setKeepScreenOn(true);
        items = new ArrayList<>();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        ScreenW = this.getWidth();// 备注2
        ScreenH = this.getHeight();
        initPlayers();
        isRun = true;
        mThread.start();
    }

    private void initPlayers() {
        mBubbles = new ArrayList<>();
        mHeros = new ArrayList<>();

        Random r = new Random(System.currentTimeMillis());
        for(int i =0;i<2;i++){
//            Bubble b = new Bubble(Color.rgb(r.nextInt(255)+1,r.nextInt(255)+1,r.nextInt(255)+1),r.nextInt(50)+20,r.nextInt(ScreenW)+1,r.nextInt(ScreenH)+1,ScreenW,ScreenH);
            Bubble b = new Bubble(Color.rgb(r.nextInt(255)+1,r.nextInt(255)+1,r.nextInt(255)+1),r.nextInt(50)+20,ScreenW/2,ScreenH/2,ScreenW,ScreenH);
            b.setName("tag:"+(i+1));
            items.add(b);
            mBubbles.add(b);
        }
        mHero = new Hero(Color.BLACK,20,ScreenW/2,ScreenH/2+100,ScreenW,ScreenH);
        items.add(mHero);
        mHeros.add(mHero);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isRun = false;
    }

    @Override
    public void run() {
        while (isRun) {
            logic();
            draw();
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mHero.handleTouchEvent(event);
        return true;
    }

    private void logic() {
        mHero.logic();
        for (Bubble b :mBubbles
                ) {
            for (Bubble b1: mBubbles
                 ) {
                if (b != b1){
                    Utils.isColl(b,b1);
                }
            }
        }
        for (Bubble b:mBubbles
             ) {
            b.logic();
        }
        for (Bubble b : mBubbles){
            Utils.isColl(mHero, b);
        }
    }

    private void draw() {
        mCanvas = mSurfaceHolder.lockCanvas();

        if (mCanvas!=null){
            mCanvas.drawColor(Color.WHITE);
            for (CommonAction c:items
                 ) {
                c.draw(mCanvas);
            }
            mSurfaceHolder.unlockCanvasAndPost(mCanvas);
        }
    }
}

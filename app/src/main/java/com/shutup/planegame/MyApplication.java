package com.shutup.planegame;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shutup on 16/6/25.
 */
public class MyApplication extends Application {
    private List<Bubble> mBubbles;

    public List<Hero> getHeros() {
        return mHeros;
    }

    public List<Bubble> getBubbles() {
        return mBubbles;
    }

    private List<Hero> mHeros;

    public MyApplication() {
        mHeros = new ArrayList<>();
        mBubbles = new ArrayList<>();
    }
}

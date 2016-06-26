package com.shutup.planegame;

import android.util.Log;

/**
 * Created by shutup on 16/6/25.
 */
public class Utils {
    private static final String TAG = "Utils";

    public static void isColl(Hero hero, Bubble bubble){
        int centerRealDistance = (int) (Math.pow(hero.getX()-bubble.getX(),2) + Math.pow(hero.getY()-bubble.getY(),2));
        int centerDistance = (int) Math.pow( hero.getRadius() + bubble.getRadius(),2);
        if (centerRealDistance < centerDistance) {
            int currentType = Constants.RUN;
            if (hero.getX() < bubble.getX() && hero.getY() > bubble.getX()) {
                currentType = Constants.UP_RIGHT;
            } else if (hero.getX() > bubble.getX() && hero.getY() > bubble.getY()) {
                currentType = Constants.UP_LEFT;
            } else if (hero.getX() < bubble.getX() && hero.getY() < bubble.getY()) {
                currentType = Constants.DOWN_RIGHT;
            } else if (hero.getX() > bubble.getX() && hero.getY() < bubble.getY()) {
                currentType = Constants.DOWN_LEFT;
            }
            bubble.setDirection(currentType);
        }else {
        }
    }

    public static void isColl(Bubble bubble1, Bubble bubble2){
        int centerRealDistance = (int) (Math.pow(bubble1.getX()-bubble2.getX(),2) + Math.pow(bubble1.getY()-bubble2.getY(),2));
        int centerDistance = (int) Math.pow(bubble1.getRadius() + bubble2.getRadius(),2);
        if (centerRealDistance  < centerDistance) {
            int currentType = Constants.RUN;
            if (bubble1.getX() < bubble2.getX() && bubble1.getY() > bubble2.getX()) {
                currentType = Constants.UP_RIGHT;
            } else if (bubble1.getX() > bubble2.getX() && bubble1.getY() > bubble2.getY()) {
                currentType = Constants.UP_LEFT;
            } else if (bubble1.getX() < bubble2.getX() && bubble1.getY() < bubble2.getY()) {
                currentType = Constants.DOWN_RIGHT;
            } else if (bubble1.getX() > bubble2.getX() && bubble1.getY() < bubble2.getY()) {
                currentType = Constants.DOWN_LEFT;
            }
            bubble2.setDirection(currentType);
        }else {
        }
    }
}

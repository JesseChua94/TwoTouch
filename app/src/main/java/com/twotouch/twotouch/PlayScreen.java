package com.twotouch.twotouch;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.os.Handler;

/**
 * Created by Jesse on 2015-03-27.
 *
 */
public class PlayScreen extends Activity implements View.OnTouchListener {
    public int pOne;
    public int pTwo;
    Handler handler;
    int half;
    float y;
    private static final int invalid = -1;
    private int activePointer = invalid;
    int swipePointer;
    int ScreenNum = MainActivity.ScreenNum;
    View decorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        decorView = getWindow().getDecorView();
        MainActivity mainActivity = new MainActivity();
        mainActivity.stickyImmersion(decorView);

        //Changes screen depending on randomized ScreenNum.
        super.onCreate(savedInstanceState);
        if (ScreenNum == 1) {
            setContentView(R.layout.play_screen_one);
        } else if (ScreenNum == 2) {
            setContentView(R.layout.play_screen_two);
        } else if (ScreenNum == 3) {
            setContentView(R.layout.play_screen_three);
        } else if (ScreenNum == 4) {
            setContentView(R.layout.play_screen_swipe);
        }
        half = getHalf();
        decorView.setOnTouchListener(this);
    }

    //Handles touches and swipes
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                //This deals with swipes only. Get starting position of pointer
                activePointer = event.getActionIndex();
                try {
                    swipePointer = event.getPointerId(activePointer);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                y = event.getY();

                //Determines which side the pointer is on and adds to counter accordingly
                float xOn = event.getX();
                if (xOn > half) {
                    pOne++;
                } else
                    pTwo++;
                //delayTouch is called after each pointer to start groupings
                delayTouch();
                break;
            //Deals with all pointers after the primary pointer
            case MotionEvent.ACTION_POINTER_DOWN:
                activePointer = event.getActionIndex();
                float xOn1 = event.getX();
                if (xOn1 > half) {
                    pOne++;
                } else
                    pTwo++;
                delayTouch();
                break;
            case MotionEvent.ACTION_UP:
                //Deals with swipes only. Finds any difference in the first and last pointer
                //position and adds points accordingly if passed the threshold.
                int newYpointer = event.findPointerIndex(swipePointer);
                float newY = 0;
                try {
                    newY = event.getY(newYpointer);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                float x = event.getX();
                float diff = Math.abs(newY - y);
                if (diff > 500) {
                    if (x > half) {
                        pOne = pOne + 4;
                    } else
                        pTwo = pTwo + 4;

                    winner();
                }


                if (activePointer != invalid) {
                    float xOff = event.getX();
                    if (xOff > half) {
                        pOne--;
                    } else
                        pTwo--;
                    //Makes a current pointer invalid when raised so it is not counted again
                    activePointer = invalid;
                }
                delayTouch();
                break;
            case MotionEvent.ACTION_POINTER_UP:
                if (activePointer != invalid) {
                    float xOff = event.getX();
                    if (xOff > half) {
                        pOne--;
                    } else
                        pTwo--;
                    activePointer = invalid;
                }
                delayTouch();
                break;
        }
        return true;
    }
    // Splits screen in half to determine coordinates of player one and two
    public int getHalf() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int maxX = size.x;
        return (maxX / 2);
    }
    //Groups touches via a short delay to count pointers
    public void delayTouch() {
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                winner();
            }
        }, 15 );
    }
    //Determines who the winner is after each delay by counting pointers in groupings
    //Switches to appropriate winning screen.
    public void winner(){
        Intent intent;
        if (pOne == ScreenNum) {
            MainActivity.win = 0;
            MainActivity.scoreOne++;
            intent = new Intent(PlayScreen.this, TransitionScreen.class);
            startActivity(intent);
        } else if (pTwo == ScreenNum) {
            MainActivity.win = 1;
            MainActivity.scoreTwo++;
            intent = new Intent(PlayScreen.this, TransitionScreen.class);
            startActivity(intent);
        }
        //Reset score to 0 in case no one won so previous touches to not add to next score
        // grouping.
        pOne = 0;
        pTwo = 0;

    }
}
package com.twotouch.twotouch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class TransitionScreen extends Activity {
    int randScreen;
    Handler handler;
    Random rand = new Random();
    Intent intent;
    int scoreOne = MainActivity.scoreOne;
    int scoreTwo = MainActivity.scoreTwo;

    protected void onCreate(Bundle savedInstanceState) {
        View decorView = getWindow().getDecorView();
        MainActivity mainActivity = new MainActivity();
        mainActivity.stickyImmersion(decorView);

        super.onCreate(savedInstanceState);
        if (MainActivity.win == 0) {
             setContentView(R.layout.player_one);
        } else
            setContentView(R.layout.player_two);

        TextView updateOne = (TextView) findViewById(R.id.pOne);
        TextView updateTwo = (TextView) findViewById(R.id.pTwo);

        updateTwo.setText("" + MainActivity.scoreOne + "");
        updateOne.setText("" + MainActivity.scoreTwo + "");

        if (scoreOne == 10 || scoreTwo == 10) {
            intent = new Intent(TransitionScreen.this, WinScreen.class);
            startActivity(intent);
        } else {
            delayChange();
            reactionBuffer();
        }
    }

    @Override
    public void onStop(){
        super.onStop();
        finish();
    }

    public void delayChange() {
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                changeToReady();
            }
        }, 800);
    }

    public void reactionBuffer() {
        handler = new Handler();
        handler.postDelayed( new Runnable() {
            public void run() {
                reactionChange();
            }
        }, (long) reactBufferSetter());
    }

    public void changeToReady() {
        setContentView(R.layout.ready_screen);
    }

    public double reactBufferSetter(){
        int wait = rand.nextInt(6);
        double reactTime= (wait + 2.5) * 500;
        return reactTime;
    }

    public void reactionChange(){
        randScreen = rand.nextInt(4);
        MainActivity.ScreenNum = randScreen + 1;
        Intent intent;
        intent = new Intent(TransitionScreen.this, PlayScreen.class);
        startActivity(intent);
        overridePendingTransition(0,0);
    }
}

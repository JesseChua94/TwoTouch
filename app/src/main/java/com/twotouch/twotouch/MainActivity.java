package com.twotouch.twotouch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import java.util.Random;


public class MainActivity extends Activity implements View.OnClickListener {
    /* Counters for randomized screen number, scores of each player, and who won the point
    after each round.
     */
    public static int ScreenNum = 0;
    public static int scoreOne = 0;
    public static int scoreTwo = 0;
    public static int win = 0;
    View decorView;
    Handler handler;
    int randScreen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Puts screen in immersive sticky mode
       decorView = getWindow().getDecorView();
        stickyImmersion(decorView);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Randomizes screen to display for each round.
        Random rand = new Random();
        randScreen = rand.nextInt(4);
    }

    @Override
    protected void onResume(){
        super.onResume();
        Button start = (Button) findViewById(R.id.start);
        start.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        //Delays start of the game after displaying the ready screen to give players time to
        //prepare
        ScreenNum = randScreen + 1;
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                screenChange();
            }
        }, 1500);

        setContentView(R.layout.ready_screen);
    }

    public void screenChange() {
        Intent intent;
        intent = new Intent(MainActivity.this, PlayScreen.class);
        startActivity(intent);
    }

    public void stickyImmersion(View decorView) {
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN
                | android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }
}


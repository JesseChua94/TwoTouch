package com.twotouch.twotouch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Jesse on 2015-03-28.
 */
public class WinScreen extends Activity implements View.OnTouchListener {
    int scoreOne = MainActivity.scoreOne;
    int scoreTwo = MainActivity.scoreTwo;
    Intent intent;
    View decorView;

    protected void onCreate(Bundle savedInstanceState) {
        decorView = getWindow().getDecorView();
        MainActivity mainActivity = new MainActivity();
        mainActivity.stickyImmersion(decorView);
        //Changes to win screen based on who won
        getWindow().getDecorView().setOnTouchListener(this);
        super.onCreate(savedInstanceState);
        if (scoreOne == 10) {
            setContentView(R.layout.win_screen_one);
        } else if (scoreTwo == 10) {
            setContentView(R.layout.win_screen_two);
        }
    }
    //Go back to splash on touch and reset scores.
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                MainActivity.scoreOne = 0;
                MainActivity.scoreTwo = 0;
                intent = new Intent(WinScreen.this, MainActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }
}

package com.example.minotaurgame;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Timer;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{

    Button Button1;
    Button Button2;
    Button Button3;
    AnimationDrawable runningMinotaur;
    AnimationDrawable attackingMinotaur;
    AnimationDrawable slidingMinotaur;
    AnimationDrawable jumpingMinotaur;
    private Handler myHandler;
    private int animation = 0;
    private boolean buttonPressed = true;
    private int loopTime = 900;

    private float jumpXVelocity;
    private Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_game);

        ImageView minotaurWalk = (ImageView)findViewById(R.id.playerWalkAnim);
        minotaurWalk.setImageResource(R.drawable.runningminotaur);
        runningMinotaur = (AnimationDrawable)minotaurWalk.getDrawable();
        runningMinotaur.start();

        Button1 = (Button)findViewById(R.id.jumpButton);
        Button2 = (Button)findViewById(R.id.attackButton);
        Button3 = (Button)findViewById(R.id.slideButton);

        Button1.setOnClickListener(this);
        Button2.setOnClickListener(this);
        Button3.setOnClickListener(this);


        if(buttonPressed){
            myHandler = new Handler() {
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);

                    switch(animation){
                        case 0:
                            ImageView minotaurWalk = (ImageView)findViewById(R.id.playerWalkAnim);
                            minotaurWalk.setImageResource(R.drawable.runningminotaur);
                            runningMinotaur = (AnimationDrawable)minotaurWalk.getDrawable();
                            runningMinotaur.start();
                            loopTime = 900;
                            break;

                        case 1:
                            ImageView minotaurAttack = (ImageView) findViewById(R.id.playerWalkAnim);
                            minotaurAttack.setImageResource(R.drawable.attackingminotaur);
                            attackingMinotaur = (AnimationDrawable) minotaurAttack.getDrawable();
                            attackingMinotaur.start();
                            loopTime = 1600;
                            break;

                        case 2:
                            ImageView minotaurSlide = (ImageView) findViewById(R.id.playerWalkAnim);
                            minotaurSlide.setImageResource(R.drawable.slidingminotaur);
                            slidingMinotaur = (AnimationDrawable) minotaurSlide.getDrawable();
                            slidingMinotaur.start();
                            loopTime = 600;
                            break;

                        case 3:
                            ImageView minotaurJump = (ImageView) findViewById(R.id.playerWalkAnim);
                            minotaurJump.setImageResource(R.drawable.jumpingminotaur);
                            jumpingMinotaur = (AnimationDrawable) minotaurJump.getDrawable();
                            jumpingMinotaur.start();
                            loopTime = 800;
                            break;

                        default:
                            minotaurWalk = (ImageView)findViewById(R.id.playerWalkAnim);
                            minotaurWalk.setImageResource(R.drawable.runningminotaur);
                            runningMinotaur = (AnimationDrawable)minotaurWalk.getDrawable();
                            runningMinotaur.start();
                            loopTime = 900;
                            break;

                    }


                    animation = 0;

                    myHandler.sendEmptyMessageDelayed(0, loopTime);
                    buttonPressed = false;
                }
            };
            myHandler.sendEmptyMessage(0);
        }


    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.attackButton:
//                ImageView minotaurAttack = (ImageView) findViewById(R.id.playerWalkAnim);
//                minotaurAttack.setImageResource(R.drawable.attackingminotaur);
//                attackingMinotaur = (AnimationDrawable) minotaurAttack.getDrawable();
//                attackingMinotaur.start();
                buttonPressed = true;
                animation = 1;
                break;

            case R.id.jumpButton:
                buttonPressed = true;
                animation = 3;

                break;

            case R.id.slideButton:
//                ImageView minotaurSlide = (ImageView) findViewById(R.id.playerWalkAnim);
//                minotaurSlide.setImageResource(R.drawable.slidingminotaur);
//                slidingMinotaur = (AnimationDrawable) minotaurSlide.getDrawable();
//                slidingMinotaur.start();
                animation = 2;
                buttonPressed = true;

                break;

            default:
//                ImageView minotaurWalk = (ImageView)findViewById(R.id.playerWalkAnim);
//                minotaurWalk.setImageResource(R.drawable.runningminotaur);
//                runningMinotaur = (AnimationDrawable)minotaurWalk.getDrawable();
//                runningMinotaur.start();
                animation = 0;
                buttonPressed = true;

                break;
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    // Shows the system bars by removing all the flags
// except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
}

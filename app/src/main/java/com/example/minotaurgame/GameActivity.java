package com.example.minotaurgame;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.q42.android.scrollingimageview.ScrollingImageView;

import java.util.Timer;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{

    Button Button1;
    Button Button2;
    Button Button3;
    Button pauseButton;
    //
    AnimationDrawable minotaurState;
    //
    ImageView minotaurImageView;
    //
    TextView scoreText;
    TextView levelText;
    //
    int currentScore = 0;
    int currentLevel = 1;
    //
    int screenWidth;
    int screenHeight;
    //
    private Handler myHandler;
    private int animation = 0;
    private boolean buttonPressed = true;
    private int loopTime = 900;

    private Timer timer = new Timer();
    private Handler handler = new Handler();

    ScrollingImageView scrollingBackground;

    long startTime = System.currentTimeMillis();
    long elapsedTime = 0;

    boolean isGameOver = false;

    private boolean isPaused = false;

    Thread ourThread = null;
    volatile boolean playingGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_game);

        minotaurImageView = (ImageView)findViewById(R.id.playerWalkAnim);
        minotaurImageView.setImageResource(R.drawable.runningminotaur);
        minotaurState = (AnimationDrawable)minotaurImageView.getDrawable();
        minotaurState.start();

        Button1 = (Button)findViewById(R.id.jumpButton);
        Button2 = (Button)findViewById(R.id.attackButton);
        Button3 = (Button)findViewById(R.id.slideButton);
        pauseButton = (Button)findViewById(R.id.pauseButton);

        Button1.setOnClickListener(this);
        Button2.setOnClickListener(this);
        Button3.setOnClickListener(this);
        pauseButton.setOnClickListener(this);

        scrollingBackground = (ScrollingImageView)findViewById(R.id.scrolling_background);
        scrollingBackground.setSpeed(3);
        int enemiesKilled = 0;

        scoreText = (TextView)findViewById(R.id.scoreText);
        levelText = (TextView)findViewById(R.id.levelText);

        // Get screen size
//        WindowManager wm = getWindowManager();
//        Display disp = wm.getDefaultDisplay();
//        Point size = new Point();
//        disp.getSize(size);
//        screenWidth = size.x;
//        screenHeight = size.y;

        //Start the timer
//        timer.schedule(new TimerTask(){
//            @Override
//            public void run() {
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//
//                   }
//                });
//            }
//        }, 0, 20);


        myHandler = new Handler() {

            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                if (!isPaused) {

                    switch (animation) {
                        case 0:
                            SetAnimation(R.drawable.runningminotaur, 900, false, false);
                            scrollingBackground.setSpeed(3);
                            break;

                        case 1:
                            SetAnimation(R.drawable.attackingminotaur, 1600, false, false);
                            break;

                        case 2:
                            SetAnimation(R.drawable.slidingminotaur, 1200, false, false);
                            scrollingBackground.setSpeed(10);
                            break;

                        case 3:
                            SetAnimation(R.drawable.jumpingminotaur, 800, true, false);
                            break;

                        case 4:
                            SetAnimation(R.drawable.fallingminotaur, 800, false, true);
                            break;

                        default:
                            SetAnimation(R.drawable.runningminotaur, 900, false, false);
                            break;

                    }

                    if (animation == 3) {
                        animation = 4;
                    } else {
                        animation = 0;
                    }
                }


                myHandler.sendEmptyMessageDelayed(0, loopTime);
                buttonPressed = false;

            }
        };
        //I'm not sure where to put this to make it work
        //updateScore(enemiesKilled);
        updateScore(enemiesKilled);
        updateLevel();

        myHandler.sendEmptyMessage(0);
    }

    public void SetAnimation(int id, int lt, boolean goUp, boolean goDown) {
        minotaurImageView = findViewById(R.id.playerWalkAnim);
        minotaurImageView.setImageResource(id);
        minotaurState = (AnimationDrawable) minotaurImageView.getDrawable();
        minotaurState.start();
        loopTime = lt;

        if (goUp) {
            moveAnimationUp();
        }

        if (goDown) {
            moveAnimationDown();
        }
    }


//    public void pausePushed(View view) {
//        if (isPaused == false) {
//            isPaused = true;
//
//            //stop the timer
//            timer.cancel();
//            timer = null;
//
//            //change button text
//            pauseButton.setText("Resume");
//        } else {
//            isPaused = false;
//
//            //change button text back
//            pauseButton.setText("Pause");
//
//            //create and start the timer
//            timer = new Timer();
//            timer.schedule(new TimerTask(){
//                @Override
//                public void run() {
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//
//                        }
//                    });
//                }
//            }, 0, 20);
//        }
//    }



    public void moveAnimationUp() {
        Animation img = new TranslateAnimation(Animation.ABSOLUTE, Animation.ABSOLUTE, 0, -300);
        img.setDuration(800);


        minotaurImageView.startAnimation(img);
        //myHandler.sendEmptyMessageDelayed(0, 800);
        //moveAnimationDown();
    }

    public void moveAnimationDown() {
        Animation img = new TranslateAnimation(Animation.ABSOLUTE, Animation.ABSOLUTE, -300, 0);
        img.setDuration(800);



        minotaurImageView.startAnimation(img);
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.attackButton:
//
                buttonPressed = true;
                animation = 1;
                //scrollingBackground.setSpeed(3);
                break;

            case R.id.jumpButton:
                buttonPressed = true;
                animation = 3;
                //scrollingBackground.setSpeed(3);
                //moveAnimation();

                break;

            case R.id.slideButton:
//
                animation = 2;
                buttonPressed = true;
                //scrollingBackground.setSpeed(10);
               /* if(animation == 2) {
                    scrollingBackground.setSpeed(10);
                } else {
                    scrollingBackground.setSpeed(1);
                }*/
                break;

            case R.id.pauseButton:
                if (isPaused) {
                    minotaurState.start();
                    scrollingBackground.start();
                    // *** Use "minotaurImageView" and try to stop the animation

                    isPaused = false;

                    pauseButton.setText("Pause");
                    Button1.setEnabled(true);
                    Button2.setEnabled(true);
                    Button3.setEnabled(true);
                } else {
                    minotaurState.stop();
                    scrollingBackground.stop();

                    isPaused = true;

                    pauseButton.setText("Resume");
                    Button1.setEnabled(false);
                    Button2.setEnabled(false);
                    Button3.setEnabled(false);
                }

                break;

            default:
                animation = 0;
                buttonPressed = true;
                //scrollingBackground.setSpeed(3);
                break;
        }

    }

    void updateScore(int enemiesKilled) {

        for (int i = 1; i <= enemiesKilled; i++) {
            currentScore = currentScore + i * 10;
        }

        scoreText.setText("Score: " + currentScore);
    }

    void updateLevel() {
        if (elapsedTime >= 10000) {
            currentLevel++;
        }
        levelText.setText("Level: " + currentLevel);

    }

//    public void pause() {
//        playingGame = false;
//        try {
//            ourThread.join();
//        } catch (InterruptedException e) {
//
//        }
//    }
//
//    public void resume() {
//        playingGame = true;
//        ourThread = new Thread(this);
//        ourThread.start();
//    }

    @Override
    protected void onPause() {
        super.onPause();
        elapsedTime = elapsedTime + (System.currentTimeMillis() - startTime);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startTime = (System.currentTimeMillis());

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

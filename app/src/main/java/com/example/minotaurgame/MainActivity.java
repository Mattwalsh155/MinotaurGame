package com.example.minotaurgame;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {
    //for the Hi scores
    SharedPreferences prefs;
    String dataName = "MyData";
    String intName = "MyString";
    int defaultInt = 0;
    public static int hiScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        //for our High score
        //initialize our two SharedPreferences objects
        prefs = getSharedPreferences(dataName, MODE_PRIVATE);

        //either load our hiScore or default to 0
        hiScore = prefs.getInt(intName, defaultInt);

        //Make a reference to the Hiscore textview
        TextView hiScoreText = findViewById(R.id.hiScoreText);
        //displays the High score
        hiScoreText.setText("High Score:" + hiScore);

        Button playButton = (Button)findViewById(R.id.playButton);

        minotaurAnimView = new MinotaurAnimView(this);

        setContentView(minotaurAnimView);
        setContentView(R.layout.activity_game);

        //i = new Intent(this, GameActivity.class);
    }

    class MinotaurAnimView extends SurfaceView implements Runnable {
        Thread ourThread = null;
        SurfaceHolder ourHolder;
        volatile boolean playingGame;
        Paint paint;

        public MinotaurAnimView(Context context) {
            super(context);
            ourHolder = getHolder();
            paint = new Paint();
            frameWidth = playerWalkAnimBitmap.getWidth() / numFrames;
            frameHeight = playerWalkAnimBitmap.getHeight();
        }

        @Override
        public void run() {
            while (playingGame) {
                update();
                draw();
                controlFPS();
                //background();
            }
        }




        public void update() {


            //which frame should be drawn
            rectToBeDrawn = new Rect((frameNumber * frameWidth) - 1,
                    0, (frameNumber * frameWidth + frameWidth) - 1, frameHeight);

            //now go to next frame
            frameNumber++;

            //reset back to first frame when we reach the last frame
            if (frameNumber == numFrames) {
                frameNumber = 0;
            }
        }

        public void draw() {

            if (ourHolder.getSurface().isValid()) {
                canvas = ourHolder.lockCanvas();
                canvas.drawColor(Color.BLACK);
                paint.setColor(Color.argb(255, 255, 255, 255));
                paint.setTextSize(250);
                //canvas.drawText("Minotaur Game", 10, 150, paint);
                //paint.setTextSize(25);
                //canvas.drawText("Lives: ", 10, screenHeight-50, paint);

                //draw the minotaur
                Rect destRect = new Rect(screenWidth / 2 - 200,
                        screenHeight / 2 - 200, screenWidth / 2 + 200,
                        screenHeight / 2 + 200);


                canvas.drawBitmap(playerWalkAnimBitmap,
                        rectToBeDrawn, destRect, paint);

                ourHolder.unlockCanvasAndPost(canvas);
            }
        }

        public void controlFPS() {
            long timeThisFrame = (System.currentTimeMillis() -
                    lastFrameTime);
            //this controls the speed of the sprite
            long timeToSleep = 50 - timeThisFrame;
            if (timeThisFrame > 0) {
                fps = (int) (1000 / timeThisFrame);
            }
            if (timeToSleep > 0) {

                try {
                    ourThread.sleep(timeToSleep);
                } catch (InterruptedException e) {

                }
            }

            lastFrameTime = System.currentTimeMillis();

        }

        public void pause() {
            playingGame = false;
            try {
                ourThread.join();
            } catch (InterruptedException e) {
            }
        }

        public void resume() {
            playingGame = true;
            ourThread = new Thread(this);
            ourThread.start();
        }

        @Override
        public boolean onTouchEvent (MotionEvent motionEvent) {
            //startActivity(i);
            return true;
        }

        playButton.setOnClickListener(this);


        Button controls = (Button)findViewById(R.id.controls);

        controls.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent i;
        Intent j;
        i = new Intent(this, GameActivity.class);
        startActivity(i);

        finish();
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

package com.example.minotaurgame;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

    Canvas canvas;

    MinotaurAnimView minotaurAnimView;

    //the player sprite sheet
    Bitmap playerWalkAnimBitmap;

    Rect rectToBeDrawn;

    //frame dimensions
    int frameHeight = 92;
    int frameWidth = 256;
    int numFrames = 8;
    int frameNumber;

    int screenWidth;
    int screenHeight;

    //stats
    int score = 0;
    int lives = 3;
    int fps;
    long lastFrameTime;

    //Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //find out the width and height of the screen
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        playerWalkAnimBitmap =
                BitmapFactory.decodeResource(getResources(), R.drawable.minotaur_walk);

        minotaurAnimView = new MinotaurAnimView(this);

        setContentView(minotaurAnimView);

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
    }

    @Override
    protected void onStop() {
        super.onStop();

        while (true) {
            minotaurAnimView.pause();
            break;
        }

        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        minotaurAnimView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        minotaurAnimView.pause();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            minotaurAnimView.pause();
            finish();
            return true;
        }
        return false;
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

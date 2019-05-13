package com.example.minotaurgame;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import java.util.Random;


public class GameActivity extends AppCompatActivity {

    volatile boolean playingGame;

    // ImageView backgroundOne =  findViewById(R.id.castle1);
    // ImageView backgroundTwo =  findViewById(R.id.castle2);



/*
   public class background1 {

        private int x, y;
        private int speed;

        private int maxX;
        private int maxY;
        private int minX;
        private int minY;

        //constructor
        public background1(int screenX, int screenY){



            maxX = screenX;
            maxY = screenY;
            minX = 0;
            minY = 0;

            //Set speed between 0-9
            Random generator = new Random();
            speed = generator.nextInt(10);

            //set coordinates
            x = generator.nextInt(maxX);
            y = generator.nextInt(maxY);
        }
*/



        /*public void update(int playerSpeed){
            x -= playerSpeed;
            x -= speed;
            //background1.setTranslationX(1.0f);

            //respawn background
            if(x < 0){
                x = maxX;
                Random generator = new Random();
                y = generator.nextInt(15);
            }
        }*/
        //getters & setters
      //  public int getX(){
         //   return x;
       // }
       // public int getY(){
         //   return y;
        //}
    //}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //setContentView(R.layout.activity_game);

        update();

    }



    private void update() {

        //backgroundTwo.setTranslationX(10f);

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

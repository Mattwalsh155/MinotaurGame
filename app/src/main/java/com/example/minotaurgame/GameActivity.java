package com.example.minotaurgame;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
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
import android.media.MediaPlayer;

import java.io.IOException;
import java.util.Timer;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{

    Button Button1;
    Button Button2;
    Button Button3;
    AnimationDrawable runningMinotaur;
    AnimationDrawable attackingMinotaur;
    AnimationDrawable slidingMinotaur;
    AnimationDrawable jumpingMinotaur;
    AnimationDrawable fallingMinotaur;
    ImageView minotaurWalk;
    ImageView minotaurJump;
    ImageView minotaurAttack;
    ImageView minotaurSlide;
    ImageView minotaurFall;
    private Handler myHandler;
    private int animation = 0;
    private boolean buttonPressed = true;
    private int loopTime = 900;

    MediaPlayer music;

    private SoundPool soundPool;
    int jump = -1;
    int slide = -1;
    int attack = -1;

    private float jumpXVelocity;
    private Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC,0);
        try{
            AssetManager assetManager = getAssets();
            AssetFileDescriptor descriptor;

            descriptor = assetManager.openFd("jump.wav");
            jump = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("hit.wav");
            attack = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("slide.wav");
            slide = soundPool.load(descriptor, 0);
        }catch (IOException e){}

        music();

        setContentView(R.layout.activity_game);

        minotaurWalk = (ImageView)findViewById(R.id.playerWalkAnim);
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
                            minotaurWalk = (ImageView)findViewById(R.id.playerWalkAnim);
                            minotaurWalk.setImageResource(R.drawable.runningminotaur);
                            runningMinotaur = (AnimationDrawable)minotaurWalk.getDrawable();
                            runningMinotaur.start();
                            loopTime = 900;
                            break;

                        case 1:
                            minotaurAttack = (ImageView) findViewById(R.id.playerWalkAnim);
                            minotaurAttack.setImageResource(R.drawable.attackingminotaur);
                            attackingMinotaur = (AnimationDrawable) minotaurAttack.getDrawable();
                            attackingMinotaur.start();
                            loopTime = 1600;
                            break;

                        case 2:
                            minotaurSlide = (ImageView) findViewById(R.id.playerWalkAnim);
                            minotaurSlide.setImageResource(R.drawable.slidingminotaur);
                            slidingMinotaur = (AnimationDrawable) minotaurSlide.getDrawable();
                            slidingMinotaur.start();
                            loopTime = 600;
                            break;

                        case 3:
                            minotaurJump = (ImageView) findViewById(R.id.playerWalkAnim);
                            minotaurJump.setImageResource(R.drawable.jumpingminotaur);
                            jumpingMinotaur = (AnimationDrawable) minotaurJump.getDrawable();
                            jumpingMinotaur.start();
                            loopTime = 800;
                            moveAnimationUp();
                            //moveAnimationDown();

                            break;

                        case 4:
                            minotaurFall = (ImageView) findViewById(R.id.playerWalkAnim);
                            minotaurFall.setImageResource(R.drawable.fallingminotaur);
                            fallingMinotaur = (AnimationDrawable) minotaurFall.getDrawable();
                            fallingMinotaur.start();
                            loopTime = 800;
                            //moveAnimationUp();
                            moveAnimationDown();

                            break;

                        default:
                            minotaurWalk = (ImageView)findViewById(R.id.playerWalkAnim);
                            minotaurWalk.setImageResource(R.drawable.runningminotaur);
                            runningMinotaur = (AnimationDrawable)minotaurWalk.getDrawable();
                            runningMinotaur.start();
                            loopTime = 900;
                            break;

                    }

                    if(animation == 3){
                        animation = 4;
                    } else {
                        animation = 0;
                    }


                    myHandler.sendEmptyMessageDelayed(0, loopTime);
                    buttonPressed = false;
                }
            };
            myHandler.sendEmptyMessage(0);
        }
    }

    public void music(){
        if(music == null){
            music = MediaPlayer.create(this,R.raw.soundtrack);
            if(music != null){
                music = MediaPlayer.create(this,R.raw.soundtrack2);
                music.setLooping(true);
            }
        }
        music.start();
    }





    public void time(){

    }


    public void moveAnimationUp() {
        Animation img = new TranslateAnimation(Animation.ABSOLUTE, Animation.ABSOLUTE, 0, -300);
        img.setDuration(800);


        minotaurJump.startAnimation(img);
        //myHandler.sendEmptyMessageDelayed(0, 800);
        //moveAnimationDown();
    }

    public void moveAnimationDown() {
        Animation img = new TranslateAnimation(Animation.ABSOLUTE, Animation.ABSOLUTE, -300, 0);
        img.setDuration(800);


        minotaurJump.startAnimation(img);
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
                soundPool.play(attack, 1, 1, 0, 0, 1);

                break;

            case R.id.jumpButton:
                buttonPressed = true;
                animation = 3;
                soundPool.play(jump, 1, 1, 0, 0, 1);
                //moveAnimation();

                break;

            case R.id.slideButton:
//                ImageView minotaurSlide = (ImageView) findViewById(R.id.playerWalkAnim);
//                minotaurSlide.setImageResource(R.drawable.slidingminotaur);
//                slidingMinotaur = (AnimationDrawable) minotaurSlide.getDrawable();
//                slidingMinotaur.start();
                buttonPressed = true;
                animation = 2;
                soundPool.play(slide, 1, 1, 0, 0, 1);


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

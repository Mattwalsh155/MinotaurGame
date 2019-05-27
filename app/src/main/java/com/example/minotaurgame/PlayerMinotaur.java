package com.example.minotaurgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;

public class PlayerMinotaur extends AppCompatActivity {

    private Bitmap bitmap;
    private int x, y;
    private int speed = 0;

    //constructor
    public PlayerMinotaur (Context context) {
        x = 50;
        y = 50;
        speed = 1;
        bitmap = BitmapFactory.decodeResource
                (context.getResources(), R.drawable.runningminotaur);
    }

    public void update() {

    }
}

package com.example.trackingcorona;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {

    private static  final int SPLASH_SCREEN=4000;
    private TextView subject,down;
    private LottieAnimationView loading;
    Animation topanim, botanim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        subject = findViewById(R.id.Subject);
        down = findViewById(R.id.down);
        loading = findViewById(R.id.loading);
        topanim= AnimationUtils.loadAnimation(MainActivity.this,R.anim.top_animation);
        botanim= AnimationUtils.loadAnimation(MainActivity.this,R.anim.bot_anim);

        loading.setAnimation(topanim);
        subject.setAnimation(botanim);
        down.setAnimation(botanim);


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this,Dashboard.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN);


    }
}
package com.example.selfevaluation.activity;

import android.content.Intent;
import android.os.Bundle;

import com.example.selfevaluation.R;

import me.wangyuwei.particleview.ParticleView;

public class SplashScreen extends BaseActivity {

    ParticleView mParticleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mParticleView = findViewById(R.id.particle);
        mParticleView.startAnim();
        mParticleView.setOnParticleAnimListener(new ParticleView.ParticleAnimListener() {
            @Override
            public void onAnimationEnd() {
                startActivity(new Intent(SplashScreen.this, QuizScreen.class));
                finish();
            }
        });
    }
}

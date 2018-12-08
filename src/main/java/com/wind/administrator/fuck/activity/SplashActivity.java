package com.wind.administrator.fuck.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.wind.administrator.fuck.R;

public class SplashActivity extends BaseActivity {

    private ImageView mLogo_iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
        initSplashAnim();
    }

    private void initSplashAnim() {
        //动画实现,属性动画
        ObjectAnimator alpha = ObjectAnimator.ofFloat(mLogo_iv, "alpha", 0.0f, 1.0f);

        alpha.setDuration(2000);
        //设置延迟
//        alpha.setStartDelay(500);

        //监听：当动画结束时启动新的界面
        alpha.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                //跳转之后 关闭当前的页面
                finish();
                super.onAnimationEnd(animation);
            }
        });
        //启动动画
        alpha.start();
    }

    private void initView() {
        mLogo_iv = (ImageView) findViewById(R.id.logo_iv);
    }
}

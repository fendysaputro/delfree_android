package com.delfree.delfree_android.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.delfree.delfree_android.AppDelfree;
import com.delfree.delfree_android.MainActivity;
import com.delfree.delfree_android.R;

/**
 * created by phephen 03/09/2019
 */

public class SplashScreen extends AppCompatActivity {

    AppDelfree appDelfree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        appDelfree = (AppDelfree) getApplication();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(appDelfree.isLogin(SplashScreen.this, MainActivity.MODE_PRIVATE)){
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                } else {
                    startActivity(new Intent(SplashScreen.this, LoginPage.class));
                }
            }
        }, 5000);
    }
}

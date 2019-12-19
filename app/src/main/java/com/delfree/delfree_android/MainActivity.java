package com.delfree.delfree_android;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.WrappedDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.delfree.delfree_android.Activity.LoginPage;
import com.delfree.delfree_android.Activity.ProgressRoute;
import com.delfree.delfree_android.Fragment.FinishJobFragment;
import com.delfree.delfree_android.Fragment.HistoryFragment;
import com.delfree.delfree_android.Fragment.HomeFragment;
import com.delfree.delfree_android.Fragment.ProfileFragment;

/**
 * Created by phephen on 6/8/19.
 */

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    AppDelfree appDelfree;
    public static boolean allowBackPressed = false;
    public boolean isFirstBackPressed = false;
    private String [] permissions = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.ACCESS_FINE_LOCATION",
            "android.permission.READ_PHONE_STATE", "android.permission.SYSTEM_ALERT_WINDOW","android.permission.CAMERA"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appDelfree = (AppDelfree) getApplication();

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        int requestCode = 200;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }


        requestPermission();


        loadFragment(new HomeFragment());
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

//        Drawable logo = getResources().getDrawable(R.drawable.logobatavree_new);
//        Bitmap bitmap = ((BitmapDrawable) logo).getBitmap();
//        Drawable logoBatavree = new BitmapDrawable(getResources(),
//                Bitmap.createBitmap(bitmap, 20, 20, 50, 50));
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDisplayUseLogoEnabled(true);
//        getSupportActionBar().setLogo(logo);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        getSupportActionBar().setDisplayUseLogoEnabled(true);
//        ImageView imageView = new ImageView(this);
//        imageView.setImageDrawable(logo);
//        imageView.setPadding(200,0,200,0);
//        textView.setText("Batavree");
//        textView.setTextSize(20);
//        textView.setTypeface(null, Typeface.BOLD);
//        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//        textView.setGravity(Gravity.CENTER);
//        textView.setTextColor(getResources().getColor(R.color.chooseNav));
//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setCustomView(textView);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        toolbar.setLogo(R.drawable.textbatavree);
//        toolbar.setTitle("Batavree");
//        toolbar.setTitleTextColor(getResources().getColor(R.color.chooseNav));

    }

    public boolean loadFragment(Fragment fragment){
        if (fragment != null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem){
        Fragment fragment = null;
        switch (menuItem.getItemId()){
            case R.id.navigation_home:
                fragment = new HomeFragment();
                break;
            case R.id.navigation_history:
                fragment = new HistoryFragment();
                break;
            case R.id.navigation_profile:
                fragment = new ProfileFragment();
                break;
        }
        return loadFragment(fragment);
    }

    public void requestPermission(){

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET}, 3);
        }
    }

    public static void ShowFragment(int resId, Fragment fragment, android.support.v4.app.FragmentManager fm) {
        final FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(resId, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

//    @Override
//    public void onBackPressed() {
//        if (getSupportFragmentManager().getBackStackEntryCount() != 0){
//            super.onBackPressed();
//        }else{
//            if (allowBackPressed) {
//                super.onBackPressed();
//            } else {
//                allowBackPressed = true;
//                Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        allowBackPressed = false;
//                    }
//                }, 500);
//            }
//        }
//    }

    @Override
    public void onBackPressed() {
        if(allowBackPressed){
            super.onBackPressed();
            finish();
            return;
        }
        this.allowBackPressed = false;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                allowBackPressed=true;          }
        }, 500);
    }

//        @Override
//    public void onResume() {
//        super.onResume();
//        MainActivity.allowBackPressed = false;
//        ((AppCompatActivity)this).getSupportActionBar().show();
//    }
}

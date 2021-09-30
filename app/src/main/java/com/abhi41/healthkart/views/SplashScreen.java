package com.abhi41.healthkart.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.abhi41.healthkart.R;
import com.abhi41.healthkart.dagger2.SharedPreferenceStorage;
import com.abhi41.healthkart.dagger2.components.MyComponent;
import com.abhi41.healthkart.databinding.ActivitySplashBinding;
import com.abhi41.healthkart.utils.MyApplication;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_SCREEN_TIME_OUT = 2000;
    ActivitySplashBinding binding;
    private static final String TAG = "SplashScreen";

    MyComponent myComponent;
    SharedPreferenceStorage sharedPreferenceStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_splash);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);

        initialiZation();
        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21)
        {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        changeStatusBarColor();

        initScreen();


    }

    private void initialiZation() {
        myComponent = ((MyApplication) getApplication()).getSharedPrefModule();
        sharedPreferenceStorage = myComponent.sharedPreferenceStorage();


    }

    private void initScreen() {



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run()
            {

                if (sharedPreferenceStorage.isLogin(sharedPreferenceStorage.isLogin, false))
                {
                    startActivity(new Intent(SplashScreen.this, DashBoardScreen.class));
                    finish();
                }else {
                    Intent intent = new Intent(SplashScreen.this, LoginScreen.class);
                    startActivity(intent);
                    finish();
                }

            }
        }, SPLASH_SCREEN_TIME_OUT);


    }

    private void changeStatusBarColor()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }
}
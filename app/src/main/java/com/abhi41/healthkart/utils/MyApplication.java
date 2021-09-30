package com.abhi41.healthkart.utils;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

import com.abhi41.healthkart.dagger2.components.DaggerMyComponent;
import com.abhi41.healthkart.dagger2.components.MyComponent;
import com.abhi41.healthkart.dagger2.modules.SharedPreferenceModule;

public class MyApplication extends Application {

    private MyComponent myComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        myComponent = DaggerMyComponent.builder()
                .sharedPreferenceModule(new SharedPreferenceModule(this))
                .build();

        myComponent.inject(MyApplication.this);

    }
    public MyComponent getSharedPrefModule() {
        return myComponent;
    }


}

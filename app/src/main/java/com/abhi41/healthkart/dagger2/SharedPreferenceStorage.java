package com.abhi41.healthkart.dagger2;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.FragmentActivity;

import com.abhi41.healthkart.views.LoginScreen;

public class SharedPreferenceStorage {

    private Context context;
    public String SharedPreference = "Storage";
    public String NoInfo = "";

    public String name = "name";
    public String last_name = "last_name";
    public String email = "email";
    public String password = "password";
    public String isLogin = "isLogin";

    public SharedPreferenceStorage(Application application) {
        this.context = application;
    }

    public void addInformation(String key, String value) {

        try {
            context.getSharedPreferences(SharedPreference, Context.MODE_PRIVATE)
                    .edit().putString(key, value).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getInformation(String key) {

        String value = new String();
        ;
        try {

            value = context.getSharedPreferences(SharedPreference, Context.MODE_PRIVATE)
                    .getString(key, NoInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;
    }

    public void setIsLogin(String key, boolean value) {

        try {
            context.getSharedPreferences(SharedPreference, Context.MODE_PRIVATE)
                    .edit().putBoolean(key, value).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public boolean isLogin(String key, boolean value) {

        try {
            value = context.getSharedPreferences(SharedPreference, Context.MODE_PRIVATE)
                    .getBoolean(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;
    }


    public void clearStorage(FragmentActivity activity) {
        context.getSharedPreferences(SharedPreference,
                Context.MODE_PRIVATE)
                .edit().clear().commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(activity, LoginScreen.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        activity.startActivity(i);
        activity.finish();
    }
}

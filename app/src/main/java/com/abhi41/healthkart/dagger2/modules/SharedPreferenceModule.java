package com.abhi41.healthkart.dagger2.modules;

import android.app.Application;


import com.abhi41.healthkart.dagger2.SharedPreferenceStorage;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class SharedPreferenceModule {

    private Application application;

    public SharedPreferenceModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Application provideApplication()
    {
        return application;
    }

    @Provides
    @Singleton
    public SharedPreferenceStorage provideLocalStorage(Application application)
    {
        return new SharedPreferenceStorage(application);
    }
}

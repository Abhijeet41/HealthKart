package com.abhi41.healthkart.dagger2.components;


import com.abhi41.healthkart.dagger2.SharedPreferenceStorage;
import com.abhi41.healthkart.dagger2.modules.SharedPreferenceModule;
import com.abhi41.healthkart.utils.MyApplication;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {SharedPreferenceModule.class})
public interface MyComponent {

    SharedPreferenceStorage sharedPreferenceStorage();

    void inject(MyApplication myApplication);

}

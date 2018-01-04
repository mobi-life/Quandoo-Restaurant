package com.quandoo.restaurant.di;

import android.app.Activity;
import android.app.Application;

import com.quandoo.restaurant.di.component.ActivityComponent;
import com.quandoo.restaurant.di.component.AppComponent;
import com.quandoo.restaurant.di.component.DaggerActivityComponent;
import com.quandoo.restaurant.di.component.DaggerAppComponent;
import com.quandoo.restaurant.di.component.DaggerFragmentComponent;
import com.quandoo.restaurant.di.component.FragmentComponent;
import com.quandoo.restaurant.di.module.ActivityModule;
import com.quandoo.restaurant.di.module.FragmentModule;
import com.quandoo.restaurant.di.modules.MockedApplicationModule;
import com.quandoo.restaurant.di.modules.MockedNetworkModule;

import org.mockito.Mock;

/**
 * Created by Behzad on 1/1/2018.
 */

public class DaggerComponentsFactory {


    @Mock
    public static Application application;
    @Mock
    public static Activity activity;

    private static AppComponent appComponent;
    private static ActivityComponent activityComponent;
    private static FragmentComponent fragmentComponent;

    public static AppComponent getApplicationComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent
                    .builder()
                    .networkModule(new MockedNetworkModule())
                    .applicationModule(new MockedApplicationModule(application))
                    .build();
        }
        return appComponent;
    }

    public static ActivityComponent getActivityComponent() {
        if (activityComponent == null) {
            activityComponent = DaggerActivityComponent
                    .builder()
                    .activityModule(new ActivityModule(activity))
                    .appComponent(getApplicationComponent())
                    .build();
        }
        return activityComponent;
    }

    public static FragmentComponent getFragmentComponent() {
        if (fragmentComponent == null) {
            fragmentComponent = DaggerFragmentComponent
                    .builder()
                    .fragmentModule(new FragmentModule())
                    .appComponent(getApplicationComponent())
                    .build();
        }
        return fragmentComponent;
    }


}

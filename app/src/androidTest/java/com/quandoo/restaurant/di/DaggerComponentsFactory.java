package com.quandoo.restaurant.di;

import android.app.Application;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.quandoo.restaurant.di.component.ActivityComponent;
import com.quandoo.restaurant.di.component.AppComponent;
import com.quandoo.restaurant.di.component.DaggerActivityComponent;
import com.quandoo.restaurant.di.component.DaggerAppComponent;
import com.quandoo.restaurant.di.component.DaggerFragmentComponent;
import com.quandoo.restaurant.di.component.FragmentComponent;
import com.quandoo.restaurant.di.module.ActivityModule;
import com.quandoo.restaurant.di.module.ApplicationModule;
import com.quandoo.restaurant.di.module.FragmentModule;
import com.quandoo.restaurant.di.module.NetworkModule;
import com.quandoo.restaurant.di.modules.MockedApplicationModule;
import com.quandoo.restaurant.ui.views.activity.MainActivity;

import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * Created by Behzad on 1/1/2018.
 */

public class DaggerComponentsFactory {

    @Rule
    public static ActivityTestRule<MainActivity> mainActivityActivityTestRule =
            new ActivityTestRule<MainActivity>(MainActivity.class);

    private static AppComponent appComponent;
    private static ActivityComponent activityComponent;
    private static FragmentComponent fragmentComponent;

    public static AppComponent getApplicationComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent
                    .builder()
                    .networkModule(new NetworkModule())
                    .applicationModule(new MockedApplicationModule(InstrumentationRegistry.getTargetContext()))
                    .build();
        }
        return appComponent;
    }

    public static ActivityComponent getActivityComponent() {
        if (activityComponent == null) {
            activityComponent = DaggerActivityComponent
                    .builder()
                    .activityModule(new ActivityModule(mainActivityActivityTestRule.getActivity()))
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

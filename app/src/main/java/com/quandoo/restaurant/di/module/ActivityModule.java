package com.quandoo.restaurant.di.module;

import android.app.Activity;
import android.content.Context;

import com.quandoo.restaurant.di.qualifier.ActivityContext;
import com.quandoo.restaurant.di.scope.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Behzad on 12/27/2017.
 */

@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    @Provides
    @PerActivity
    @ActivityContext
    public Context provideActivityContext() {
        return mActivity;
    }
}

package com.quandoo.restaurant.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.quandoo.restaurant.BuildConfig;
import com.quandoo.restaurant.data.local.database.QuandooDatabase;
import com.quandoo.restaurant.di.qualifier.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Behzad on 12/25/2017.
 */

@Module
public class ApplicationModule {

    private Context mApplication;

    public ApplicationModule(Context app) {
        mApplication = app;
    }

    @Provides
    @Singleton
    @ApplicationContext
    public Context provideApplicationContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    public QuandooDatabase getDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(context,
                QuandooDatabase.class,
                BuildConfig.DATABASE_NAME)
                .build();
    }
}

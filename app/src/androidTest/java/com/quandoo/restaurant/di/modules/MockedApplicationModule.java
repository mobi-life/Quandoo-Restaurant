package com.quandoo.restaurant.di.modules;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.quandoo.restaurant.BuildConfig;
import com.quandoo.restaurant.data.local.database.QuandooDatabase;
import com.quandoo.restaurant.di.module.ApplicationModule;

import org.mockito.Mockito;

/**
 * Created by Behzad on 1/4/2018.
 */

public class MockedApplicationModule extends ApplicationModule{

    public MockedApplicationModule(Context app) {
        super(app);
    }

    @Override
    public QuandooDatabase getDatabase(Context context) {
        return Room.inMemoryDatabaseBuilder(
                context,
                QuandooDatabase.class)
                .allowMainThreadQueries()  // we added this to be able to run queries on Main thread for instrumentation tests.
                .build();
    }
}

package com.quandoo.restaurant.di.modules;

import android.app.Application;
import android.content.Context;

import com.quandoo.restaurant.data.local.database.QuandooDatabase;
import com.quandoo.restaurant.di.module.ApplicationModule;

import org.mockito.Mockito;

/**
 * Created by Behzad on 1/1/2018.
 */

public class MockedApplicationModule extends ApplicationModule {
    public MockedApplicationModule(Application app) {
        super(app);
    }

    @Override
    public Context provideApplicationContext() {
        return Mockito.mock(Application.class);
    }

    @Override
    public QuandooDatabase getDatabase(Context context) {
        return Mockito.mock(QuandooDatabase.class);
    }
}

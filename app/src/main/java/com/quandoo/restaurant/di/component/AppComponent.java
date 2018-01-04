package com.quandoo.restaurant.di.component;

import com.quandoo.restaurant.QuandooRestaurantApp;
import com.quandoo.restaurant.data.local.database.QuandooDatabase;
import com.quandoo.restaurant.di.module.ApplicationModule;
import com.quandoo.restaurant.di.module.NetworkModule;
import com.quandoo.restaurant.jobs.TheJobCreator;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by Behzad on 12/25/2017.
 * <p>
 * Provides dependencies for the Application scope
 */

@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface AppComponent {
    Retrofit getRetrofit();

    OkHttpClient getOkHttp();

    QuandooDatabase getDatabase();

    TheJobCreator getJobCreator();

    void inject(QuandooRestaurantApp quandooRestaurantApp);
}

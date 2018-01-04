package com.quandoo.restaurant.di.modules;

import android.content.Context;

import com.quandoo.restaurant.data.network.CachingInterceptor;
import com.quandoo.restaurant.di.module.NetworkModule;
import com.quandoo.restaurant.di.qualifier.ApplicationContext;

import org.mockito.Mockito;

import javax.annotation.Nullable;

import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * Created by Behzad on 1/1/2018.
 */

public class MockedNetworkModule extends NetworkModule {
    /**
     * Providing a fake cache
     *
     * @param context
     * @return
     */
    @Override
    public Cache provideCache(@ApplicationContext Context context) {
        return Mockito.mock(Cache.class);
    }

    /**
     * Providing OkHttp without caching for testing purpose
     *
     * @param cache
     * @return
     */
    @Override
    public OkHttpClient provideOkHttp(Cache cache) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new CachingInterceptor())
                .build();
        return okHttpClient;
    }
}

package com.quandoo.restaurant.di.module;

import android.content.Context;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.quandoo.restaurant.BuildConfig;
import com.quandoo.restaurant.data.network.CachingInterceptor;
import com.quandoo.restaurant.data.network.QuandooService;
import com.quandoo.restaurant.di.qualifier.ApplicationContext;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Behzad on 12/25/2017.
 */

@Module
public class NetworkModule {

    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.END_POINT)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttp(Cache cache) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .addNetworkInterceptor(new CachingInterceptor())
                .build();
        return okHttpClient;
    }

    @Provides
    @Singleton
    public Cache provideCache(@ApplicationContext Context context) {
        long SIZE_OF_CACHE = 10 * 1024 * 1024; // 10 MiB
        return new Cache(new File(context.getCacheDir(), "http"), SIZE_OF_CACHE);
    }

    @Provides
    @Singleton
    public QuandooService provideQuandooService(Retrofit retrofit) {
        return new QuandooService(retrofit);
    }
}

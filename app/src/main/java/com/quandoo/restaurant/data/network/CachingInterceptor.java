package com.quandoo.restaurant.data.network;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Behzad on 1/1/2018.
 */

public class CachingInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        request = new Request.Builder()
                .headers(request.headers())
                .cacheControl(new CacheControl.Builder()
                        .maxAge(1, TimeUnit.DAYS)
                        .minFresh(5, TimeUnit.MINUTES)
                        .maxStale(1, TimeUnit.DAYS)
                        .build())
                .url(request.url())
                .build();

        return chain.proceed(request);
    }
}

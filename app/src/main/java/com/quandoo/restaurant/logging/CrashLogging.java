package com.quandoo.restaurant.logging;

import android.util.Log;

import com.quandoo.restaurant.domain.contract.ApplicationContract;

import timber.log.Timber;

/**
 * Created by Behzad on 1/8/2018.
 */

public class CrashLogging extends Timber.Tree{

    ApplicationContract mApplication;

    public CrashLogging(ApplicationContract app){
        mApplication = app;
    }

    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
        if (priority == Log.ERROR || priority == Log.WARN) {
            mApplication.reportCrash(t);
        }
    }
}

package com.quandoo.restaurant;

import android.app.Application;

import com.evernote.android.job.JobManager;
import com.quandoo.restaurant.di.component.AppComponent;
import com.quandoo.restaurant.di.component.DaggerAppComponent;
import com.quandoo.restaurant.di.module.ApplicationModule;
import com.quandoo.restaurant.di.module.NetworkModule;
import com.quandoo.restaurant.domain.contract.ApplicationContract;
import com.quandoo.restaurant.jobs.PeriodicReservationCleanerJob;
import com.quandoo.restaurant.jobs.TheJobCreator;
import com.quandoo.restaurant.logging.CrashLogging;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by Behzad on 12/25/2017.
 */

public class QuandooRestaurantApp extends Application
        implements ApplicationContract {

    private static AppComponent mComponent;

    @Inject
    public TheJobCreator jobCreator;

    public static AppComponent getComponent() {
        return mComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashLogging(this));
        }

        mComponent = DaggerAppComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .networkModule(new NetworkModule())
                .build();
        mComponent.inject(this);

        scheduleJobs();
    }

    private void scheduleJobs() {
        JobManager.create(this)
                .addJobCreator(jobCreator);
        PeriodicReservationCleanerJob.schedulePeriodic();
    }

    @Override
    public void reportCrash(Throwable throwable) {
        // We would send the crash using a crash reporting library
    }
}

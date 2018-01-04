package com.quandoo.restaurant;

import android.app.Application;

import com.evernote.android.job.JobManager;
import com.quandoo.restaurant.di.component.AppComponent;
import com.quandoo.restaurant.di.component.DaggerAppComponent;
import com.quandoo.restaurant.di.module.ApplicationModule;
import com.quandoo.restaurant.di.module.NetworkModule;
import com.quandoo.restaurant.jobs.PeriodicReservationCleanerJob;
import com.quandoo.restaurant.jobs.TheJobCreator;

import javax.inject.Inject;

/**
 * Created by Behzad on 12/25/2017.
 */

public class QuandooRestaurantApp extends Application {

    private static AppComponent mComponent;

    @Inject
    public TheJobCreator jobCreator;

    public static AppComponent getComponent() {
        return mComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
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

}

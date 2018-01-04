package com.quandoo.restaurant.helper;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Behzad on 12/28/2017.
 * <p>
 * Reduces the dependency of ViewModels to actual thread schedulers and makes the testing of
 * ViewModels easier.
 */

public class SchedulersFacade {

    /**
     * IO thread scheduler
     */
    public Scheduler io() {
        return Schedulers.io();
    }

    /**
     * Computation thread scheduler
     */
    public Scheduler computation() {
        return Schedulers.computation();
    }

    /**
     * Main thread scheduler
     */
    public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }
}

package com.quandoo.restaurant.jobs;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;
import com.quandoo.restaurant.data.repository.ReservationsRepository;

import javax.inject.Inject;

/**
 * Created by Behzad on 1/4/2018.
 */

public class TheJobCreator implements JobCreator {

    @Inject
    public ReservationsRepository reservationsRepository;

    @Inject
    public TheJobCreator(ReservationsRepository reservationsRepository) {
        this.reservationsRepository = reservationsRepository;
    }

    @Nullable
    @Override
    public Job create(@NonNull String tag) {
        switch (tag) {
            case PeriodicReservationCleanerJob.TAG:
                return new PeriodicReservationCleanerJob(reservationsRepository);
            default:
                return null;
        }
    }
}

package com.quandoo.restaurant.jobs;

import android.support.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.quandoo.restaurant.data.repository.ReservationsRepository;

import java.util.concurrent.TimeUnit;

/**
 * Created by Behzad on 1/4/2018.
 */

public class PeriodicReservationCleanerJob extends Job {

    public static final String TAG = "periodic_reservation_cleaner_tag";

    private ReservationsRepository mReservationsRepository;

    public PeriodicReservationCleanerJob(ReservationsRepository reservationsRepository) {
        mReservationsRepository = reservationsRepository;
    }

    public static JobRequest schedulePeriodic() {
        JobRequest jobRequest = new JobRequest.Builder(TAG)
                .setPeriodic(TimeUnit.MINUTES.toMillis(15),
                        TimeUnit.MINUTES.toMillis(5))
                .setUpdateCurrent(true)
                .build();
        jobRequest.schedule();
        return jobRequest;
    }

    @NonNull
    @Override
    protected Result onRunJob(@NonNull Params params) {
        int count = mReservationsRepository.removeAllReservations().blockingGet();
        return Result.RESCHEDULE;
    }
}

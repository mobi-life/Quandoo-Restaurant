package com.quandoo.restaurant.di.module;

import com.quandoo.restaurant.helper.SchedulersFacade;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Behzad on 12/28/2017.
 */

@Module
public class FragmentModule {

    @Provides
    public SchedulersFacade provideSchedulersFacade() {
        return new SchedulersFacade();
    }
}

package com.quandoo.restaurant.di.component;

import com.quandoo.restaurant.di.module.ActivityModule;
import com.quandoo.restaurant.di.scope.PerActivity;
import com.quandoo.restaurant.ui.views.activity.MainActivity;

import dagger.Component;

/**
 * Created by Behzad on 12/25/2017.
 * <p>
 * Provides dependencies for the Activity scope
 */

@PerActivity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class})
public interface ActivityComponent {

    public void inject(MainActivity activity);
}

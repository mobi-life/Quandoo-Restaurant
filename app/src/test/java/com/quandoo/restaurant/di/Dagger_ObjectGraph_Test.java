package com.quandoo.restaurant.di;

import android.app.Activity;
import android.app.Application;

import com.quandoo.restaurant.di.component.ActivityComponent;
import com.quandoo.restaurant.di.component.AppComponent;
import com.quandoo.restaurant.di.component.DaggerActivityComponent;
import com.quandoo.restaurant.di.component.DaggerAppComponent;
import com.quandoo.restaurant.di.component.DaggerFragmentComponent;
import com.quandoo.restaurant.di.component.FragmentComponent;
import com.quandoo.restaurant.di.module.ActivityModule;
import com.quandoo.restaurant.di.module.ApplicationModule;
import com.quandoo.restaurant.di.module.FragmentModule;
import com.quandoo.restaurant.di.module.NetworkModule;

import org.junit.Test;
import org.mockito.Mock;

import static junit.framework.Assert.*;

/**
 * Created by Behzad on 12/28/2017.
 */

public class Dagger_ObjectGraph_Test {

    @Mock
    Application application;

    @Mock
    Activity activity;

    @Test
    public void testDagger(){
        AppComponent appComponent = DaggerComponentsFactory
                .getApplicationComponent();

        assertNotNull(appComponent);

        ActivityComponent activityComponent = DaggerComponentsFactory
                .getActivityComponent();

        assertNotNull(activityComponent);

        FragmentComponent fragmentComponent = DaggerComponentsFactory
                .getFragmentComponent();

        assertNotNull(fragmentComponent);

        assertNotNull(fragmentComponent.getCustomerRepository());
        assertNotNull(fragmentComponent.getLoadCustomersUseCase());
        assertNotNull(fragmentComponent.getTablesRepository());
        assertNotNull(fragmentComponent.getLoadTablesUsesCase());
        assertNotNull(fragmentComponent.getCustomersViewModel());
        assertNotNull(fragmentComponent.getTablesViewModel());
    }
}

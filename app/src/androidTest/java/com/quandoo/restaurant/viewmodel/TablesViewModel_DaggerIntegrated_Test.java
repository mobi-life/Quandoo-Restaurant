package com.quandoo.restaurant.viewmodel;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.quandoo.restaurant.di.DaggerComponentsFactory;
import com.quandoo.restaurant.di.component.FragmentComponent;
import com.quandoo.restaurant.helper.SchedulersFacade;
import com.quandoo.restaurant.sharedtest.viewmodel.TablesViewModelTester;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.concurrent.TimeoutException;

/**
 * Created by Behzad on 12/30/2017.
 */

public class TablesViewModel_DaggerIntegrated_Test {

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    SchedulersFacade schedulersFacade;

    TablesViewModel tablesViewModel;
    FragmentComponent fragmentComponent;

    @Before
    public void start() {
        fragmentComponent = DaggerComponentsFactory
                .getFragmentComponent();
        tablesViewModel = fragmentComponent.getTablesViewModel();
    }

    @Test
    public void TablesViewModelTest() throws TimeoutException {
        TablesViewModelTester tester = new TablesViewModelTester(tablesViewModel);
        tester.testLoadingTables();
    }
}

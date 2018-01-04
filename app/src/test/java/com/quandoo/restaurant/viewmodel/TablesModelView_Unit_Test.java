package com.quandoo.restaurant.viewmodel;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.quandoo.restaurant.sharedtest.TestUtils;
import com.quandoo.restaurant.domain.interactor.LoadTablesUseCase;
import com.quandoo.restaurant.helper.SchedulersFacade;
import com.quandoo.restaurant.sharedtest.viewmodel.TablesViewModelTester;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.concurrent.TimeoutException;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.when;

/**
 * Created by Behzad on 12/30/2017.
 */

public class TablesModelView_Unit_Test {

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    public LoadTablesUseCase loadTablesUseCase;
    @Mock
    public SchedulersFacade schedulersFacade;
    @InjectMocks
    TablesViewModel tablesViewModel;

    @Before
    public void start() {
        when(schedulersFacade.ui()).thenReturn(Schedulers.io());
        when(schedulersFacade.io()).thenReturn(Schedulers.io());
    }

    @Test
    public void TablesViewModelTest() throws TimeoutException {
        when(loadTablesUseCase.execute()).thenReturn(Single.just(TestUtils.mockTables()));
        TablesViewModelTester tester = new TablesViewModelTester(tablesViewModel);
        tester.testLoadingTables();
    }
}

package com.quandoo.restaurant.viewmodel;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.quandoo.restaurant.sharedtest.TestUtils;
import com.quandoo.restaurant.domain.interactor.LoadCustomersUseCase;
import com.quandoo.restaurant.helper.SchedulersFacade;
import com.quandoo.restaurant.sharedtest.viewmodel.CustomersViewModelTester;

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
 * Created by Behzad on 12/29/2017.
 */

public class CustomersViewModel_Unit_Test {

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    public LoadCustomersUseCase loadCustomersUseCase;
    @Mock
    public SchedulersFacade schedulersFacade;
    @InjectMocks
    public CustomersViewModel customersViewModel;


    @Before
    public void start() {
        when(schedulersFacade.ui()).thenReturn(Schedulers.io());
        when(schedulersFacade.io()).thenReturn(Schedulers.io());
    }

    @Test
    public void testCustomersViewModel() throws TimeoutException {
        when(loadCustomersUseCase.execute()).thenReturn(Single.just(TestUtils.mockCustomers()));

        CustomersViewModelTester tester = new CustomersViewModelTester(customersViewModel);

        tester.testLoadingCustomers();
        tester.testCustomerSelection();
    }


}

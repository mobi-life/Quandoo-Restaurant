package com.quandoo.restaurant.viewmodel;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.support.test.runner.AndroidJUnit4;

import com.quandoo.restaurant.di.DaggerComponentsFactory;
import com.quandoo.restaurant.di.component.FragmentComponent;
import com.quandoo.restaurant.helper.SchedulersFacade;
import com.quandoo.restaurant.sharedtest.viewmodel.CustomersViewModelTester;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.concurrent.TimeoutException;

/**
 * Created by Behzad on 12/29/2017.
 */

@RunWith(AndroidJUnit4.class)
public class CustomersViewModel_DaggerIntegrated_Test {

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    SchedulersFacade schedulersFacade;

    CustomersViewModel customersViewModel;
    FragmentComponent fragmentComponent;


    @Before
    public void start() {
        fragmentComponent = DaggerComponentsFactory
                .getFragmentComponent();

        customersViewModel = fragmentComponent.getCustomersViewModel();
    }

    // This test will need internet connection
    @Test
    public void testCustomersViewModel() throws TimeoutException {
        CustomersViewModelTester tester = new CustomersViewModelTester(customersViewModel);
        tester.testLoadingCustomers();
        tester.testCustomerSelection();
    }

}

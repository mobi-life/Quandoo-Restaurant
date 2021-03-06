package com.quandoo.restaurant.data.repository;

import android.app.Application;

import com.quandoo.restaurant.di.DaggerComponentsFactory;
import com.quandoo.restaurant.di.component.AppComponent;
import com.quandoo.restaurant.di.component.DaggerAppComponent;
import com.quandoo.restaurant.di.component.DaggerFragmentComponent;
import com.quandoo.restaurant.di.component.FragmentComponent;
import com.quandoo.restaurant.di.module.ApplicationModule;
import com.quandoo.restaurant.di.module.FragmentModule;
import com.quandoo.restaurant.di.module.NetworkModule;

import net.jodah.concurrentunit.Waiter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.concurrent.TimeoutException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by Behzad on 12/28/2017.
 */

public class CustomersRepository_DaggerIntegrated_Test {

    final Waiter waiter = new Waiter();
    CustomersRepository customersRepository;

    @Before
    public void start() {
        customersRepository = DaggerComponentsFactory
                .getFragmentComponent()
                .getCustomerRepository();
    }

    // This test will need internet connection
    @Test
    public void testGettingCustomers() throws TimeoutException {
        customersRepository.getCustomers()
                .subscribe(
                        customers -> {
                            assertNotNull(customers);
                            assertEquals(customers.size(), 21);
                            waiter.resume();
                        },
                        throwable -> waiter.fail(throwable.getMessage())
                );

        waiter.await(10000);
    }

}

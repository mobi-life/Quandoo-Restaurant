package com.quandoo.restaurant.data.repository;

import com.quandoo.restaurant.data.network.QuandooService;
import com.quandoo.restaurant.data.network.model.CustomerResponseItem;

import net.jodah.concurrentunit.Waiter;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import io.reactivex.Single;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * Created by Behzad on 12/27/2017.
 */

public class CustomersRepository_Unit_Test {

//    @Rule
//    public DaggerMockRule<AppComponent> mockitoAppRule = new DaggerMockRule<AppComponent>
//            (AppComponent.class, new ApplicationModule(application));
//    @Rule
//    public DaggerMockRule<FragmentComponent> mockitoRule = new DaggerMockRule<FragmentComponent>
//            (FragmentComponent.class, new FragmentModule())
//            .addComponentDependency(AppComponent.class,FragmentComponent.class,new ApplicationModule(application),new NetworkModule())
//            .set(mComponent -> customersRepository = mComponent.getCustomerRepository());

    final Waiter waiter = new Waiter();
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    QuandooService restService;
    @InjectMocks
    CustomersRepository customersRepository;

    @Test
    public void testGettingCustomers() throws TimeoutException {
        when(restService.getCustomers()).thenReturn(mockRemoteCustomers());
        customersRepository.getCustomers()
                .subscribe(
                        customers -> {
                            assertNotNull(customers);
                            assertEquals(customers.size(), 3);
                            waiter.resume();
                        },
                        throwable -> waiter.fail(throwable.getMessage())
                );

        waiter.await(1000);
    }

    private Single<List<CustomerResponseItem>> mockRemoteCustomers() {
        List<CustomerResponseItem> customers = new ArrayList<>();
        customers.add(new CustomerResponseItem());
        customers.add(new CustomerResponseItem());
        customers.add(new CustomerResponseItem());
        return Single.just(customers);
    }
}

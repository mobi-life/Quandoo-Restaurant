package com.quandoo.restaurant.sharedtest.viewmodel;

import com.quandoo.restaurant.domain.contract.Screen;
import com.quandoo.restaurant.domain.model.CustomerModel;
import com.quandoo.restaurant.viewmodel.CustomersViewModel;

import net.jodah.concurrentunit.Waiter;

import java.util.List;
import java.util.concurrent.TimeoutException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Behzad on 12/29/2017.
 */

public class CustomersViewModelTester {

    Waiter waiter = new Waiter();
    boolean showLoadingFlag = false;
    boolean hideLoadingFlag = false;
    List<CustomerModel> customers;
    CustomersViewModel customersViewModel;

    public CustomersViewModelTester(CustomersViewModel customersViewModel){
        this.customersViewModel = customersViewModel;
    }

    public void testLoadingCustomers() throws TimeoutException {
        showLoadingFlag = false;
        hideLoadingFlag = false;
        waiter = new Waiter();
        customersViewModel.getCustomersStatus()
                .observeForever(response -> {
                    assertNull(response.error);
                    assertNotNull(response.data);
                    customers = response.data;
                });
        customersViewModel.getLoadingStatus()
                .observeForever(response -> {
                    assertNotNull(response);
                    if (response) {
                        showLoadingFlag = true;
                    } else {
                        hideLoadingFlag = true;
                    }
                    if (showLoadingFlag && hideLoadingFlag) {
                        waiter.resume();
                    }
                });
        customersViewModel.loadCustomers();

        waiter.await(10000);

        assertTrue(showLoadingFlag);
        assertTrue(hideLoadingFlag);
        assertNotNull(customers);
    }

    public void testCustomerSelection() throws TimeoutException {
        waiter = new Waiter();
        final CustomerModel customer =
                new CustomerModel(1,"name","family");
        customersViewModel.getNavigationStatus()
                .observeForever(response -> {
                    assertEquals(response.screen, Screen.Tables);
                    assertNotNull(response.data);
                    assertEquals(response.data, customer);
                    waiter.resume();
                });
        customersViewModel.onCustomerSelected(customer);

        waiter.await(1000);
    }
}

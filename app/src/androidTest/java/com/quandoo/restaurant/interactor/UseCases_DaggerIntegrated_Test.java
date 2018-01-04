package com.quandoo.restaurant.interactor;

import com.quandoo.restaurant.sharedtest.TestUtils;
import com.quandoo.restaurant.di.DaggerComponentsFactory;
import com.quandoo.restaurant.di.component.FragmentComponent;
import com.quandoo.restaurant.domain.interactor.CancelReservationUseCase;
import com.quandoo.restaurant.domain.interactor.LoadCustomersUseCase;
import com.quandoo.restaurant.domain.interactor.LoadTablesUseCase;
import com.quandoo.restaurant.domain.interactor.SaveReservationUseCase;
import com.quandoo.restaurant.domain.model.CustomerModel;
import com.quandoo.restaurant.domain.model.TableModel;

import net.jodah.concurrentunit.Waiter;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeoutException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Behzad on 12/28/2017.
 */

public class UseCases_DaggerIntegrated_Test {

    public Waiter waiter = new Waiter();

    FragmentComponent fragmentComponent;

    @Before
    public void start() {
        fragmentComponent = DaggerComponentsFactory
                .getFragmentComponent();
        waiter = new Waiter();
    }

    // This test will need internet connection
    @Test
    public void testLoadCustomersUseCase() throws TimeoutException {
        LoadCustomersUseCase useCase = fragmentComponent.getLoadCustomersUseCase();
        useCase.execute()
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

    // This test will need internet connection
    @Test
    public void testLoadTablesUseCase() throws TimeoutException {
        LoadTablesUseCase useCase = fragmentComponent.getLoadTablesUsesCase();
        useCase.execute()
                .subscribe(
                        tables -> {
                            assertNotNull(tables);
                            assertEquals(tables.size(), 70);
                            waiter.resume();
                        },
                        throwable -> waiter.fail(throwable.getMessage())
                );

        waiter.await(10000);
    }

    @Test
    public void testSaveReservationUseCase() throws TimeoutException {
        SaveReservationUseCase useCase = fragmentComponent.getSaveReservationUseCase();
        final CustomerModel customer = TestUtils.mockCustomers().get(1);
        final TableModel table = TestUtils.mockTables().get(1);
        useCase
                .setTable(table)
                .setCustomer(customer)
                .execute()
                .subscribe(
                        reservationModel -> {
                            assertNotNull(reservationModel);
                            assertFalse(reservationModel.isCancellation());
                            assertEquals(customer.getId(),reservationModel.getCustomerId());
                            assertEquals(table.getTableNumber(),reservationModel.getTableNumber());
                            waiter.resume();
                        },
                        throwable -> waiter.fail()
                );

        waiter.await(10000);
    }

    @Test
    public void testCancelReservationUseCaseByCustomer() throws TimeoutException {
        final CustomerModel customer = TestUtils.mockCustomers().get(1);
        final TableModel table = TestUtils.mockTables().get(1);

        saveMockedReservation(customer,table);

        CancelReservationUseCase useCase = fragmentComponent.getCancelReservationUseCase();
        useCase
                .setCustomer(customer)
                .execute()
                .subscribe(
                        reservationModel -> {
                            assertNotNull(reservationModel);
                            assertTrue(reservationModel.isCancellation());
                            assertEquals(customer.getId(),reservationModel.getCustomerId());
                            assertEquals(table.getTableNumber(),reservationModel.getTableNumber());
                            waiter.resume();
                        },
                        throwable -> waiter.fail()
                );

        waiter.await(10000);
    }

    @Test
    public void testCancelReservationUseCaseByTable() throws TimeoutException {
        final CustomerModel customer = TestUtils.mockCustomers().get(1);
        final TableModel table = TestUtils.mockTables().get(1);

        saveMockedReservation(customer,table);

        CancelReservationUseCase useCase = fragmentComponent.getCancelReservationUseCase();
        useCase
                .setTable(table)
                .execute()
                .subscribe(
                        reservationModel -> {
                            assertNotNull(reservationModel);
                            assertTrue(reservationModel.isCancellation());
                            assertEquals(customer.getId(),reservationModel.getCustomerId());
                            assertEquals(table.getTableNumber(),reservationModel.getTableNumber());
                            waiter.resume();
                        },
                        throwable -> waiter.fail()
                );

        waiter.await(10000);
    }

    private void saveMockedReservation(CustomerModel customer, TableModel table){
        SaveReservationUseCase saveReservationUseCase = fragmentComponent.getSaveReservationUseCase();
        saveReservationUseCase
                .setTable(table)
                .setCustomer(customer)
                .execute()
                .blockingGet();
    }

}

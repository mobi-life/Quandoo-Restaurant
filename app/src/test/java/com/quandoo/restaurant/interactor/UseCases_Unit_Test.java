package com.quandoo.restaurant.interactor;

import com.quandoo.restaurant.sharedtest.TestUtils;
import com.quandoo.restaurant.data.network.QuandooService;
import com.quandoo.restaurant.data.repository.ReservationsRepository;
import com.quandoo.restaurant.domain.interactor.LoadCustomersUseCase;
import com.quandoo.restaurant.domain.interactor.LoadTablesUseCase;
import com.quandoo.restaurant.data.repository.CustomersRepository;
import com.quandoo.restaurant.data.repository.TablesRepository;

import net.jodah.concurrentunit.Waiter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.concurrent.TimeoutException;

import io.reactivex.Single;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by Behzad on 12/28/2017.
 */

public class UseCases_Unit_Test {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    QuandooService restService;
    @Mock
    CustomersRepository customersRepository;
    @Mock
    TablesRepository tablesRepository;
    @Mock
    ReservationsRepository reservationsRepository;

    @InjectMocks
    LoadCustomersUseCase loadCustomersUseCase;
    @InjectMocks
    LoadTablesUseCase loadTablesUseCase;

    final Waiter waiter = new Waiter();

    @Before
    public void start(){
        when(reservationsRepository.getAllReservations())
                .thenReturn(Single.just(TestUtils.mockedReservations()));
    }

    @Test
    public void testLoadCustomersUseCase() throws TimeoutException {
        when(customersRepository.getCustomers()).thenReturn(Single.just(TestUtils.mockCustomers()));
        loadCustomersUseCase.execute()
                .subscribe(
                        customers ->{
                            assertNotNull(customers);
                            assertEquals(customers.size(),3);
                            waiter.resume();
                        },
                        throwable -> waiter.fail(throwable.getMessage())
                );

        waiter.await(1000);
    }

    @Test
    public void testLoadTables() throws TimeoutException {
        when(tablesRepository.getTables()).thenReturn(Single.just(TestUtils.mockTables()));
        loadTablesUseCase.execute()
                .subscribe(
                        tables ->{
                            assertNotNull(tables);
                            assertEquals(tables.size(),3);
                            assertFalse(tables.get(0).isReserved());
                            waiter.resume();
                        },
                        throwable -> waiter.fail(throwable.getMessage())
                );

        waiter.await(1000);
    }



}

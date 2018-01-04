package com.quandoo.restaurant.data.repository;

import com.quandoo.restaurant.data.network.QuandooService;

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
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by Behzad on 12/28/2017.
 */

public class TablesRepository_Unit_Test {

    final Waiter waiter = new Waiter();
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    QuandooService restService;
    @InjectMocks
    TablesRepository tablesRepository;

    @Test
    public void testGettingTables() throws TimeoutException {
        when(restService.getTables()).thenReturn(Single.just(mockTables()));
        tablesRepository.getTables()
                .subscribe(
                        tables -> {
                            assertNotNull(tables);
                            assertEquals(tables.size(), 3);
                            assertFalse(tables.get(1).isReserved());
                            assertTrue(tables.get(2).isReserved());
                            waiter.resume();
                        },
                        throwable -> waiter.fail(throwable.getMessage())
                );

        waiter.await(1000);
    }

    private List<Boolean> mockTables() {
        List<Boolean> mockedTables = new ArrayList<>();
        mockedTables.add(false);
        mockedTables.add(true);
        mockedTables.add(false);
        return mockedTables;
    }

}

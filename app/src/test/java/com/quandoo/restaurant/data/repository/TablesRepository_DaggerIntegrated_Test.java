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
import com.quandoo.restaurant.domain.model.TableModel;

import net.jodah.concurrentunit.Waiter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.concurrent.TimeoutException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Behzad on 12/28/2017.
 */

public class TablesRepository_DaggerIntegrated_Test {
    final Waiter waiter = new Waiter();

    TablesRepository tablesRepository;

    @Before
    public void start() {
        tablesRepository = DaggerComponentsFactory
                .getFragmentComponent()
                .getTablesRepository();
    }

    // This test will need internet connection
    @Test
    public void testGettingTables() throws TimeoutException {
        tablesRepository.getTables()
                .subscribe(
                        tables -> {
                            assertNotNull(tables);
                            assertEquals(tables.size(), 70);
                            assertTrue(tables.get(0) instanceof TableModel);
                            waiter.resume();
                        },
                        throwable -> waiter.fail(throwable.getMessage())
                );

        waiter.await(10000);
    }
}

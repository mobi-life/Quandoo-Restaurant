package com.quandoo.restaurant.sharedtest.viewmodel;

import com.quandoo.restaurant.domain.model.TableModel;
import com.quandoo.restaurant.viewmodel.TablesViewModel;

import net.jodah.concurrentunit.Waiter;

import java.util.List;
import java.util.concurrent.TimeoutException;
import static junit.framework.Assert.*;

/**
 * Created by Behzad on 12/30/2017.
 */

public class TablesViewModelTester {

    Waiter waiter = new Waiter();
    boolean showLoadingFlag = false;
    boolean hideLoadingFlag = false;
    List<TableModel> tables;
    TablesViewModel tablesViewModel;

    public TablesViewModelTester(TablesViewModel tablesViewModel){
        this.tablesViewModel = tablesViewModel;
    }

    public void testLoadingTables() throws TimeoutException {
        showLoadingFlag = false;
        hideLoadingFlag = false;
        waiter = new Waiter();
        tablesViewModel.getTablesStatus()
                .observeForever(response -> {
                    assertNull(response.error);
                    assertNotNull(response.data);
                    tables = response.data;
                });
        tablesViewModel.getLoadingStatus()
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
        tablesViewModel.loadTables();

        waiter.await(10000);

        assertTrue(showLoadingFlag);
        assertTrue(hideLoadingFlag);
        assertNotNull(tables);
    }
}

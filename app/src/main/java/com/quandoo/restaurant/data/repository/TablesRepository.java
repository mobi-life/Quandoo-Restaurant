package com.quandoo.restaurant.data.repository;

import com.quandoo.restaurant.data.network.QuandooService;
import com.quandoo.restaurant.domain.model.TableModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by Behzad on 12/28/2017.
 */

public class TablesRepository {

    private QuandooService mApi;

    @Inject
    public TablesRepository(QuandooService apiService) {
        this.mApi = apiService;
    }

    public Single<List<TableModel>> getTables() {
        return mApi.getTables()
                .flatMap(tables -> {
                    List<TableModel> tableModels = new ArrayList<>();
                    for (int tableIndex = 0; tableIndex < tables.size(); tableIndex++) {
                        tableModels.add(new TableModel(
                                tableIndex + 1,
                                tables.get(tableIndex),
                                -1
                        ));
                    }
                    return Single.just(tableModels);
                });
    }
}

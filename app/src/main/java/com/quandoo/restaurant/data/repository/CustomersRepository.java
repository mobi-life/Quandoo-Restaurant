package com.quandoo.restaurant.data.repository;

import com.quandoo.restaurant.data.network.QuandooService;
import com.quandoo.restaurant.data.network.model.CustomerResponseItem;
import com.quandoo.restaurant.domain.model.CustomerModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by Behzad on 12/27/2017.
 */

public class CustomersRepository {

    private QuandooService mApi;

    @Inject
    public CustomersRepository(QuandooService apiService) {
        this.mApi = apiService;
    }

    public Single<List<CustomerModel>> getCustomers() {
        return mApi.getCustomers()
                .flatMap(customers -> {
                    Thread.sleep(500); // for simulating internet response delay
                    List<CustomerModel> customerModels = new ArrayList<>();
                    for (CustomerResponseItem customer : customers) {
                        customerModels.add(customer.toDomainModel());
                    }
                    return Single.just(customerModels);
                });
    }

}

package com.quandoo.restaurant.data.network;

import com.quandoo.restaurant.data.network.model.CustomerResponseItem;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import retrofit2.Retrofit;

/**
 * Created by Behzad on 12/25/2017.
 */

public class QuandooService {

    private QuandooApi mApi;

    @Inject
    public QuandooService(Retrofit retrofit) {
        mApi = retrofit.create(QuandooApi.class);
        retrofit.baseUrl();
    }

    public Single<List<CustomerResponseItem>> getCustomers() {
        return mApi.getCustomers();
    }

    public Single<List<Boolean>> getTables() {
        return mApi.getTables();
    }

}

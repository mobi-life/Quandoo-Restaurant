package com.quandoo.restaurant.data.network;

import com.quandoo.restaurant.data.network.model.CustomerResponseItem;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;


/**
 * Created by Behzad on 12/25/2017.
 */

public interface QuandooApi {
    @GET("customer-list.json")
    Single<List<CustomerResponseItem>> getCustomers();

    @GET("table-map.json")
    Single<List<Boolean>> getTables();
}

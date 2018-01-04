package com.quandoo.restaurant.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.quandoo.restaurant.domain.contract.ModelMapper;
import com.quandoo.restaurant.domain.model.CustomerModel;

/**
 * Created by Behzad on 12/25/2017.
 * <p>
 * An immutable CustomerResponseItem model
 */


public class CustomerResponseItem implements ModelMapper {

    @SerializedName("customerFirstName")
    @Expose
    private String mFirstName;

    @SerializedName("customerLastName")
    @Expose
    private String mLastName;

    @SerializedName("id")
    @Expose
    private long mId;

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        this.mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public long getId() {
        return mId;
    }

    @Override
    public CustomerModel toDomainModel() {
        return new CustomerModel(getId(), getFirstName(), getLastName());
    }
}

package com.quandoo.restaurant.domain.model;

/**
 * Created by Behzad on 12/27/2017.
 */

public class CustomerModel extends BaseModel {

    private String mName;
    private String mFamily;
    private long mId;
    /**
     * Helps to identify whether the customer already has a reservation or not.
     */
    private boolean hasReservation = false;

    public CustomerModel(long id, String name, String family) {
        mId = id;
        mName = name;
        mFamily = family;
    }

    public String getName() {
        return mName;
    }

    public String getFamily() {
        return mFamily;
    }

    public long getId() {
        return mId;
    }

    public boolean hasReservation() {
        return hasReservation;
    }

    public void setHasReservation(boolean status) {
        this.hasReservation = status;
    }

}

package com.quandoo.restaurant.domain.model;

/**
 * Created by Behzad on 12/31/2017.
 */

public class ReservationModel extends BaseModel {

    private long mId;
    private int mTableNumber;
    private long mCustomerId;
    /**
     * To indicate the current object as the cancellation result.
     */
    private boolean isCanceled = false;

    public ReservationModel(long id, int tableNumber, long customerId) {
        this.mId = id;
        this.mTableNumber = tableNumber;
        this.mCustomerId = customerId;
    }

    public long getId() {
        return mId;
    }

    public int getTableNumber() {
        return mTableNumber;
    }

    public long getCustomerId() {
        return mCustomerId;
    }

    public boolean isCancellation() {
        return isCanceled;
    }

    public void setCanceled(boolean canceled) {
        isCanceled = canceled;
    }
}

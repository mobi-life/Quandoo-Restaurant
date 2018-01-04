package com.quandoo.restaurant.domain.model;

/**
 * Created by Behzad on 12/28/2017.
 */

public class TableModel extends BaseModel {

    private int mTableNumber;
    /**
     * Defines whether table is available or not
     * True means table is available and False means reserved
     */
    private boolean mTableState;
    private long mCustomerId = -1;

    public TableModel(int tableNumber, boolean tableState, long customerId) {
        mTableNumber = tableNumber;
        mTableState = tableState;
        mCustomerId = customerId;
    }

    public int getTableNumber() {
        return mTableNumber;
    }

    public boolean isReserved() {
        return !mTableState;
    }

    public long getCustomerId() {
        return mCustomerId;
    }

    public void setCustomerId(long id) {
        mCustomerId = id;
    }

    public void setAsReserved(long customerId) {
        mTableState = false;
        setCustomerId(customerId);
    }

    public void clearReservation() {
        mTableState = true;
        setCustomerId(-1);
    }
}

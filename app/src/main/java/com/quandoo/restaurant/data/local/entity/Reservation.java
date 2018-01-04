package com.quandoo.restaurant.data.local.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Behzad on 1/1/2018.
 */

@Entity
public class Reservation {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "table_no")
    private int tableNo;

    @ColumnInfo(name = "customer_id")
    private long customerId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTableNo() {
        return tableNo;
    }

    public void setTableNo(int tableNo) {
        this.tableNo = tableNo;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }
}

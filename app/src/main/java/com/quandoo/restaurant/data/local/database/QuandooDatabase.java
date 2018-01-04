package com.quandoo.restaurant.data.local.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.quandoo.restaurant.data.local.dao.ReservationDao;
import com.quandoo.restaurant.data.local.entity.Reservation;

/**
 * Created by Behzad on 1/1/2018.
 */

@Database(entities = {Reservation.class}, version = 1)
public abstract class QuandooDatabase extends RoomDatabase {

    public abstract ReservationDao reservationDao();
}

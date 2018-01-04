package com.quandoo.restaurant.data.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.quandoo.restaurant.data.local.entity.Reservation;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Behzad on 1/1/2018.
 */

@Dao
public interface ReservationDao {

    @Query("SELECT * FROM reservation")
    Single<List<Reservation>> getAllReservations();

    @Query("SELECT * FROM reservation where customer_id = :id LIMIT 1")
    Single<Reservation> findByCustomerId(long id);

    @Query("SELECT * FROM reservation where table_no = :tableNo LIMIT 1")
    Single<Reservation> findByTableNo(int tableNo);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertReservation(Reservation reservation);

    @Insert
    long[] insertAll(List<Reservation> reservations);

    @Update
    void update(Reservation reservation);

    @Delete
    int delete(Reservation reservation);

    @Query("DELETE FROM reservation")
    int deleteAll();
}

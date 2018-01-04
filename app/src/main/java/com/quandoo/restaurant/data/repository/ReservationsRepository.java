package com.quandoo.restaurant.data.repository;

import com.quandoo.restaurant.data.local.database.QuandooDatabase;
import com.quandoo.restaurant.data.local.entity.Reservation;
import com.quandoo.restaurant.domain.model.CustomerModel;
import com.quandoo.restaurant.domain.model.ReservationModel;
import com.quandoo.restaurant.domain.model.TableModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by Behzad on 12/31/2017.
 */

public class ReservationsRepository {

    private QuandooDatabase mQuandooDatabase;

    @Inject
    public ReservationsRepository(QuandooDatabase quandooDatabase) {
        this.mQuandooDatabase = quandooDatabase;
    }

    public Single<ReservationModel> saveReservation(final CustomerModel customer, final TableModel table) {
        return Single.create(e -> {
            try {
                Reservation reservation = new Reservation();
                reservation.setCustomerId(customer.getId());
                reservation.setTableNo(table.getTableNumber());
                long newId = mQuandooDatabase
                        .reservationDao()
                        .insertReservation(reservation);
                if (newId > 0) {
                    e.onSuccess(new ReservationModel(
                            newId,
                            reservation.getTableNo(),
                            reservation.getCustomerId()
                    ));
                } else {
                    e.onError(new RuntimeException("Couldn't add the reservation."));
                }
            } catch (Exception ex) {
                e.onError(ex);
            }
        });
    }

    public Single<ReservationModel> cancelReservation(final CustomerModel customer) {
        return Single.create(e -> {
            try {
                Reservation reservation = null;
                reservation = mQuandooDatabase
                        .reservationDao()
                        .findByCustomerId(customer.getId())
                        .blockingGet();


                if (reservation != null) {
                    int count = mQuandooDatabase
                            .reservationDao()
                            .delete(reservation);
                    if (count > 0) {
                        e.onSuccess(new ReservationModel(
                                reservation.getId(),
                                reservation.getTableNo(),
                                reservation.getCustomerId()
                        ));
                    } else {
                        e.onError(new RuntimeException("Error happened in canceling the reservation."));
                    }
                } else {
                    e.onError(new RuntimeException("Error happened in canceling the reservation."));
                }
            } catch (Exception ex) {
                e.onError(ex);
            }
        });
    }

    public Single<ReservationModel> cancelReservation(final TableModel table) {
        return Single.create(e -> {
            try {
                Reservation reservation = mQuandooDatabase
                        .reservationDao()
                        .findByTableNo(table.getTableNumber())
                        .blockingGet();

                if (reservation != null) {
                    int count = mQuandooDatabase
                            .reservationDao()
                            .delete(reservation);
                    if (count > 0) {
                        e.onSuccess(new ReservationModel(
                                reservation.getId(),
                                reservation.getTableNo(),
                                reservation.getCustomerId()
                        ));
                    } else {
                        e.onError(new RuntimeException("Error happened in canceling the reservation."));
                    }
                } else {
                    e.onError(new RuntimeException("Error happened in canceling the reservation."));
                }
            } catch (Exception ex) {
                e.onError(ex);
            }
        });
    }

    public Single<Integer> removeAllReservations() {
        return Single.create(e -> {
            try {
                int count = mQuandooDatabase
                        .reservationDao()
                        .deleteAll();

                e.onSuccess(count);
            } catch (Exception ex) {
                e.onError(ex);
            }
        });
    }

    public Single<List<ReservationModel>> getAllReservations() {
        return mQuandooDatabase
                .reservationDao()
                .getAllReservations()
                .flatMap(reservations -> {
                    List<ReservationModel> reservationModels = new ArrayList<>();
                    for (Reservation reservation : reservations) {
                        reservationModels.add(new ReservationModel(
                                reservation.getId(),
                                reservation.getTableNo(),
                                reservation.getCustomerId()
                        ));
                    }
                    return Single.just(reservationModels);
                });
    }
}

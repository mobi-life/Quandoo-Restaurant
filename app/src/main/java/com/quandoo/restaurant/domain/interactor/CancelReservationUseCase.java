package com.quandoo.restaurant.domain.interactor;

import com.quandoo.restaurant.data.repository.ReservationsRepository;
import com.quandoo.restaurant.domain.model.CustomerModel;
import com.quandoo.restaurant.domain.model.ReservationModel;
import com.quandoo.restaurant.domain.model.TableModel;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by Behzad on 1/2/2018.
 */

public class CancelReservationUseCase implements Interactor<ReservationModel> {

    private TableModel mTable;
    private CustomerModel mCustomer;
    private ReservationsRepository mRepository;

    @Inject
    public CancelReservationUseCase(ReservationsRepository repository) {
        this.mRepository = repository;
    }

    @Override
    public Single<ReservationModel> execute() {
        if (mTable == null && mCustomer == null) {
            throw new RuntimeException
                    ("Customer or Table information are needed for cancelling a reservation");
        }

        Single<ReservationModel> result;
        if (mTable != null) {
            result = mRepository.cancelReservation(mTable);
        } else {
            result = mRepository.cancelReservation(mCustomer);
        }

        return result.flatMap(reservationModel -> {
            if (reservationModel != null) {
                reservationModel.setCanceled(true);
            }
            return Single.just(reservationModel);
        });
    }

    public CancelReservationUseCase setTable(TableModel table) {
        this.mTable = table;
        return this;
    }

    public CancelReservationUseCase setCustomer(CustomerModel customer) {
        this.mCustomer = customer;
        return this;
    }
}

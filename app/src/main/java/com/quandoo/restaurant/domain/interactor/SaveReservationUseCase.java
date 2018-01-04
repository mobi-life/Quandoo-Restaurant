package com.quandoo.restaurant.domain.interactor;

import com.quandoo.restaurant.data.repository.ReservationsRepository;
import com.quandoo.restaurant.domain.exceptions.MultipleTableReservationException;
import com.quandoo.restaurant.domain.model.CustomerModel;
import com.quandoo.restaurant.domain.model.ReservationModel;
import com.quandoo.restaurant.domain.model.TableModel;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by Behzad on 12/31/2017.
 */

public class SaveReservationUseCase implements Interactor<ReservationModel> {

    private ReservationsRepository mRepository;
    private CustomerModel mCustomer;
    private TableModel mTable;

    @Inject
    public SaveReservationUseCase(ReservationsRepository repository) {
        this.mRepository = repository;
    }

    public SaveReservationUseCase setCustomer(CustomerModel customer) {
        this.mCustomer = customer;
        return this;
    }

    public SaveReservationUseCase setTable(TableModel table) {
        this.mTable = table;
        return this;
    }

    @Override
    public Single<ReservationModel> execute() {
        if (mTable == null || mCustomer == null) {
            throw new RuntimeException
                    ("Customer and Table information are needed for saving a reservation");
        }

        if (mCustomer.hasReservation()) {
            return Single.create(e -> {
                e.onError(new MultipleTableReservationException());
            });
        } else {
            return mRepository.saveReservation(mCustomer, mTable);
        }
    }
}

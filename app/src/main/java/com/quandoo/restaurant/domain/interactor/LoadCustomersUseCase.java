package com.quandoo.restaurant.domain.interactor;

import com.quandoo.restaurant.data.repository.CustomersRepository;
import com.quandoo.restaurant.data.repository.ReservationsRepository;
import com.quandoo.restaurant.domain.model.CustomerModel;
import com.quandoo.restaurant.domain.model.ReservationModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by Behzad on 12/28/2017.
 */

public class LoadCustomersUseCase implements Interactor<List<CustomerModel>> {

    private CustomersRepository mCustomersRepository;
    private ReservationsRepository mReservationsRepository;

    @Inject
    public LoadCustomersUseCase(CustomersRepository customersRepository,
                                ReservationsRepository reservationsRepository) {
        this.mCustomersRepository = customersRepository;
        this.mReservationsRepository = reservationsRepository;
    }

    @Override
    public Single<List<CustomerModel>> execute() {
        return mReservationsRepository
                .getAllReservations()
                .flatMap(reservationModels -> {
                    List<CustomerModel> customers = mCustomersRepository.getCustomers()
                            .blockingGet();
                    for (CustomerModel customer : customers) {
                        ReservationModel reservation = findReservation(reservationModels, customer);
                        if (reservation != null) {
                            customer.setHasReservation(true);
                        }
                    }
                    return Single.just(customers);
                });
    }

    private ReservationModel findReservation(List<ReservationModel> reservations,
                                             CustomerModel customer) {
        for (ReservationModel reservation : reservations) {
            if (reservation.getCustomerId() == customer.getId()) {
                return reservation;
            }
        }
        return null;
    }
}

package com.quandoo.restaurant.domain.interactor;

import com.quandoo.restaurant.data.repository.ReservationsRepository;
import com.quandoo.restaurant.data.repository.TablesRepository;
import com.quandoo.restaurant.domain.model.ReservationModel;
import com.quandoo.restaurant.domain.model.TableModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by Behzad on 12/28/2017.
 */

public class LoadTablesUseCase implements Interactor<List<TableModel>> {

    private TablesRepository mTablesRepository;
    private ReservationsRepository mReservationsRepository;

    @Inject
    public LoadTablesUseCase(TablesRepository tablesRepository,
                             ReservationsRepository reservationsRepository) {
        this.mTablesRepository = tablesRepository;
        this.mReservationsRepository = reservationsRepository;
    }

    /**
     * Merges the reservation information with loaded tables information.
     *
     * @return returns list of tables after applying reservation information on them.
     */
    @Override
    public Single<List<TableModel>> execute() {
        return mReservationsRepository
                .getAllReservations()
                .flatMap(reservationModels -> {
                    List<TableModel> tables = mTablesRepository.getTables().blockingGet();
                    for (TableModel table : tables) {
                        ReservationModel reservation = findReservation(reservationModels, table);
                        if (reservation != null) {
                            table.setAsReserved(reservation.getCustomerId());
                        }
                    }

                    return Single.just(tables);
                });
    }

    private ReservationModel findReservation(List<ReservationModel> reservations, TableModel table) {
        for (ReservationModel reservation : reservations) {
            if (reservation.getTableNumber() == table.getTableNumber()) {
                return reservation;
            }
        }
        return null;
    }
}

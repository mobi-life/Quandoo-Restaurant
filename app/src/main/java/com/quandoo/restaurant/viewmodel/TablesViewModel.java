package com.quandoo.restaurant.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.quandoo.restaurant.domain.contract.Screen;
import com.quandoo.restaurant.domain.interactor.CancelReservationUseCase;
import com.quandoo.restaurant.domain.interactor.LoadTablesUseCase;
import com.quandoo.restaurant.domain.interactor.SaveReservationUseCase;
import com.quandoo.restaurant.domain.model.CustomerModel;
import com.quandoo.restaurant.domain.model.ReservationModel;
import com.quandoo.restaurant.domain.model.TableModel;
import com.quandoo.restaurant.helper.SchedulersFacade;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Behzad on 12/28/2017.
 */

public class TablesViewModel extends BaseViewModel {

    private final LoadTablesUseCase mLoadTablesUseCase;
    private final SaveReservationUseCase mSaveReservationUseCase;
    private final CancelReservationUseCase mCancelReservationUseCase;

    /**
     * The actual view(s) will observe tables status to stay up to date about tables list changes
     */
    private final MutableLiveData<DataWrapper<List<TableModel>>> mTablesStatus
            = new MutableLiveData<>();
    /**
     * The actual view(s) will observe reservation status to get notified about the success or
     * failure of a reservation. In the case of successful reservation the view will receive a
     * ReservationModel object otherwise a null value.
     */
    private final MutableLiveData<DataWrapper<ReservationModel>> mReservationStatus
            = new MutableLiveData<>();

    @Inject
    public TablesViewModel(LoadTablesUseCase loadTablesUseCase,
                           SaveReservationUseCase saveReservationUseCase,
                           CancelReservationUseCase cancelReservationUseCase,
                           SchedulersFacade schedulersFacade) {
        this.mLoadTablesUseCase = loadTablesUseCase;
        this.mSaveReservationUseCase = saveReservationUseCase;
        this.mCancelReservationUseCase = cancelReservationUseCase;
        this.mSchedulersFacade = schedulersFacade;
    }

    public MutableLiveData<DataWrapper<List<TableModel>>> getTablesStatus() {
        return mTablesStatus;
    }

    public MutableLiveData<DataWrapper<ReservationModel>> getReservationStatus() {
        return mReservationStatus;
    }

    /**
     * Starts the process of loading tables.
     * The view will receive the result by observing the live data is returned by getTablesStatus().
     */
    public void loadTables() {
        loadAllTables();
    }

    /**
     * Decides what happen in the case of a table selection in actual view.
     *
     * @param table is the selected item.
     */
    public void onTableSelected(TableModel table) {
        Screen screen;
        if (table.isReserved()) {
            if (table.getCustomerId() > 0) {
                screen = Screen.CancelReservationConfirm;
            } else {
                screen = Screen.ReservationCancellationAlert;
            }
        } else {
            screen = Screen.ReservationConfirm;
        }

        getNavigationStatus()
                .postValue(new NavWrapper<TableModel>(screen, table));
    }

    /**
     * Start the process of a table reservation.
     * The view will receive the result by observing the live data is returned by getReservationStatus().
     *
     * @param table
     * @param customer
     */
    public void saveReservationFor(TableModel table, CustomerModel customer) {
        getDisposables()
                .add(
                        mSaveReservationUseCase
                                .setCustomer(customer)
                                .setTable(table)
                                .execute()
                                .subscribeOn(mSchedulersFacade.io())
                                .observeOn(mSchedulersFacade.ui())
                                .doOnSubscribe(c -> getLoadingStatus().setValue(true))
                                .doAfterTerminate(() -> getLoadingStatus().setValue(false))
                                .subscribe(
                                        reservation -> getReservationStatus()
                                                .setValue(
                                                        new DataWrapper<ReservationModel>
                                                                (reservation, null)
                                                ),
                                        throwable -> getReservationStatus()
                                                .setValue(
                                                        new DataWrapper<ReservationModel>
                                                                (null, throwable)
                                                )
                                )
                );
    }

    private void loadAllTables() {
        getDisposables()
                .add(mLoadTablesUseCase.execute()
                        .subscribeOn(mSchedulersFacade.io())
                        .observeOn(mSchedulersFacade.ui())
                        .doOnSubscribe(c -> getLoadingStatus().setValue(true))
                        .doAfterTerminate(() -> getLoadingStatus().setValue(false))
                        .subscribe(
                                data -> getTablesStatus()
                                        .setValue(
                                                new DataWrapper<List<TableModel>>
                                                        (data, null)
                                        ),
                                throwable -> getTablesStatus()
                                        .setValue(
                                                new DataWrapper<>(null, throwable)
                                        )
                        )
                );
    }


    public void cancelReservation(TableModel table) {
        getDisposables().add(
                mCancelReservationUseCase
                        .setTable(table)
                        .execute()
                        .subscribeOn(mSchedulersFacade.io())
                        .observeOn(mSchedulersFacade.ui())
                        .doOnSubscribe(c -> getLoadingStatus().setValue(true))
                        .doAfterTerminate(() -> getLoadingStatus().setValue(false))
                        .subscribe(
                                reservation -> getReservationStatus()
                                        .setValue(
                                                new DataWrapper<ReservationModel>
                                                        (reservation, null)
                                        ),
                                throwable -> getReservationStatus()
                                        .setValue(
                                                new DataWrapper<ReservationModel>
                                                        (null, throwable)
                                        )
                        )
        );
    }
}

package com.quandoo.restaurant.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.quandoo.restaurant.domain.contract.Screen;
import com.quandoo.restaurant.domain.interactor.CancelReservationUseCase;
import com.quandoo.restaurant.domain.interactor.LoadCustomersUseCase;
import com.quandoo.restaurant.domain.model.CustomerModel;
import com.quandoo.restaurant.helper.SchedulersFacade;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Behzad on 12/28/2017.
 */

public class CustomersViewModel extends BaseViewModel {

    private final LoadCustomersUseCase mLoadCustomersUseCase;
    private final CancelReservationUseCase mCancelReservationUseCase;
    private final MutableLiveData<DataWrapper<List<CustomerModel>>> mCustomersStatus
            = new MutableLiveData<>();
    private final MutableLiveData<CustomerModel> mCustomerUpdateStatus = new MutableLiveData<>();

    @Inject
    public CustomersViewModel(LoadCustomersUseCase loadCustomersUseCase,
                              CancelReservationUseCase cancelReservationUseCase,
                              SchedulersFacade schedulersFacade) {
        this.mLoadCustomersUseCase = loadCustomersUseCase;
        this.mCancelReservationUseCase = cancelReservationUseCase;
        this.mSchedulersFacade = schedulersFacade;
    }

    public void loadCustomers() {
        loadAllCustomers();
    }

    public void onCustomerSelected(CustomerModel customer) {
        Screen screen;
        if (customer.hasReservation()) {
            screen = Screen.CancelReservationConfirm;
        } else {
            screen = Screen.Tables;
        }
        getNavigationStatus()
                .setValue(new NavWrapper<CustomerModel>(screen, customer));
    }

    public MutableLiveData<DataWrapper<List<CustomerModel>>> getCustomersStatus() {
        return mCustomersStatus;
    }

    public void cancelReservation(CustomerModel customer) {
        getDisposables().add(
                mCancelReservationUseCase
                        .setCustomer(customer)
                        .execute()
                        .subscribeOn(mSchedulersFacade.io())
                        .observeOn(mSchedulersFacade.ui())
                        .doOnSubscribe(c -> getLoadingStatus().setValue(true))
                        .doAfterTerminate(() -> getLoadingStatus().setValue(false))
                        .subscribe(
                                reservation -> {
                                    customer.setHasReservation(false);
                                    getCustomerUpdateStatus()
                                            .setValue(customer);
                                }
                        )
        );
    }

    public MutableLiveData<CustomerModel> getCustomerUpdateStatus() {
        return mCustomerUpdateStatus;
    }

    private void loadAllCustomers() {
        getDisposables()
                .add(mLoadCustomersUseCase.execute()
                        .subscribeOn(mSchedulersFacade.io())
                        .observeOn(mSchedulersFacade.ui())
                        .doOnSubscribe(c -> getLoadingStatus().setValue(true))
                        .doAfterTerminate(() -> getLoadingStatus().setValue(false))
                        .subscribe(
                                data -> getCustomersStatus()
                                        .setValue(
                                                new DataWrapper<List<CustomerModel>>
                                                        (data, null)
                                        ),
                                throwable -> getCustomersStatus()
                                        .setValue(
                                                new DataWrapper<>
                                                        (null, throwable)
                                        )
                        )
                );
    }

}

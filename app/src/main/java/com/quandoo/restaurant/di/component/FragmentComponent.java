package com.quandoo.restaurant.di.component;

import com.quandoo.restaurant.data.repository.CustomersRepository;
import com.quandoo.restaurant.data.repository.ReservationsRepository;
import com.quandoo.restaurant.data.repository.TablesRepository;
import com.quandoo.restaurant.di.module.FragmentModule;
import com.quandoo.restaurant.di.scope.PerFragment;
import com.quandoo.restaurant.domain.interactor.CancelReservationUseCase;
import com.quandoo.restaurant.domain.interactor.LoadCustomersUseCase;
import com.quandoo.restaurant.domain.interactor.LoadTablesUseCase;
import com.quandoo.restaurant.domain.interactor.SaveReservationUseCase;
import com.quandoo.restaurant.ui.views.fragment.CustomersFragment;
import com.quandoo.restaurant.ui.views.fragment.TablesFragment;
import com.quandoo.restaurant.viewmodel.CustomersViewModel;
import com.quandoo.restaurant.viewmodel.TablesViewModel;

import dagger.Component;

/**
 * Created by Behzad on 12/25/2017.
 * <p>
 * Provides dependencies for the Fragment scope
 */

@PerFragment
@Component(dependencies = {AppComponent.class}, modules = {FragmentModule.class})
public interface FragmentComponent {
    CustomersRepository getCustomerRepository();

    TablesRepository getTablesRepository();

    ReservationsRepository getReservationsRepository();

    LoadCustomersUseCase getLoadCustomersUseCase();

    LoadTablesUseCase getLoadTablesUsesCase();

    SaveReservationUseCase getSaveReservationUseCase();

    CancelReservationUseCase getCancelReservationUseCase();

    CustomersViewModel getCustomersViewModel();

    TablesViewModel getTablesViewModel();

    void inject(CustomersFragment customersFragment);

    void inject(TablesFragment tablesFragment);
}
